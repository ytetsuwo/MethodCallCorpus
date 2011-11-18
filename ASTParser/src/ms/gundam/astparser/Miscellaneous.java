package ms.gundam.astparser;

/**
 * カッコやセミコロンなど雑多なトークンを表すクラス
 * 
 * @author higo
 *
 */
public class Miscellaneous implements Token {
	private String name;

	public Miscellaneous(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String dump() {
		return 'M' + name;
	}

	@Override
	public void restore(String str) {
		this.name = str.substring(1);
	}
}
