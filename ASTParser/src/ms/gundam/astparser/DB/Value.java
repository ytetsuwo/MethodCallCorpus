package ms.gundam.astparser.DB;

import java.io.Serializable;

public class Value implements Serializable, Comparable<Value> {
	private static final long serialVersionUID = 1L;
	protected String classname;
	protected String methodname;
	protected int count;

	public Value() {
		super();
	}
	public Value(String classname, String methodname) {
		super();
		this.classname = classname;
		this.methodname = methodname;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getClassname() {
		return classname;
 	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	@Override
	public String toString() {
		return count + " " + classname + "#" + methodname;
	}
	@Override
	public int compareTo(Value o) {
		if (this.count == o.count) {
			return 0;
		} else if (this.count > o.count) {
			return -1;
		} else {
			return 1;
		}
	}
}
