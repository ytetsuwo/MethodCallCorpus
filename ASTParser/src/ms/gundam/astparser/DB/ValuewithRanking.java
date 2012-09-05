package ms.gundam.astparser.DB;



public class ValuewithRanking extends Value {
	private static final long serialVersionUID = 1L;
	private int ranking;
	private int percentage;

	public ValuewithRanking(Value v) {
		this.classname = v.getClassname();
		this.methodname = v.getMethodname();
		this.count = v.getCount();
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public int compareTo(ValuewithRanking o) {
		if (this.percentage == o.percentage) {
			return 0;
		} else if (this.percentage > o.percentage) {
			return -1;
		} else {
			return 1;
		}
	}
}
