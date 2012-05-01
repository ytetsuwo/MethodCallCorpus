package ms.gundam.astparser.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ms.gundam.astparser.DB;
import ms.gundam.astparser.Value;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

public class ASTParserProposalComputer implements IJavaCompletionProposalComputer {
	private int targetmethodoffset;
	private String myClassname = null;
	final private String directory = "/DB/mDB";
	private DB db;

	class MethodVisitor extends ASTVisitor {
    	private List<Value> statementList = new ArrayList<Value>();
    
		public List<Value> getStatementList() {
			return statementList;
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
    }

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sessionStarted() {
		db = new DB();
		db.open(new File(directory), true);
	}

	@Override
	public void sessionEnded() {
	}

	private List<Value> fetchSource(List<Value> list) {
		final List<Value> ret = new ArrayList<Value>();
		Value key = list.get(list.size()-1);
System.out.println(key.getClassname() + "#" + key.getMethodname());
		List<Value> result = db.get(key.getClassname(), key.getMethodname(), true);
		for (Value v : result) {
//System.out.println("\t"+ v.getClassname() + "#" + v.getMethodname());
			ret.add(v);
		}
		return ret;
	}
	
	@Override
	public List<ICompletionProposal> computeCompletionProposals(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		final List<ICompletionProposal> list = new ArrayList<ICompletionProposal>();
		final int offset = context.getInvocationOffset();
		IMethod method = null;

		try {
			final IJavaElement element = ((JavaContentAssistInvocationContext)context).getCoreContext().getEnclosingElement();
			if (element == null) {
				return list;
			}
			method = (IMethod)element.getAncestor(IJavaElement.METHOD);
			if (method == null) {
				return list;
			}
			targetmethodoffset = method.getSourceRange().getOffset();
		} catch (UnsupportedOperationException e) {
			return list;
		} catch (JavaModelException e) {
			e.printStackTrace();
			return list;
		}
		
		ICompilationUnit cu = method.getCompilationUnit();
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		parser.setSource(cu);
		CompilationUnit unit = (CompilationUnit) parser.createAST(null);
		MethodVisitor visitor= new MethodVisitor();
		unit.accept(visitor);

		if (visitor.getStatementList() != null && visitor.getStatementList().size() != 0) {
			List<Value> proposallist = fetchSource(visitor.getStatementList());
			Collections.sort(proposallist);
			for (Value v : proposallist) {
				String str = v.getCount() + " " + v.getClassname() + "." + v.getMethodname();
				list.add(new CompletionProposal(str, offset, 0, 0, null, str, null, "<pre>"+str+"</pre>"));
			}
		}
		return list;
	}

	@Override
	public List<IContextInformation> computeContextInformation(
			ContentAssistInvocationContext arg0, IProgressMonitor arg1) {
		return null;
	}

}
