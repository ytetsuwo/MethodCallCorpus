package ms.gundam.astparser.token;

/**
 * 
 * @author tetsuo
 *
 */
public abstract class Token {
	protected String name;
	public Token(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public abstract String dump();		
	public abstract void restore(String str);
}
