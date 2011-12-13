package token;

public class Operator extends Token {
	public Operator(String name) {
		super(name);
	}

	@Override
	public String dump() {
		return 'O' + name;
	}

	@Override
	public void restore(String str) {
		this.name = str.substring(1);
	}
}
