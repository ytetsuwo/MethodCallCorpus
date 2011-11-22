package ms.gundam.astparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ASTParser {
	private List<AttributedToken> tokenlist;
	
	public List<Token> getTokens() {
		final List<Token> newList = new ArrayList<Token>();
		for (final AttributedToken attributedToken : tokenlist) {
			newList.add(attributedToken.getToken());
		}
		return newList;
	}

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
		tokenlist.add(new AttributedToken(new Keyword("for"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addSingleVariableDeclaration(statement.getParameter(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
	}

	public void addWhileStatement(WhileStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("while"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
	}

	@SuppressWarnings("unchecked")
	public void addVariableDeclarationStatement(VariableDeclarationStatement statement, ATTRIBUTE attribute) {
		List<IExtendedModifier> modlist = statement.modifiers();
		if (modlist != null && !modlist.isEmpty()) {
			addIExtendedModifier(modlist.get(0), attribute);
			for (int i = 1; i < modlist.size(); i++) {
				addIExtendedModifier(modlist.get(i), attribute);
			}
		}
		addType(statement.getType(), attribute);
		List<VariableDeclarationFragment> varlist = statement.fragments();
		if (varlist != null && !varlist.isEmpty()) {
			addVariableDeclarationFragment(varlist.get(0), attribute);
			for (int i = 1; i < varlist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addVariableDeclarationFragment(varlist.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addTypeDeclarationStatement(TypeDeclarationStatement statement, ATTRIBUTE attribute) {
		addAbstractTypeDeclaration(statement.getDeclaration(), attribute);
	}

	@SuppressWarnings("unchecked")
	public void addTryStatement(TryStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("try"), attribute, tokenlist.size()));
		List<VariableDeclarationExpression> varlist = statement.resources();
		if (varlist != null && !varlist.isEmpty()) {
			tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
			addVariableDeclarationExpression(varlist.get(0), attribute);
			for (int i = 1; i < varlist.size(); i++) {
				tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
				addVariableDeclarationExpression(varlist.get(i), attribute);
			}
			tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		}
		addBlock(statement.getBody(), attribute);
		List<CatchClause> catchlist = statement.catchClauses();
		if (catchlist != null && !catchlist.isEmpty()) {
			addCatchClause(catchlist.get(0), attribute);
			for (int i = 1; i < catchlist.size(); i++) {
				addCatchClause(catchlist.get(i), attribute);
			}
		}
		Block finalblock = statement.getFinally();
		if (finalblock != null) {
			tokenlist.add(new AttributedToken(new Keyword("finally"), attribute, tokenlist.size()));
			addBlock(finalblock, attribute);
		}
	}

	public void addThrowStatement(ThrowStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("throw"), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addSynchronizedStatement(SynchronizedStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("synchronized"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addBlock(statement.getBody(), attribute);
	}

	@SuppressWarnings("unchecked")
	public void addSwtichStatement(SwitchStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("switch"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("{"), attribute, tokenlist.size()));
		List<Statement> switchlist = statement.statements();
		if (switchlist != null && !switchlist.isEmpty()) {
			addStatement(switchlist.get(0), attribute);
			for (int i = 1; i < switchlist.size(); i++) {
				addStatement(switchlist.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("}"), attribute, tokenlist.size()));
	}

	public void addSwitchCase(SwitchCase statement, ATTRIBUTE attribute) {
		if (statement.isDefault()) {
			tokenlist.add(new AttributedToken(new Keyword("default"), attribute, tokenlist.size()));
			tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
		} else {
			tokenlist.add(new AttributedToken(new Keyword("case"), attribute, tokenlist.size()));
			addExpression(statement.getExpression(), attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
		}
	}

	@SuppressWarnings("unchecked")
	public void addSuperConstructorInvocation(SuperConstructorInvocation statement, ATTRIBUTE attribute) {
		Expression exp = statement.getExpression();
		if (exp != null) {
			addExpression(exp, attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		}
		tokenlist.add(new AttributedToken(new Keyword("super"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		List<Expression> arglist = statement.arguments();
		if (arglist != null && !arglist.isEmpty()) {
			addExpression(arglist.get(0), attribute);
			for (int i = 1; i < arglist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addExpression(arglist.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addReturnStatement(ReturnStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("return"), attribute, tokenlist.size()));
		Expression exp = statement.getExpression();
		if (exp != null) {
			addExpression(exp, attribute);
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addLabeledStatement(LabeledStatement statement, ATTRIBUTE attribute) {
		addSimpleName(statement.getLabel(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
	}

	public void addIfStatement(IfStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("if"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addStatement(statement.getThenStatement(), attribute);
		Statement elsestatement = statement.getElseStatement();
		if (elsestatement != null) {
			tokenlist.add(new AttributedToken(new Keyword("else"), attribute, tokenlist.size()));
			addStatement(elsestatement, attribute);
		}
	}

	@SuppressWarnings("unchecked")
	public void addForStatement(ForStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("for"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		List<Expression> forinit = statement.initializers();
		if (forinit != null && !forinit.isEmpty()) {
			addExpression(forinit.get(0), attribute);
			for (int i = 1; i < forinit.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addExpression(forinit.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
		Expression exp = statement.getExpression();
		if (exp != null) {
			addExpression(exp, attribute);
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
		List<Expression> forupdate = statement.updaters();
		if (forupdate != null && !forupdate.isEmpty()) {
			addExpression(forupdate.get(0), attribute);
			for (int i = 1; i < forupdate.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addExpression(forupdate.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
	}

	public void addExpressionStatement(ExpressionStatement statement, ATTRIBUTE attribute) {
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addEmptyStatement(EmptyStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addDoStatement(DoStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("do"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
		tokenlist.add(new AttributedToken(new Keyword("while"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	public void addContinueStatement(ContinueStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("continue"), attribute, tokenlist.size()));
		addSimpleName(statement.getLabel(), attribute);
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	public void addConstructorInvocation(ConstructorInvocation statement, ATTRIBUTE attribute) {
		List<Type> typelist = statement.typeArguments();
		if (typelist != null && !typelist.isEmpty()) {
			addType(typelist.get(0), attribute);
			for (int i = 1; i < typelist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addType(typelist.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new Keyword("this"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		List<Expression> expressionlist = statement.arguments();
		if (expressionlist != null && !expressionlist.isEmpty()) {
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
		addSimpleName(statement.getLabel(), attribute);
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

	private void addSimpleName(SimpleName label, ATTRIBUTE attribute) {
		if (label != null) {
			tokenlist.add(new AttributedToken(new Identifier(label.getIdentifier()), attribute, tokenlist.size()));
		}
	}

	public void addExpression(Expression expression, ATTRIBUTE attribute) {
		switch(expression.getNodeType()) {
		case ASTNode.NORMAL_ANNOTATION:
		case ASTNode.MARKER_ANNOTATION:
		case ASTNode.SINGLE_MEMBER_ANNOTATION:
			addAnnotation((Annotation)expression, attribute);
			break;
		case ASTNode.ARRAY_ACCESS:
			addArrayAccess((ArrayAccess)expression, attribute);
			break;
		case ASTNode.ARRAY_CREATION:
			addArrayCreation((ArrayCreation)expression, attribute);
			break;
		case ASTNode.ARRAY_INITIALIZER:
			addArrayInitializer((ArrayInitializer)expression, attribute);
			break;
		case ASTNode.ASSIGNMENT:
			addAssignment((Assignment)expression, attribute);
			break;
		case ASTNode.BOOLEAN_LITERAL:
			addBooleanLiteral((BooleanLiteral)expression, attribute);
			break;
		case ASTNode.CAST_EXPRESSION:
			addCaseExpression((CastExpression)expression, attribute);
			break;
		case ASTNode.CLASS_INSTANCE_CREATION:
			addClassInstanceCreation((ClassInstanceCreation)expression, attribute);
			break;
		case ASTNode.CONDITIONAL_EXPRESSION:
			addConditionalExpression((ConditionalExpression)expression, attribute);
			break;
		case ASTNode.FIELD_ACCESS:
			addFieldAccess((FieldAccess)expression, attribute);
			break;
		case ASTNode.INFIX_EXPRESSION:
			addInfixExpression((InfixExpression)expression, attribute);
			break;
		case ASTNode.INSTANCEOF_EXPRESSION:
			addInstanceofExpression((InstanceofExpression)expression, attribute);
			break;
		case ASTNode.METHOD_INVOCATION:
			addMethodInvocation((MethodInvocation)expression, attribute);
			break;
		case ASTNode.SIMPLE_NAME:
		case ASTNode.QUALIFIED_NAME:
			addName((Name)expression, attribute);
			break;
		case ASTNode.NULL_LITERAL:
			addNullLiteral((NullLiteral)expression, attribute);
			break;
		case ASTNode.NUMBER_LITERAL:
			addNumberLiteral((NumberLiteral)expression, attribute);
			break;
		case ASTNode.PARENTHESIZED_EXPRESSION:
			addParenthesizedExpression((ParenthesizedExpression)expression, attribute);
			break;
		case ASTNode.POSTFIX_EXPRESSION:
			addPostfixExpression((PostfixExpression)expression, attribute);
			break;
		case ASTNode.PREFIX_EXPRESSION:
			addPrefixExpression((PrefixExpression)expression, attribute);
			break;
		case ASTNode.STRING_LITERAL:
			addStringLiteral((StringLiteral)expression, attribute);
			break;
		case ASTNode.SUPER_FIELD_ACCESS:
			addSuperFieldAccess((SuperFieldAccess)expression, attribute);
			break;
		case ASTNode.SUPER_METHOD_INVOCATION:
			addSuperMethodInvocation((SuperMethodInvocation)expression, attribute);
			break;
		case ASTNode.THIS_EXPRESSION:
			addThisExpression((ThisExpression)expression, attribute);
			break;
		case ASTNode.TYPE_LITERAL:
			addTypeLiteral((TypeLiteral)expression, attribute);
			break;
		case ASTNode.VARIABLE_DECLARATION_EXPRESSION:
			addVariableDeclarationExpression((VariableDeclarationExpression)expression, attribute);
			break;
		default:
			throw new IllegalStateException();
		}
	}

	private void addVariableDeclarationExpression(VariableDeclarationExpression expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addTypeLiteral(TypeLiteral expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addThisExpression(ThisExpression expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addSuperMethodInvocation(SuperMethodInvocation expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addSuperFieldAccess(SuperFieldAccess expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addStringLiteral(StringLiteral expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addPrefixExpression(PrefixExpression expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addPostfixExpression(PostfixExpression expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addParenthesizedExpression(ParenthesizedExpression expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addNumberLiteral(NumberLiteral expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addNullLiteral(NullLiteral expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addName(Name expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addMethodInvocation(MethodInvocation expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addInstanceofExpression(InstanceofExpression expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addInfixExpression(InfixExpression expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addFieldAccess(FieldAccess expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addConditionalExpression(ConditionalExpression expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addClassInstanceCreation(ClassInstanceCreation expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addCaseExpression(CastExpression expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addBooleanLiteral(BooleanLiteral expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addAssignment(Assignment expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addArrayInitializer(ArrayInitializer expression,
			ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addArrayCreation(ArrayCreation expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addArrayAccess(ArrayAccess expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addAnnotation(Annotation expression, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addType(Type type, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addCatchClause(CatchClause catchClause, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addAbstractTypeDeclaration(AbstractTypeDeclaration declaration, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addIExtendedModifier(IExtendedModifier mod, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addVariableDeclarationFragment(VariableDeclarationFragment var, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

	private void addSingleVariableDeclaration(SingleVariableDeclaration parameter, ATTRIBUTE attribute) {
		// TODO Auto-generated method stub
		
	}

}
