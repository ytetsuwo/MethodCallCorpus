package ms.gundam.astparser;

import java.io.Serializable;

public class Value implements Serializable {
	private static final long serialVersionUID = 1L;
	private String classname;
	private String methodname;
	private int count;

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
		return count + " " + classname + "# " + methodname;
	}
}
