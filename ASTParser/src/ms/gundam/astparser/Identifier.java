package ms.gundam.astparser;

public final class Identifier implements Token {
	private String name;

	public Identifier(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
