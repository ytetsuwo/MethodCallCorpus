package ms.gundam.astparser;

public class Literal implements Token {

	private String name;

	public Literal(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
