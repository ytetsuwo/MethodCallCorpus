package ms.gundam.astparser;

import java.io.Serializable;

public class Value implements Serializable {
	private String classname;
	private String methodname;

	public Value(String classname, String methodname) {
		super();
		this.classname = classname;
		this.methodname = methodname;
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
}
