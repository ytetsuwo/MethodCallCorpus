package ms.gundam.astparser.parser;

import java.util.ArrayList;
import java.util.List;

import ms.gundam.astparser.token.ATTRIBUTE;
import ms.gundam.astparser.token.AttributedToken;
import ms.gundam.astparser.token.Identifier;
import ms.gundam.astparser.token.Keyword;
import ms.gundam.astparser.token.Literal;
import ms.gundam.astparser.token.Miscellaneous;
import ms.gundam.astparser.token.SemiColon;
import ms.gundam.astparser.token.Token;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
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
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;


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

	private void addEnhancedForStatement(EnhancedForStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("for"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addSingleVariableDeclaration(statement.getParameter(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
	}

	private void addWhileStatement(WhileStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("while"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
	}

	@SuppressWarnings("unchecked")
	private void addVariableDeclarationStatement(VariableDeclarationStatement statement, ATTRIBUTE attribute) {
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

	private void addTypeDeclarationStatement(TypeDeclarationStatement statement, ATTRIBUTE attribute) {
		addAbstractTypeDeclaration(statement.getDeclaration(), attribute);
	}

	@SuppressWarnings("unchecked")
	private void addTryStatement(TryStatement statement, ATTRIBUTE attribute) {
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

	private void addThrowStatement(ThrowStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("throw"), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	private void addSynchronizedStatement(SynchronizedStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("synchronized"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addBlock(statement.getBody(), attribute);
	}

	@SuppressWarnings("unchecked")
	private void addSwtichStatement(SwitchStatement statement, ATTRIBUTE attribute) {
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

	private void addSwitchCase(SwitchCase statement, ATTRIBUTE attribute) {
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
	private void addSuperConstructorInvocation(SuperConstructorInvocation statement, ATTRIBUTE attribute) {
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

	private void addReturnStatement(ReturnStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("return"), attribute, tokenlist.size()));
		Expression exp = statement.getExpression();
		if (exp != null) {
			addExpression(exp, attribute);
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	private void addLabeledStatement(LabeledStatement statement, ATTRIBUTE attribute) {
		addSimpleName(statement.getLabel(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
	}

	private void addIfStatement(IfStatement statement, ATTRIBUTE attribute) {
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
	private void addForStatement(ForStatement statement, ATTRIBUTE attribute) {
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

	private void addExpressionStatement(ExpressionStatement statement, ATTRIBUTE attribute) {
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	private void addEmptyStatement(EmptyStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	private void addDoStatement(DoStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("do"), attribute, tokenlist.size()));
		addStatement(statement.getBody(), attribute);
		tokenlist.add(new AttributedToken(new Keyword("while"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(statement.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	private void addContinueStatement(ContinueStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("continue"), attribute, tokenlist.size()));
		addSimpleName(statement.getLabel(), attribute);
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	private void addConstructorInvocation(ConstructorInvocation statement, ATTRIBUTE attribute) {
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

	private void addBreakStatement(BreakStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("break"), attribute, tokenlist.size()));
		addSimpleName(statement.getLabel(), attribute);
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	private void addBlock(Block body, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Miscellaneous("{"), attribute, tokenlist.size()));
		for (Object statement : body.statements()) {
			addStatement((Statement)statement, attribute);
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("}"), attribute, tokenlist.size()));
	}

	private void addAssertStatement(AssertStatement statement, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("assert"), attribute, tokenlist.size()));
		addExpression((Expression)statement.getExpression(), attribute);
		Expression message = statement.getMessage();
		if (message != null) {
			tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
			addExpression((Expression)statement.getMessage(), attribute);
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	private void addQualifiedName(QualifiedName expression, ATTRIBUTE attribute) {
		addName(expression.getQualifier(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		addSimpleName(expression.getName(), attribute);
	}

	private void addSimpleName(SimpleName label, ATTRIBUTE attribute) {
		if (label != null) {
			tokenlist.add(new AttributedToken(new Identifier(label.getIdentifier()), attribute, tokenlist.size()));
		}
	}

	private void addExpression(Expression expression, ATTRIBUTE attribute) {
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
		case ASTNode.CHARACTER_LITERAL:
			addCharacterLiteral((CharacterLiteral)expression, attribute);
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

	@SuppressWarnings("unchecked")
	private void addVariableDeclarationExpression(VariableDeclarationExpression expression, ATTRIBUTE attribute) {
		List<IExtendedModifier> modlist = expression.modifiers();
		if (modlist != null && !modlist.isEmpty()) {
			addIExtendedModifier(modlist.get(0), attribute);
			for (int i = 1; i < modlist.size(); i++) {
				addIExtendedModifier(modlist.get(i), attribute);
			}
		}
		addType(expression.getType(), attribute);
		List<VariableDeclarationFragment> varlist = expression.fragments();
		if (varlist != null && !varlist.isEmpty()) {
			addVariableDeclarationFragment(varlist.get(0), attribute);
			for (int i = 1; i < varlist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addVariableDeclarationFragment(varlist.get(i), attribute);
			}
		}
	}

	private void addTypeLiteral(TypeLiteral expression, ATTRIBUTE attribute) {
		Type type = expression.getType();
		if (type != null) {
			addType(type, attribute);
		} else {
			tokenlist.add(new AttributedToken(new Keyword("void"), attribute, tokenlist.size()));
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Keyword("class"), attribute, tokenlist.size()));
	}

	private void addThisExpression(ThisExpression expression, ATTRIBUTE attribute) {
		Name name = expression.getQualifier();
		if (name != null) {
			addName(name, attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		}
		tokenlist.add(new AttributedToken(new Keyword("this"), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	private void addSuperMethodInvocation(SuperMethodInvocation expression, ATTRIBUTE attribute) {
		Name name = expression.getQualifier();
		if (name != null) {
			addName(name, attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		}
		tokenlist.add(new AttributedToken(new Keyword("super"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		List<Type> typelist = expression.typeArguments();
		if (typelist != null && !typelist.isEmpty()) {
			tokenlist.add(new AttributedToken(new Miscellaneous("<"), attribute, tokenlist.size()));
			addType(typelist.get(0), attribute);
			for (int i = 1; i < typelist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addType(typelist.get(i), attribute);
			}
			tokenlist.add(new AttributedToken(new Miscellaneous(">"), attribute, tokenlist.size()));
		}
		addSimpleName(expression.getName(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		List<Expression> explist = expression.arguments();
		if (explist != null && !explist.isEmpty()) {
			addExpression(explist.get(0), attribute);
			for (int i = 1; i < explist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addExpression(explist.get(i), attribute);
			}
			
		}
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
	}

	private void addSuperFieldAccess(SuperFieldAccess expression, ATTRIBUTE attribute) {
		Name name = expression.getQualifier();
		if (name != null) {
			addName(name, attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		}
		tokenlist.add(new AttributedToken(new Keyword("super"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		addSimpleName(expression.getName(), attribute);
	}

	private void addStringLiteral(StringLiteral expression, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Literal(expression.getEscapedValue()), attribute, tokenlist.size()));
	}

	private void addPrefixExpression(PrefixExpression expression, ATTRIBUTE attribute) {
		addPrefixExpressionOperator(expression.getOperator(), attribute);
		addExpression(expression.getOperand(), attribute);
	}

	private void addPostfixExpression(PostfixExpression expression, ATTRIBUTE attribute) {
		addExpression(expression.getOperand(), attribute);
		addPostfixExpressionOperator(expression.getOperator(), attribute);
	}

	private void addParenthesizedExpression(ParenthesizedExpression expression, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addExpression(expression.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
	}

	private void addNumberLiteral(NumberLiteral expression, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Literal(expression.getToken()), attribute, tokenlist.size()));
	}

	private void addNullLiteral(NullLiteral expression, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Literal("null"), attribute, tokenlist.size()));
	}

	private void addName(Name expression, ATTRIBUTE attribute) {
		if (expression.isSimpleName()) {
			addSimpleName((SimpleName)expression, attribute);
		} else if (expression.isQualifiedName()) {
			addQualifiedName((QualifiedName)expression, attribute);
		} else {
			throw new IllegalStateException();
		}
	}

	@SuppressWarnings("unchecked")
	private void addMethodInvocation(MethodInvocation expression, ATTRIBUTE attribute) {
		Expression exp = expression.getExpression();
		if (exp != null) {
			addExpression(exp, attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));

		}
 		List<Type> typelist = expression.typeArguments();
		if (typelist != null && !typelist.isEmpty()) {
			tokenlist.add(new AttributedToken(new Miscellaneous("<"), attribute, tokenlist.size()));
			addType(typelist.get(0), attribute);
			for (int i = 1; i < typelist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addType(typelist.get(i), attribute);
			}
			tokenlist.add(new AttributedToken(new Miscellaneous(">"), attribute, tokenlist.size()));
		}
		addSimpleName(expression.getName(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		List<Expression> explist = expression.arguments();
		if (explist != null && !explist.isEmpty()) {
			addExpression(explist.get(0), attribute);
			for (int i = 1; i < explist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addExpression(explist.get(i), attribute);
			}
			
		}
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
	}

	private void addInstanceofExpression(InstanceofExpression expression, ATTRIBUTE attribute) {
		addExpression(expression.getLeftOperand(), attribute);
		tokenlist.add(new AttributedToken(new Keyword("instanceof"), attribute, tokenlist.size()));
		addType(expression.getRightOperand(), attribute);
	}

	@SuppressWarnings("unchecked")
	private void addInfixExpression(InfixExpression expression, ATTRIBUTE attribute) {
		addExpression(expression.getLeftOperand(), attribute);
		addInfixExpressionOperator(expression.getOperator(), attribute);
		addExpression(expression.getRightOperand(), attribute);
		if (expression.hasExtendedOperands()) {
			List<Expression> list = expression.extendedOperands();
			addInfixExpressionOperator(expression.getOperator(), attribute);
			addExpression(list.get(0), attribute);
			for (int i = 1; i < list.size(); i++) {
				addInfixExpressionOperator(expression.getOperator(), attribute);
				addExpression(list.get(i), attribute);
			}
		}
	}

	private void addFieldAccess(FieldAccess expression, ATTRIBUTE attribute) {
		addExpression(expression.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		addSimpleName(expression.getName(), attribute);
	}

	private void addConditionalExpression(ConditionalExpression expression, ATTRIBUTE attribute) {
		addExpression(expression.getExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("?"), attribute, tokenlist.size()));
		addExpression(expression.getThenExpression(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(":"), attribute, tokenlist.size()));
		addExpression(expression.getElseExpression(), attribute);
	}

	@SuppressWarnings("unchecked")
	private void addClassInstanceCreation(ClassInstanceCreation expression, ATTRIBUTE attribute) {
		Expression exp = expression.getExpression();
		if (exp != null) {
			addExpression(exp, attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		}
		tokenlist.add(new AttributedToken(new Keyword("new"), attribute, tokenlist.size()));
		addType(expression.getType(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		List<Expression> explist = expression.arguments();
		if (explist != null && !explist.isEmpty()) {
			addExpression(explist.get(0), attribute);
			for (int i = 1; i < explist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addExpression(explist.get(i), attribute);
			}
			
		}
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		AnonymousClassDeclaration anon = expression.getAnonymousClassDeclaration();
		if (anon != null) {
			addAnonymousClassDeclaration(anon, attribute);
		}
	}

	private void addCharacterLiteral(CharacterLiteral expression, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Literal(expression.getEscapedValue()), attribute, tokenlist.size()));
	}

	private void addCaseExpression(CastExpression expression, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addType(expression.getType(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addExpression(expression.getExpression(), attribute);
	}

	private void addBooleanLiteral(BooleanLiteral expression, ATTRIBUTE attribute) {
		if (expression.booleanValue()) {
			tokenlist.add(new AttributedToken(new Literal("true"), attribute, tokenlist.size()));
		} else {
			tokenlist.add(new AttributedToken(new Literal("false"), attribute, tokenlist.size()));
		}
	}

	private void addAssignment(Assignment expression, ATTRIBUTE attribute) {
		addExpression(expression.getLeftHandSide(), attribute);
		addAssignmentOperator(expression.getOperator(), attribute);
		addExpression(expression.getRightHandSide(), attribute);
	}

	@SuppressWarnings("unchecked")
	private void addArrayInitializer(ArrayInitializer expression, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Miscellaneous("{"), attribute, tokenlist.size()));
		List<Expression> list = expression.expressions();
		if (list != null && !list.isEmpty()) {
			addExpression(list.get(0), attribute);
			for (int i = 1; i < list.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addExpression(list.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("}"), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	private void addArrayCreation(ArrayCreation expression, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("new"), attribute, tokenlist.size()));
		addType(expression.getType().getElementType(), attribute);
		List<Expression> list = expression.dimensions();
		if (list != null && !list.isEmpty()) {
			tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
			addExpression(list.get(0), attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
			for (int i = 1; i < list.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
				addExpression(list.get(i), attribute);
				tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
			}
		}
		int count = expression.getType().getDimensions() - list.size();
		if (count >= 0) {
			for (int i = 0; i < count; i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
				tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
			}
		}
		ArrayInitializer init = expression.getInitializer();
		if (init != null) {
			addArrayInitializer(init, attribute);
		}
	}

	private void addArrayAccess(ArrayAccess expression, ATTRIBUTE attribute) {
		addExpression(expression.getArray(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
		addExpression(expression.getIndex(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	private void addAnnotation(Annotation expression, ATTRIBUTE attribute) {
		if (expression.isNormalAnnotation()) {
			NormalAnnotation normal = (NormalAnnotation)expression;
			tokenlist.add(new AttributedToken(new Miscellaneous("@"), attribute, tokenlist.size()));
			addName(normal.getTypeName(), attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
			List<MemberValuePair> pairlist = normal.values();
			if (pairlist != null && !pairlist.isEmpty()) {
				MemberValuePair pair = pairlist.get(0);
				addSimpleName(pair.getName(), attribute);
				tokenlist.add(new AttributedToken(new Miscellaneous("="), attribute, tokenlist.size()));
				addExpression(pair.getValue(), attribute);
				for (int i = 1; i < pairlist.size(); i++) {
					tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
					addSimpleName(pairlist.get(i).getName(), attribute);
					tokenlist.add(new AttributedToken(new Miscellaneous("="), attribute, tokenlist.size()));
					addExpression(pairlist.get(i).getValue(), attribute);
				}
			}
			tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		} else if (expression.isMarkerAnnotation()){
			MarkerAnnotation marker = (MarkerAnnotation)expression;
			tokenlist.add(new AttributedToken(new Miscellaneous("@"), attribute, tokenlist.size()));
			addName(marker.getTypeName(), attribute);
		} else if (expression.isSingleMemberAnnotation()) {
			SingleMemberAnnotation single = (SingleMemberAnnotation)expression;
			tokenlist.add(new AttributedToken(new Miscellaneous("@"), attribute, tokenlist.size()));
			addName(single.getTypeName(), attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
			addExpression(single.getValue(), attribute);
			tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		} else {
			throw new IllegalStateException();
		}
	}

	private void addPrefixExpressionOperator(PrefixExpression.Operator operator, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new ms.gundam.astparser.token.Operator(operator.toString()), attribute, tokenlist.size()));
	}

	private void addPostfixExpressionOperator(PostfixExpression.Operator operator, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new ms.gundam.astparser.token.Operator(operator.toString()), attribute, tokenlist.size()));
	}

	private void addInfixExpressionOperator(InfixExpression.Operator operator, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new ms.gundam.astparser.token.Operator(operator.toString()), attribute, tokenlist.size()));
	}

	private void addAssignmentOperator(Assignment.Operator operator, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new ms.gundam.astparser.token.Operator(operator.toString()), attribute, tokenlist.size()));
	}

	private void addType(Type type, ATTRIBUTE attribute) {
		if (type.isArrayType()) {
			addArrayType((ArrayType)type, attribute);
		} else if (type.isParameterizedType()) {
			addParameterizedType((ParameterizedType)type, attribute);
		} else if (type.isPrimitiveType()) {
			addPrimitiveType((PrimitiveType)type, attribute);
		} else if (type.isQualifiedType()) {
			addQualifiedType((QualifiedType)type, attribute);
		} else if (type.isSimpleType()) {
			addSimpleType((SimpleType)type, attribute);
		} else if (type.isUnionType()) {
			addUnionType((UnionType)type, attribute);
		} else if (type.isWildcardType()) {
			addWildcardType((WildcardType)type, attribute);
		}
	}

	private void addWildcardType(WildcardType type, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Miscellaneous("?"), attribute, tokenlist.size()));
		Type boundtype = type.getBound();
		if (boundtype != null) {
			if (type.isUpperBound()) {
				tokenlist.add(new AttributedToken(new Keyword("extends"), attribute, tokenlist.size()));
			} else {
				tokenlist.add(new AttributedToken(new Keyword("super"), attribute, tokenlist.size()));
			}
			addType(boundtype, attribute);
		}
	}

	@SuppressWarnings("unchecked")
	private void addUnionType(UnionType type, ATTRIBUTE attribute) {
		List<Type> typelist =  type.types();
		if (typelist != null && !typelist.isEmpty()) {
			addType(typelist.get(0), attribute);
			for (int i = 1; i < typelist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous("|"), attribute, tokenlist.size()));
				addType(typelist.get(i), attribute);
			}
		}
	}

	private void addSimpleType(SimpleType type, ATTRIBUTE attribute) {
		addName(type.getName(), attribute);
	}

	private void addQualifiedType(QualifiedType type, ATTRIBUTE attribute) {
		addType(type.getQualifier(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("."), attribute, tokenlist.size()));
		addSimpleName(type.getName(), attribute);
	}

	private void addPrimitiveType(PrimitiveType type, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword(type.getPrimitiveTypeCode().toString()), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	private void addParameterizedType(ParameterizedType type, ATTRIBUTE attribute) {
		addType(type.getType(), attribute);
		List<Type> typelist =  type.typeArguments();
		if (typelist != null && !typelist.isEmpty()) {
			tokenlist.add(new AttributedToken(new Miscellaneous("<"), attribute, tokenlist.size()));
			addType(typelist.get(0), attribute);
			for (int i = 1; i < typelist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addType(typelist.get(i), attribute);
			}
			tokenlist.add(new AttributedToken(new Miscellaneous(">"), attribute, tokenlist.size()));
		}
	}

	private void addArrayType(ArrayType type, ATTRIBUTE attribute) {
		addType(type.getComponentType(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
	}

	private void addCatchClause(CatchClause catchClause, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword("catch"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		addSingleVariableDeclaration(catchClause.getException(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		addBlock(catchClause.getBody(), attribute);
	}

	private void addAbstractTypeDeclaration(AbstractTypeDeclaration declaration, ATTRIBUTE attribute) {
		switch(declaration.getNodeType()) {
		case ASTNode.TYPE_DECLARATION:
			addTypeDeclaration((TypeDeclaration)declaration, attribute);
			break;
		case ASTNode.ENUM_DECLARATION:
			addEnumDeclaration((EnumDeclaration)declaration, attribute);
			break;
		case ASTNode.ANNOTATION_TYPE_DECLARATION:
			addAnnotationTypeDeclaration((AnnotationTypeDeclaration)declaration, attribute);
			break;
		default:
			throw new IllegalStateException();
		}
	}

	private void addIExtendedModifier(IExtendedModifier mod, ATTRIBUTE attribute) {
		if (mod.isAnnotation()) {
			addAnnotation((Annotation)mod, attribute);
		}
		if (mod.isModifier()) {
			addModifier((Modifier)mod, attribute);
		}
		throw new IllegalStateException();
	}

	private void addModifier(Modifier mod, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Keyword(mod.getKeyword().toString()), attribute, tokenlist.size()));
	}

	private void addVariableDeclarationFragment(VariableDeclarationFragment var, ATTRIBUTE attribute) {
		addSimpleName(var.getName(), attribute);
		int num = var.getExtraDimensions();
		for (int i = 0; i < num; i++) {
			tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
			tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
		}
		Expression exp = var.getInitializer();
		if (exp != null) {
			tokenlist.add(new AttributedToken(new Miscellaneous("="), attribute, tokenlist.size()));
			addExpression(exp, attribute);
		}
	}

	@SuppressWarnings("unchecked")
	private void addSingleVariableDeclaration(SingleVariableDeclaration parameter, ATTRIBUTE attribute) {
		List<IExtendedModifier> modlist = parameter.modifiers();
		if (modlist != null && !modlist.isEmpty()) {
			addIExtendedModifier(modlist.get(0), attribute);
			for (int i = 1; i < modlist.size(); i++) {
				addIExtendedModifier(modlist.get(i), attribute);
			}
		}
		addType(parameter.getType(), attribute);
		if (parameter.isVarargs()) {
			tokenlist.add(new AttributedToken(new Miscellaneous("..."), attribute, tokenlist.size()));
		}
		addSimpleName(parameter.getName(), attribute);
		int num = parameter.getExtraDimensions();
		for (int i = 0; i < num; i++) {
			tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
			tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
		}
		Expression exp = parameter.getInitializer();
		if (exp != null) {
			tokenlist.add(new AttributedToken(new Miscellaneous("="), attribute, tokenlist.size()));
			addExpression(exp, attribute);
		}
	}

	@SuppressWarnings("unchecked")
	private void addAnonymousClassDeclaration(AnonymousClassDeclaration anon, ATTRIBUTE attribute) {
		tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
		List<BodyDeclaration> bodylist = anon.bodyDeclarations(); 
		if (bodylist != null && !bodylist.isEmpty()) {
			addBodyDeclaration(bodylist.get(0), attribute);
			for (int i = 1; i < bodylist.size(); i++) {
				addBodyDeclaration(bodylist.get(i), attribute);
			}
		}
	}

	private void addBodyDeclaration(BodyDeclaration bodyDeclaration, ATTRIBUTE attribute) {
		if (bodyDeclaration instanceof AbstractTypeDeclaration) {
			addAbstractTypeDeclaration((AbstractTypeDeclaration)bodyDeclaration, attribute);
		} else if (bodyDeclaration instanceof AnnotationTypeMemberDeclaration) {
			addAnnotationTypeMemberDeclaration((AnnotationTypeMemberDeclaration)bodyDeclaration, attribute);
		} else if (bodyDeclaration instanceof EnumConstantDeclaration) {
			addEnumConstantDeclaration((EnumConstantDeclaration)bodyDeclaration, attribute);
		} else if (bodyDeclaration instanceof FieldDeclaration) {
			addFieldDeclaration((FieldDeclaration)bodyDeclaration, attribute);
		} else if (bodyDeclaration instanceof Initializer) {
			addInitializer((Initializer)bodyDeclaration, attribute);
		} else if (bodyDeclaration instanceof MethodDeclaration) {
			addMethodDeclaration((MethodDeclaration)bodyDeclaration, attribute);
		} else {
			throw new IllegalStateException();
		}
	}

	@SuppressWarnings("unchecked")
	private void addMethodDeclaration(MethodDeclaration bodyDeclaration, ATTRIBUTE attribute) {
		List<IExtendedModifier> modlist = bodyDeclaration.modifiers();
		if (modlist != null && !modlist.isEmpty()) {
			addIExtendedModifier(modlist.get(0), attribute);
			for (int i = 1; i < modlist.size(); i++) {
				addIExtendedModifier(modlist.get(i), attribute);
			}
		}
		List<Type> typelist =  bodyDeclaration.typeParameters();
		if (typelist != null && !typelist.isEmpty()) {
			tokenlist.add(new AttributedToken(new Miscellaneous("<"), attribute, tokenlist.size()));
			addType(typelist.get(0), attribute);
			for (int i = 1; i < typelist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addType(typelist.get(i), attribute);
			}
			tokenlist.add(new AttributedToken(new Miscellaneous(">"), attribute, tokenlist.size()));
		}
		if (!bodyDeclaration.isConstructor()) {
			Type type = bodyDeclaration.getReturnType2();
			if (type != null) {
				addType(bodyDeclaration.getReturnType2(), attribute);
			}
		}
		addSimpleName(bodyDeclaration.getName(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		List<SingleVariableDeclaration> paramlist =  bodyDeclaration.parameters();
		if (paramlist != null && !paramlist.isEmpty()) {
			addSingleVariableDeclaration(paramlist.get(0), attribute);
			for (int i = 1; i < paramlist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addSingleVariableDeclaration(paramlist.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		int num = bodyDeclaration.getExtraDimensions();
		for (int i = 0; i < num; i++) {
			tokenlist.add(new AttributedToken(new Miscellaneous("["), attribute, tokenlist.size()));
			tokenlist.add(new AttributedToken(new Miscellaneous("]"), attribute, tokenlist.size()));
		}
		List<Name> explist =  bodyDeclaration.thrownExceptions();
		if (explist != null && !explist.isEmpty()) {
			tokenlist.add(new AttributedToken(new Keyword("throw"), attribute, tokenlist.size()));
			addName(explist.get(0), attribute);
			for (int i = 1; i < explist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addName(explist.get(i), attribute);
			}
		}
		addBlock(bodyDeclaration.getBody(), attribute);
	}

	@SuppressWarnings("unchecked")
	private void addInitializer(Initializer bodyDeclaration, ATTRIBUTE attribute) {
		List<IExtendedModifier> modlist = bodyDeclaration.modifiers();
		if (modlist != null && !modlist.isEmpty()) {
			addIExtendedModifier(modlist.get(0), attribute);
			for (int i = 1; i < modlist.size(); i++) {
				addIExtendedModifier(modlist.get(i), attribute);
			}
		}
		addBlock(bodyDeclaration.getBody(), attribute);
	}

	@SuppressWarnings("unchecked")
	private void addFieldDeclaration(FieldDeclaration bodyDeclaration, ATTRIBUTE attribute) {
		List<IExtendedModifier> modlist = bodyDeclaration.modifiers();
		if (modlist != null && !modlist.isEmpty()) {
			addIExtendedModifier(modlist.get(0), attribute);
			for (int i = 1; i < modlist.size(); i++) {
				addIExtendedModifier(modlist.get(i), attribute);
			}
		}
		addType(bodyDeclaration.getType(), attribute);
		List<VariableDeclarationFragment> paramlist =  bodyDeclaration.fragments();
		if (paramlist != null && !paramlist.isEmpty()) {
			addVariableDeclarationFragment(paramlist.get(0), attribute);
			for (int i = 1; i < paramlist.size(); i++) {
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
				addVariableDeclarationFragment(paramlist.get(i), attribute);
			}
		}
		tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	private void addEnumConstantDeclaration(EnumConstantDeclaration bodyDeclaration, ATTRIBUTE attribute) {
		List<IExtendedModifier> modlist = bodyDeclaration.modifiers();
		if (modlist != null && !modlist.isEmpty()) {
			addIExtendedModifier(modlist.get(0), attribute);
			for (int i = 1; i < modlist.size(); i++) {
				addIExtendedModifier(modlist.get(i), attribute);
			}
		}
		addSimpleName(bodyDeclaration.getName(), attribute);
		List<Expression> paramlist =  bodyDeclaration.arguments();
		if (paramlist != null) {
			tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
			if (!paramlist.isEmpty()) {
				addExpression(paramlist.get(0), attribute);
				for (int i = 1; i < paramlist.size(); i++) {
					tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
					addExpression(paramlist.get(i), attribute);
				}
			}
			tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		}
		AnonymousClassDeclaration anon = bodyDeclaration.getAnonymousClassDeclaration();
		if (anon != null) {
			addAnonymousClassDeclaration(anon, attribute);
		}
	}

	@SuppressWarnings("unchecked")
	private void addAnnotationTypeMemberDeclaration(AnnotationTypeMemberDeclaration bodyDeclaration, ATTRIBUTE attribute) {
		List<IExtendedModifier> modlist = bodyDeclaration.modifiers();
		if (modlist != null && !modlist.isEmpty()) {
			addIExtendedModifier(modlist.get(0), attribute);
			for (int i = 1; i < modlist.size(); i++) {
				addIExtendedModifier(modlist.get(i), attribute);
			}
		}
		addType(bodyDeclaration.getType(), attribute);
		addSimpleName(bodyDeclaration.getName(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("("), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Miscellaneous(")"), attribute, tokenlist.size()));
		Expression exp = bodyDeclaration.getDefault();
		if (exp != null) {
			tokenlist.add(new AttributedToken(new Keyword("default"), attribute, tokenlist.size()));
		}
	}

	@SuppressWarnings("unchecked")
	private void addTypeDeclaration(TypeDeclaration declaration, ATTRIBUTE attribute) {
		{
			List<IExtendedModifier> modlist = declaration.modifiers();
			if (modlist != null && !modlist.isEmpty()) {
				addIExtendedModifier(modlist.get(0), attribute);
				for (int i = 1; i < modlist.size(); i++) {
					addIExtendedModifier(modlist.get(i), attribute);
				}
			}
		}
		if (declaration.isInterface()) {
			tokenlist.add(new AttributedToken(new Keyword("interface"), attribute, tokenlist.size()));
		} else {
			tokenlist.add(new AttributedToken(new Keyword("class"), attribute, tokenlist.size()));
		}
		addSimpleName(declaration.getName(), attribute);
		{
			List<Type> typelist =  declaration.typeParameters();
			if (typelist != null && !typelist.isEmpty()) {
				tokenlist.add(new AttributedToken(new Miscellaneous("<"), attribute, tokenlist.size()));
				addType(typelist.get(0), attribute);
				for (int i = 1; i < typelist.size(); i++) {
					tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
					addType(typelist.get(i), attribute);
				}
				tokenlist.add(new AttributedToken(new Miscellaneous(">"), attribute, tokenlist.size()));
			}
		}
		if (declaration.isInterface()) {
			{
				List<Type> interfacelist =  declaration.superInterfaceTypes();
				if (interfacelist != null && !interfacelist.isEmpty()) {
					tokenlist.add(new AttributedToken(new Keyword("extends"), attribute, tokenlist.size()));
					addType(interfacelist.get(0), attribute);
					for (int i = 1; i < interfacelist.size(); i++) {
						tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
						addType(interfacelist.get(i), attribute);
					}
				}
			}
		} else {
			Type superclass = declaration.getSuperclassType();
			if (superclass != null) {
				tokenlist.add(new AttributedToken(new Keyword("extends"), attribute, tokenlist.size()));
				addType(superclass, attribute);
			}
			{
				List<Type> interfacelist =  declaration.superInterfaceTypes();
				if (interfacelist != null && !interfacelist.isEmpty()) {
					tokenlist.add(new AttributedToken(new Keyword("implements"), attribute, tokenlist.size()));
					addType(interfacelist.get(0), attribute);
					for (int i = 1; i < interfacelist.size(); i++) {
						tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
						addType(interfacelist.get(i), attribute);
					}
				}
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("{"), attribute, tokenlist.size()));
		{
			List<TypeDeclaration> bodylist = declaration.bodyDeclarations();
			if (bodylist != null && !bodylist.isEmpty()) {
				addTypeDeclaration(bodylist.get(0), attribute);
				for (int i = 1; i < bodylist.size(); i++) {
					addTypeDeclaration(bodylist.get(i), attribute);
				}
			} else {
				tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("}"), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	private void addEnumDeclaration(EnumDeclaration declaration, ATTRIBUTE attribute) {
		{
			List<IExtendedModifier> modlist = declaration.modifiers();
			if (modlist != null && !modlist.isEmpty()) {
				addIExtendedModifier(modlist.get(0), attribute);
				for (int i = 1; i < modlist.size(); i++) {
					addIExtendedModifier(modlist.get(i), attribute);
				}
			}
		}
		tokenlist.add(new AttributedToken(new Keyword("enum"), attribute, tokenlist.size()));
		addSimpleName(declaration.getName(), attribute);
		{
			List<Type> interfacelist =  declaration.superInterfaceTypes();
			if (interfacelist != null && !interfacelist.isEmpty()) {
				tokenlist.add(new AttributedToken(new Keyword("implements"), attribute, tokenlist.size()));
				addType(interfacelist.get(0), attribute);
				for (int i = 1; i < interfacelist.size(); i++) {
					tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
					addType(interfacelist.get(i), attribute);
				}
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("{"), attribute, tokenlist.size()));
		{
			List<EnumConstantDeclaration> enumlist =  declaration.enumConstants();
			if (enumlist != null && !enumlist.isEmpty()) {
				addEnumConstantDeclaration(enumlist.get(0), attribute);
				for (int i = 1; i < enumlist.size(); i++) {
					tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
					addEnumConstantDeclaration(enumlist.get(i), attribute);
				}
				tokenlist.add(new AttributedToken(new Miscellaneous(","), attribute, tokenlist.size()));
			}
		}
		{
			List<TypeDeclaration> bodylist = declaration.bodyDeclarations();
			if (bodylist != null && !bodylist.isEmpty()) {
				tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
				addTypeDeclaration(bodylist.get(0), attribute);
				for (int i = 1; i < bodylist.size(); i++) {
					addTypeDeclaration(bodylist.get(i), attribute);
				}
			} else {
				tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("}"), attribute, tokenlist.size()));
	}

	@SuppressWarnings("unchecked")
	private void addAnnotationTypeDeclaration(AnnotationTypeDeclaration declaration, ATTRIBUTE attribute) {
		{
			List<IExtendedModifier> modlist = declaration.modifiers();
			if (modlist != null && !modlist.isEmpty()) {
				addIExtendedModifier(modlist.get(0), attribute);
				for (int i = 1; i < modlist.size(); i++) {
					addIExtendedModifier(modlist.get(i), attribute);
				}
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("@"), attribute, tokenlist.size()));
		tokenlist.add(new AttributedToken(new Keyword("interface"), attribute, tokenlist.size()));
		addSimpleName(declaration.getName(), attribute);
		tokenlist.add(new AttributedToken(new Miscellaneous("{"), attribute, tokenlist.size()));
		{
			List<TypeDeclaration> bodylist = declaration.bodyDeclarations();
			if (bodylist != null && !bodylist.isEmpty()) {
				addTypeDeclaration(bodylist.get(0), attribute);
				for (int i = 1; i < bodylist.size(); i++) {
					addTypeDeclaration(bodylist.get(i), attribute);
				}
			} else {
				tokenlist.add(new AttributedToken(new SemiColon(";"), attribute, tokenlist.size()));
			}
		}
		tokenlist.add(new AttributedToken(new Miscellaneous("}"), attribute, tokenlist.size()));
	}

}
