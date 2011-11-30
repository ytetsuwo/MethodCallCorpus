package ms.gundam.astparser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;

/**
 *  
 * @author tetsuo
 *
 */
public class SourceReader {

    private final String sourcecode;
    private ms.gundam.astparser.ASTParser parser;
    private List<AttributedToken> list = new ArrayList<AttributedToken>(); 

    public SourceReader(String sourcecode) {
    	this.sourcecode = sourcecode;
    	parser = new ms.gundam.astparser.ASTParser();
    }

    public void read() {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setEnvironment(null, null, null, true);
		parser.setUnitName("Hello.java");
		parser.setSource(sourcecode.toCharArray());
    
		CompilationUnit unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
    
		unit.accept(new ASTVisitorImpl());
    }

    public static void main(String args[]) {
    	if (args.length == 0) {
	    	System.out.println("Specify a source file.");
	    	System.exit(1);
    	}
		try {
		    System.out.println("Reading " + args[0] + " . . .");
		    BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(args[0])));
		    StringBuffer sb = new StringBuffer();
		    String line;
		    while ((line = br.readLine()) != null){
		    	sb.append(line+"\n");
	    	}
		    SourceReader sr = new SourceReader(sb.toString());
		    sr.read();
		} catch (FileNotFoundException e) {
		    System.out.println("File " + args[0] + " not found.");
	    	System.exit(1);
		} catch (IOException e){
		    System.out.println("IO Exception !");
	    	System.exit(1);
		}
    }

    /**
     * VisitorパターンでASTの内容を表示する
     */
    class ASTVisitorImpl extends ASTVisitor {
		@Override
        public boolean visit(Assignment node) {
    		System.out.print("ASSIGN***: ");
		    Expression left = node.getLeftHandSide();
		    Expression right = node.getRightHandSide();
		    String lefttype = left.toString();
		    String righttype = right.toString();
		    if (left instanceof Name) {
		    	IBinding lbind = ((Name)left).resolveBinding();
		    	lefttype = lbind.toString();
		    }
		    if (right instanceof Name) {
		    	IBinding rbind = ((Name)right).resolveBinding();
		    	righttype = rbind.toString();
		    }
		    System.out.println(lefttype + " = " + righttype);
	    	return super.visit(node);
		}

    	@Override
    	public boolean visit(MethodDeclaration node) {
	    	Block body = node.getBody();
		    if (body != null) {
				for (Object statement : body.statements()) {
					parser.addStatement((Statement)statement, ATTRIBUTE.NORMAL);
				}
			}
    		System.out.print(node.getName().getFullyQualifiedName() + ">>>");
    		for (Token token : parser.getTokens()) {
    			System.out.print(token.dump() + " ");
    		}
		    return super.visit(node);
        }
    }
}
