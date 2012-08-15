package ms.gundam.astparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import ms.gundam.astparser.DB;
import ms.gundam.astparser.Value;
import ms.gundam.astparser.ValuewithRanking;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
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

public class DBReaderMulti {
	private String myClassname = null;
	final private static String directory = "/DB/mDB";
	private DB db;
	private int ntokens = 5;
	private List<Integer> oklist = null;
	private int ngcount = 0;
	private int ignorecount = 0;
	private boolean ignoreLowRanking = false;
	
    class ASTVisitorImpl extends ASTVisitor {
    	private Stack<List<Value>> stack = new Stack<List<Value>>();
    	private List<Value> statementList = null;
    
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
    		if (getStatementList() != null) {
    			getStatementList().add(new Value(classname, "<init>"));
    		}
			return super.visit(node);
    	}
    	
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
    		if (getStatementList() != null) {
    			getStatementList().add(new Value(classname, node.getName().toString()));
    		}
			return super.visit(node);
		}

		@Override
    	public boolean visit(MethodDeclaration node) {
			if (statementList != null) {
				stack.push(statementList);
			}
			statementList = new ArrayList<Value>();
		    return super.visit(node);
        }

		@Override
		public void endVisit(MethodDeclaration node) {
			Map<String, ValuewithRanking> proposalmap = new HashMap<String, ValuewithRanking>();
			if (getStatementList() == null || getStatementList().size() <= ntokens) {
				statementList = null;
				super.endVisit(node);
				return;
			}

			// 先頭からnトークンいれた後にちゃんと候補がでるかしらべる
			int x = ntokens - 1;// < getStatementList().size() ? (ntokens - 1) : (getStatementList().size() - 1); 

			// 自分と同じクラスは登録してないと出てこないので無視				
//System.out.println(myClassname.replaceFirst("([^.]*\\.[^.]*\\.).*", "$1"));
			if (getStatementList().get(x).getClassname().contains(myClassname.replaceFirst("([^.]*\\.[^.]*\\.).*", "$1"))) {
System.out.print(node.getName().getFullyQualifiedName() + "{");
System.out.println(getStatementList().get(x).getClassname() + "." + getStatementList().get(x).getMethodname());
System.out.println("###NG###,-1,");
System.out.println("}");
				ignorecount++;
				statementList = null;
				super.endVisit(node);
				return;
			}

System.out.print(node.getName().getFullyQualifiedName() + "{");

			for (Value key : getStatementList().subList(0, x)) {
				makeRanking(key, proposalmap, true);
			}

			List<ValuewithRanking> proposallist = new ArrayList<ValuewithRanking>();
			for (ValuewithRanking v: proposalmap.values()) {
				proposallist.add(v);
			}
			Collections.sort(proposallist, new MyComparator());
			int rank = 1;
System.out.println(getStatementList().get(x).getClassname() + "." + getStatementList().get(x).getMethodname());
			boolean flag=false;
			for (ValuewithRanking v : proposallist) {
				if (getStatementList().get(x).getClassname().equals(v.getClassname()) &&
					getStatementList().get(x).getMethodname().equals(v.getMethodname())) {
System.out.println("***OK***,"+ rank + "," + v.getClassname()+v.getMethodname());
//					String str = String.format("%3d(%3d) & %s.%s \\\\", rank, v.getPercentage(), v.getClassname(),v.getMethodname());
//System.out.println(str);
					oklist.add(rank);
					flag=true;
				}
				rank++;
			}
			if (flag == false) {
System.out.println("@@@NG@@@,0,");
				ngcount++;
			}
			if (stack.empty()) {
				statementList = null;
			} else {
				statementList = stack.pop();
			}
System.out.println("}");
			super.endVisit(node);
		}

		public List<Value> getStatementList() {
			return statementList;
		}
    }

	private void makeRanking(Value key, Map<String, ValuewithRanking> proposalmap, boolean which) {
		int ranking = 0;
		int count = -1;

		List<Value> result = db.get(key.getClassname(), key.getMethodname(), which);
		Collections.sort(result);
		long sum = 0;
		for (Value v: result) {
			sum += v.getCount();  
		}
//System.out.println(key.getClassname()+key.getMethodname());			
		/*
		 * 出現数順にランキングを1位からつける．すでにマップにある場合はランキングを統合する
		 */
		for (Value v : result) {
			if (count != v.getCount()) {
				count = v.getCount();
				ranking++;
			}
			int percentage = (int) (v.getCount() * 100 / sum);
			if (percentage == 0 && ignoreLowRanking)
				continue;
			ValuewithRanking newvalue = new ValuewithRanking(v);
			newvalue.setPercentage(percentage);
			newvalue.setRanking(ranking);

			String keyString = newvalue.getClassname()+"#"+newvalue.getMethodname(); 
//System.out.println(newvalue.getRanking()+"("+percentage+"%):"+ keyString);			

			if (proposalmap.containsKey(keyString)) {
				ValuewithRanking value = proposalmap.get(keyString);
				value.setRanking(value.getRanking() + ranking);
				value.setPercentage(value.getPercentage() + percentage);
				proposalmap.put(keyString, value);
			} else {
				proposalmap.put(keyString, newvalue);
			}
		}
	}
	
	int index = 1;
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
				getList(file);
				index++;
			}
		}
		// ディレクトリでもファイルでもない場合は不正と表示し，無視
		else {
			System.err.println(file.getAbsolutePath() + " is invaild");
		}
	}
    private void register(final File file) {
    	List<Integer> nlist = new ArrayList<Integer>();
    	nlist.add(5);
    	nlist.add(10);
    	nlist.add(15);
    	nlist.add(20);
    	for (int ntokens : nlist) {
    		oklist = new ArrayList<Integer>();
    		ngcount = 0;
    		ignorecount = 0;
    		index = 1;
    		this.ntokens = ntokens; 
    		regist(file);
    		// 上位5位以内にはいる割合を調べたい．
    		int sum5 = 0;
    		int sum10 = 0;
    		int sum15 = 0;
    		int sum20 = 0;
    		for (int n : oklist) {
    			if (n <= 5) {
    				sum5++;
    			}
    			if (n <= 10) {
    				sum10++;
    			}
    			if (n <= 15) {
    				sum15++;
    			}
    			if (n <= 20) {
    				sum20++;
    			}
    		}
    		System.out.println(ntokens + "tokens OK:(" + oklist.size() + ")("+ sum5+","+sum10+","+sum15 +","+sum20+ ") NG:("+ ngcount + ") IGNORE:(" + ignorecount + ")");
    	}
    }

    public static void main(String args[]) {
		DBReaderMulti m = new DBReaderMulti();
		m.db = new DB();
		m.db.open(new File(args[0]), true);
		m.register(new File(args[1]));
		m.db.close();
	}

	private void getList(File file) {
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
		
		CompilationUnit unit = (CompilationUnit) parser.createAST(null);
		ASTVisitorImpl visitor= new ASTVisitorImpl();
		unit.accept(visitor);

	}
}

class MyComparator implements Comparator<ValuewithRanking> {  
	@Override
	public int compare(ValuewithRanking o1, ValuewithRanking o2) {
		if (o1.getPercentage() == o2.getPercentage()) {
			return 0;
		} else if (o1.getPercentage() > o2.getPercentage()) {
			return -1;
		} else {
			return 1;
		}
	}
}  
