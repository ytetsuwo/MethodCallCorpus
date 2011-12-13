package ms.gundam.astparser.token;


public class TokenFactory {
	public static Token create(String str) {
		Token token = null;
		switch (str.charAt(0)) {
		case 'I':
			token = new Identifier(null);
			token.restore(str);
			break;
		case 'K':
			token = new Keyword(null);
			token.restore(str);
			break;
		case 'L':
			token = new Literal(null);
			token.restore(str);
			break;
		case 'M':
			token = new Miscellaneous(null);
			token.restore(str);
			break;
		case 'O':
			token = new Operator(null);
			token.restore(str);
			break;
		default:
			System.out.println("Error: Cannot create Token " + str);
			System.exit(-1);
		}
		return token;
	}
}
