package ms.gundam.astparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ASTParser {
	private List<AttributedToken> tokenlist;
	
	public List<AttributedToken> getTokenlist() {
		return tokenlist;
	}

	public ASTParser() {
		tokenlist = new ArrayList<AttributedToken>();
	}
	
	public void addStatement(Statement statement, ATTRIBUTE attribute) {
		switch (statement.getNodeType()) {
		case ASTNode.ASSERT_STATEMENT:
			addAssertStatement((AssertStatement)statement, attribute);
			break;
		case ASTNode.BLOCK:
			addBlock((Block)statement, attribute);
			break;
		case ASTNode.BREAK_STATEMENT:
			addBreakStatement((BreakStatement)statement, attribute);
			break;
		case ASTNode.CONSTRUCTOR_INVOCATION:
			addConstructorInvocation((ConstructorInvocation)statement, attribute);
			break;
		case ASTNode.CONTINUE_STATEMENT:
			addContinueStatement((ContinueStatement)statement, attribute);
			break;
		case ASTNode.DO_STATEMENT:
			addDoStatement((DoStatement)statement, attribute);
			break;
		case ASTNode.EMPTY_STATEMENT:
			addEmptyStatement((EmptyStatement)statement, attribute);
			break;
		case ASTNode.EXPRESSION_STATEMENT:
			addExpressionStatement((ExpressionStatement)statement, attribute);
			break;
		case ASTNode.FOR_STATEMENT:
			addForStatement((ForStatement)statement, attribute);
			break;
		case ASTNode.IF_STATEMENT:
			addIfStatement((IfStatement)statement, attribute);
			break;
		case ASTNode.LABELED_STATEMENT:
			addLabeledStatement((LabeledStatement)statement, attribute);
			break;
		case ASTNode.RETURN_STATEMENT:
			addReturnStatement((ReturnStatement)statement, attribute);
			break;
		case ASTNode.SUPER_CONSTRUCTOR_INVOCATION:
			addSuperConstructorInvocation((SuperConstructorInvocation)statement, attribute);
			break;
		case ASTNode.SWITCH_CASE:
			addSwitchCase((SwitchCase)statement, attribute);
			break;
		case ASTNode.SWITCH_STATEMENT:
			addSwtichStatement((SwitchStatement)statement, attribute);
			break;
		case ASTNode.SYNCHRONIZED_STATEMENT:
			addSynchronizedStatement((SynchronizedStatement)statement, attribute);
			break;
		case ASTNode.THROW_STATEMENT:
			addThrowStatement((ThrowStatement)statement, attribute);
			break;
		case ASTNode.TRY_STATEMENT:
			addTryStatement((TryStatement)statement, attribute);
			break;
		case ASTNode.TYPE_DECLARATION_STATEMENT:
			addTypeDeclarationStatement((TypeDeclarationStatement)statement, attribute);
			break;
		case ASTNode.VARIABLE_DECLARATION_STATEMENT:
			addVariableDeclarationStatement((VariableDeclarationStatement)statement, attribute);
			break;
		case ASTNode.WHILE_STATEMENT:
			addWhileStatement((WhileStatement)statement, attribute);
			break;
		case ASTNode.ENHANCED_FOR_STATEMENT:
			addEnhancedForStatement((EnhancedForStatement)statement, attribute);
			break;
		default:
			throw new IllegalStateException();
		}
	}

	public void addEnhancedForStatement(EnhancedForStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addWhileStatement(WhileStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addVariableDeclarationStatement(
			VariableDeclarationStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addTypeDeclarationStatement(
			TypeDeclarationStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addTryStatement(TryStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addThrowStatement(ThrowStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addSynchronizedStatement(SynchronizedStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addSwtichStatement(SwitchStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addSwitchCase(SwitchCase statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addSuperConstructorInvocation(SuperConstructorInvocation statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addReturnStatement(ReturnStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addLabeledStatement(LabeledStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addIfStatement(IfStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addForStatement(ForStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addExpressionStatement(ExpressionStatement statement,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addEmptyStatement(EmptyStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	public void addDoStatement(DoStatement statement, ATTRIBUTE attributeute) {
		// TODO Auto-generated method stub
		
	}

	public void addContinueStatement(ContinueStatement statement, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	public void addConstructorInvocation(ConstructorInvocation statement, ATTRIBUTE attribute) {
		List<Type> typelist = statement.typeArguments();
		if (!typelist.isEmpty()) {
			addType(typelist.get(0), attribute);
			for (int i = 1; i < typelist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addType(typelist.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new Keyword("this"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		List<Expression> expressionlist = statement.arguments();
		if (!expressionlist.isEmpty()) {
			addExpression(expressionlist.get(0), attribute);
			for (int i = 1; i < expressionlist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addExpression(expressionlist.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
	}

	public void addBreakStatement(BreakStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("break"), attribute, tokenlist.size()));
		SimpleName name = statement.getLabel();
		if (name != null) {
			tokenlist.add(new AttributedToken(new Identifier(name.getIdentifier()), attribute, tokenlist.size()));
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addBlock(Block body, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Miscellaneous("{"), attribute, tokenlist.size()));
		for (Object statement : body.statements()) {
			addStatement((Statement)statement, attribute);
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("}"), attribute, tokenlist.size()));
	}

	public void addAssertStatement(AssertStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("assert"), attribute, tokenlist.size()));
		addExpression((Expression)statement.getExpression(), attribute);
		Expression message = statement.getMessage();
		if (message != null) {
			tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
			addExpression((Expression)statement.getMessage(), attribute);
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addExpression(Expression expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addType(Type type, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

}
