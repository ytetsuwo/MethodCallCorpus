package ms.gundam.astparser;

import java.io.Serializable;

public class Value implements Serializable {
	private String classname;
	private String methodname;
 	private int count;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
