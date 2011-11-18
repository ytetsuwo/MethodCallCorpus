package ms.gundam.astparser;

public class Operator implements Token {

	private String name;

	public Operator(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
