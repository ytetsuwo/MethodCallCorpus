package ms.gundam.astparser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import ms.gundam.astparser.DB.DB;
import ms.gundam.astparser.DB.Value;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * コーパスへ登録
 * 指定したディレクトリ以下にあるすべてのJavaファイルをコーパス化
 * 引数
 *  一つ目 DBのディレクトリ
 *  二つ目 コーパス化するJavaファイルがディレクトリ
 *
 */
public class SourceReader {
	/** 解析するJavaのクラス名を保存しておく */
    private String myClassname = null; 
	
	/** コーパス */
	private DB db;
	
	/** 解析したJavaファイルの数 */
	private int index = 1;

    /**
     * Read.
     *
     * @param file the file
     */
    public void read(File file) {
		FileAnalyzer fileinfo = new FileAnalyzer();
		fileinfo.analyze(file);
		myClassname = fileinfo.getFQDN();
		String[] sourcepath = fileinfo.getSourcepath();

		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setEnvironment(null, sourcepath, null, true);
		parser.setUnitName(file.getName());
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setSource(fileinfo.getSb().toString().toCharArray());

		@SuppressWarnings("unchecked")
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(options);

		try {
			CompilationUnit unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
			unit.accept(new ASTVisitorImpl());
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return;
		}
    }

    /**
     * Regist.
     *
     * @param file the file
     */
    private void regist(final File file) {
		// ディレクトリの場合
		if (file.isDirectory()) {
			File[] subfiles = file.listFiles();
			for (int i = 0; i < subfiles.length; i++) {
				regist(subfiles[i]);
			}
		}

		// ファイルの場合
		else if (file.isFile()) {
			if (file.getName().endsWith(".java")) {
				System.out.println(index + " Reading " + file.getAbsolutePath() + " . . .");
				index++;
				read(file);
			}
		}
		// ディレクトリでもファイルでもない場合は不正と表示し，無視
		else {
			System.err.println(file.getAbsolutePath() + " is invaild");
		}
	}

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String args[]) {
    	if (args.length == 0) {
	    	System.out.println("Specify a source file or directory.");
	    	System.exit(1);
    	}
		SourceReader sr = new SourceReader();
		sr.db = new DB();
		sr.db.open(new File(args[0]), false);
		sr.regist(new File(args[1]));
		sr.db.close();
    }

    /**
     * VisitorパターンでASTの内容を表示する.
     */
    class ASTVisitorImpl extends ASTVisitor {
    	
	    /** The statement list. */
	    private List<Value> statementList = null;
		
		/** The stack. */
		private Stack<List<Value>> stack = new Stack<List<Value>>();
    
    	/* (non-Javadoc)
	     * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ClassInstanceCreation)
	     */
	    public boolean visit(ClassInstanceCreation node) {
			String classname = "";
    		Type classtype = node.getType();
    		if (classtype != null) {
    			ITypeBinding type = classtype.resolveBinding();
    			if (type != null) {
					if (type.isArray()) {
						classname = DB.ARRAYNAME;
					} else {
						classname = type.getQualifiedName();
					}
    			} else {
    				if (classtype.isSimpleType()) {
						classname = ((SimpleType)classtype).getName().getFullyQualifiedName();
    				} else if (classtype.isQualifiedType()) {
						classname = ((QualifiedType)classtype).getName().getFullyQualifiedName();
    				} else
    					;
    			}
    		} else {
    			System.err.println("cannot get type of new statement.");
    			System.exit(1);
    		}
    		if (statementList != null) {
    			statementList.add(new Value(classname, "<init>"));
    		}
			return super.visit(node);
    	}

		/* (non-Javadoc)
		 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodInvocation)
		 */
		public boolean visit(MethodInvocation node) {
			String classname = "";
    		Expression exp = node.getExpression();
    		if (exp != null) {
    			ITypeBinding type = exp.resolveTypeBinding();
    			if (type != null) {
					if (type.isArray()) {
						classname = DB.ARRAYNAME;
					} else {
						classname = type.getQualifiedName();
					}
    			} else {
    				if (exp.getNodeType() == ASTNode.SIMPLE_NAME) {
						classname = ((SimpleName)exp).getIdentifier();
    					IBinding bind = ((SimpleName)exp).resolveBinding();
    					if (bind != null) {
    						System.out.print("@@@");
    					}
    				} else if (exp.getNodeType() == ASTNode.QUALIFIED_NAME) {
						classname = ((QualifiedName)exp).getFullyQualifiedName();
    				} else
    					;
    			}
    		} else {
    			classname = myClassname;
    		}
    		if (statementList != null) {
    			statementList.add(new Value(classname, node.getName().toString()));
    		}
			return super.visit(node);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
		 */
		@Override
    	public boolean visit(MethodDeclaration node) {
/*
 	    	Block body = node.getBody();
	    	ms.gundam.astparser.ASTParser parser = new ms.gundam.astparser.ASTParser();
		    if (body != null) {
				for (Object statement : body.statements()) {
					parser.addStatement((Statement)statement, ATTRIBUTE.NORMAL);
				}
			}
		    StringBuilder sourcestr = new StringBuilder(node.getReturnType2() == null ? "" :  node.getReturnType2() + " ");
		    sourcestr.append(node.getName().getFullyQualifiedName());
		    sourcestr.append("(){");
    		for (Token token : parser.getTokens()) {
    			sourcestr.append(token.getName());
    			if (token.dump().charAt(0) == 'M') {
    				if (sourcestr.charAt(sourcestr.length()-2) == ' ') {
    					sourcestr.deleteCharAt(sourcestr.length()-2);
    				}
    			} else {
    				sourcestr.append(" ");
    			}
    		}
    		sourcestr.append("}\n");
    		System.out.print(Formatter.format(sourcestr.toString()));
 */
//			System.out.println(node.getName().getFullyQualifiedName() + "{");
			if (statementList != null) {
				stack .push(statementList);
			}
			statementList = new ArrayList<Value>();
		    return super.visit(node);
        }

		/* (non-Javadoc)
		 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.MethodDeclaration)
		 */
		@Override
		public void endVisit(MethodDeclaration node) {
//			System.out.println("}");
			if (statementList == null) {
				super.endVisit(node);
				return;
			}

			final int size = statementList.size();
			for (int i = 0; i < size - 1; i++) {
				Value statement = statementList.get(i);
				for (int j = i + 1; j < size; j++) {
					Value afterStatement = statementList.get(j);
					db.put(statement.getClassname(), statement.getMethodname(), afterStatement.getClassname(), afterStatement.getMethodname());
//					System.out.println(statement.getClassname()+"#"+statement.getMethodname()+" "+afterStatement.getClassname()+"#"+afterStatement.getMethodname());
				}
			}
			
			if (stack.empty()) {
				statementList = null;
			} else {
				statementList = stack.pop();
			}
			super.endVisit(node);
		}
		
    }
}
