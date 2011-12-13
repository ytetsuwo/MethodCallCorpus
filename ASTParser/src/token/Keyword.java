package token;

public final class Keyword extends Token {
	public Keyword(String name) {
		super(name);
	}

	@Override
	public String dump() {
		return 'K' + name;
	}

	@Override
	public void restore(String str) {
		this.name = str.substring(1);
	}
}
