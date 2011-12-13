package token;

/**
 * カッコやセミコロンなど雑多なトークンを表すクラス
 * 
 */
public class Miscellaneous extends Token {
	public Miscellaneous(String name) {
		super(name);
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
