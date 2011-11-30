import java.util.Iterator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;

/**
 * Javaソースを解析するリーダーです。
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class SourceReader {

    private static final Logger logger = Logger.getLogger(SourceReader.class);

    private final String element;

    public static void main(String args[]) {
    int aa[];
    aa = new int[5][][];
	if (args.length == 0) {
	    System.out.println("Specify a source file.");
	    System.exit(1);
	}
	try {
	    logger.setLevel(Level.ALL);
	    System.out.println("Reading " + args[0] + " . . .");
	    StringBuffer sb = new StringBuffer();
	    BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(args[0])));
	    String line;
	    while( (line = br.readLine()) != null ){
		sb.append(line+"\n");
	    }
	    SourceReader sr = new SourceReader(sb.toString());
	    sr.read();
	} catch ( FileNotFoundException e) {
	    System.out.println("File " + args[0] + " not found.");
	    return;
	} catch ( IOException e ){
	    System.out.println("IO Exception !");
	    return;
	}
    }

    public SourceReader(String elemen) {
	this.element = elemen;
    }

    /**
     * 渡されたソースコードの解析を行います。
     */
    public void read() {
	System.out.println("read() - start");
    
	ASTParser parser = ASTParser.newParser(AST.JLS3);
	// parser.setResolveBindings(true);
	parser.setSource(element.toCharArray());
    
	CompilationUnit unit = (CompilationUnit) parser
	    .createAST(new NullProgressMonitor());
    
	unit.accept(new ASTVisitorImpl());
    
	/*
	System.out.println("ファイル名: " + element.getElementName());// ファイル名
	String sourceName = element.getElementName().substring(0,
							       element.getElementName().lastIndexOf("."));
	System.out.println("クラス名: " + sourceName);
	System.out.println("クラスの完全修飾クラス名: "
		     + element.getType(sourceName).getFullyQualifiedName());
	System.out.println("パッケージ名: " + element.getParent().getElementName());
	*/
	System.out.println("read() - end");
    }

    /**
     * ソースを走査するVisitorの実装クラスです。
     * 
     * @author Masatomi KINO
     * @version $Revision$
     */
    class ASTVisitorImpl extends ASTVisitor {
	private final Logger logger = Logger.getLogger(ASTVisitorImpl.class);

	/*
	public boolean visit(Javadoc node) {
	    System.out.println("visit(Javadoc) - start");
	    System.out.println(node);
	    Iterator iterator = node.tags().iterator();
	    while (iterator.hasNext()) {
		TagElement element = (TagElement) iterator.next();
		System.out.println("型:" + element.getClass().getName());
		System.out.println("tagname: " + element.getTagName());
		System.out.println(element);
	    }
	    System.out.println("visit(Javadoc) - end");
	    return super.visit(node);
	}
	*/
        @Override
        public boolean visit(Assignment node) {
	    System.out.println("ASSI***");
	    Expression left = node.getRightHandSide();
	    if (left instanceof Name) {
		System.out.println("*"+((Name)left).getFullyQualifiedName());
		IBinding bind = ((Name)left).resolveBinding();
		System.out.println("*"+bind);
	    }
	    return super.visit(node);
	}

        @Override
        public boolean visit(MethodDeclaration node) {
	    StringBuffer sb = new StringBuffer();

	    //Modifiers
	    sb.append(node.modifiers()+" ");
	    sb.append(" ");

	    // Return Type
	    if( !node.isConstructor() ){
		sb.append(node.getReturnType2().toString() );
		sb.append(" ");
	    }

	    // Parameters
	    sb.append(node.getName().toString());
	    sb.append("(");
	    sb.append(node.parameters()+", ");
	    sb.append(")");

	    System.out.println(sb);

	    Block body = node.getBody();
	    if (body != null) {
		for (Object statement : body.statements()) {
		    System.out.println("!"+((Statement)statement).toString());
		    System.out.println(((Statement)statement).getNodeType());
		}
	    }
	    return super.visit(node);
        }
    }
}