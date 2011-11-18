package ms.gundam.astparser;

public final class Keyword implements Token {
	private String name;

	public Keyword(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
