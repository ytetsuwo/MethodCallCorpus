package ms.gundam.astparser;

/**
 * 
 * @author tetsuo
 *
 */
public interface Token {
	public String dump();		
	public void restore(String str);
}
