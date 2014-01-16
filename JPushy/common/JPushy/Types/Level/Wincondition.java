package JPushy.Types.Level;

public class Wincondition {

	String	        name	= "";

	private boolean	complete;

	public void onComplete() {
		this.complete = true;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *          the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the complete
	 */
	public boolean isComplete() {
		return complete;
	}
}
