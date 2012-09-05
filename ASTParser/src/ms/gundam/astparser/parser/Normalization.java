package ms.gundam.astparser.parser;

/**
 * トークン文字列の正規化情報格納クラス
 * 正規化したトークン文字列なら該当setterを呼び出しtureにsetしておく
 * @author tetsuo
 *
 */
public class Normalization {
	static final int IFNULLCHECK = 1;
	static final int IFTHROW = 2;
	static final int IFRETURN = 4;
	static final int VARIABLE = 8;
	private int flag = 0;
	
	public boolean isIfnullcheck() {
		return ((flag & IFNULLCHECK) == IFNULLCHECK ? true : false);
	}
	public void setIfnullcheck(boolean value) {
		if (value) {
			flag = flag | IFNULLCHECK;
		} else {
			flag = flag & ~IFNULLCHECK;
		}
	}
	public boolean isIfthrow() {
		return ((flag & IFTHROW) == IFTHROW ? true : false);
	}
	public void setIfthrow(boolean value) {
		if (value) {
			flag = flag | IFTHROW;
		} else {
			flag = flag & ~IFTHROW;
		}
	}
	public boolean isIfreturn() {
		return ((flag & IFRETURN) == IFRETURN ? true : false);
	}
	public void setIfreturn(boolean value) {
		if (value) {
			flag = flag | IFRETURN;
		} else {
			flag = flag & ~IFRETURN;
		}
	}
	public boolean isVariable() {
		return ((flag & VARIABLE) == VARIABLE ? true : false);
	}
	public void setVariable(boolean value) {
		if (value) {
			flag = flag | VARIABLE;
		} else {
			flag = flag & ~VARIABLE;
		}
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getFlag() {
		return flag;
	}
	private int countBits() {
		int count = 0;
		if (isIfnullcheck()) count++;
		if (isIfreturn()) count++;
		if (isIfthrow()) count++;
		if (isVariable()) count++;
		return count;
	}
	public boolean greater(Normalization flag) {
		if (countBits() > flag.countBits()) {
			return true;
		} else if (countBits() == flag.countBits()) {
			if (this.flag < flag.getFlag());
				return true;
		} else { 
			return false;
		}
	}
}
