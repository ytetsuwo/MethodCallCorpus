package ms.gundam.astparser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ms.gundam.astparser.token.Token;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;


/**
 *  
 * @author tetsuo
 *
 */
public class SourceReader {

    private final String sourcecode;
    private List<AttributedToken> list = new ArrayList<AttributedToken>(); 

    public SourceReader(String sourcecode) {
    	this.sourcecode = sourcecode;
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
	    	ms.gundam.astparser.ASTParser parser = new ms.gundam.astparser.ASTParser();
		    if (body != null) {
				for (Object statement : body.statements()) {
					parser.addStatement((Statement)statement, ATTRIBUTE.NORMAL);
				}
			}
    		System.out.print(node.getName().getFullyQualifiedName() + ">>>");
    		for (Token token : parser.getTokens()) {
    			System.out.print(token.dump() + " ");
    		}
   			System.out.println();
		    return super.visit(node);
        }
    }
}
