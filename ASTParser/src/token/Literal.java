package token;

public class Literal extends Token {
	public Literal(String name) {
		super(name);
	}

	@Override
	public String dump() {
		return 'L' + name;
	}

	@Override
	public void restore(String str) {
		this.name = str.substring(1);
	}
}
