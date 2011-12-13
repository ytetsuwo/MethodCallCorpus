package token;

public final class Identifier extends Token {
	public Identifier(String name) {
		super(name);
	}

	@Override
	public String dump() {
		return 'I' + name;
	}

	@Override
	public void restore(String str) {
		this.name = str.substring(1);
	}
}
