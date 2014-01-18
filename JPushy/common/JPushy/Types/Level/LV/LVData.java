package JPushy.Types.Level.LV;

public class LVData extends Object implements Cloneable {

	private String	dataName	= "";

	private Object	object	 = null;
	private String	string	 = "";
	private Integer	integer	 = 0;
	private boolean	iSet	   = false;
	private boolean	sSet	   = false;
	private boolean	oSet	   = false;

	public LVData(String dataName) {
		this.setDataName(dataName);
	}

	/**
	 * @return the dataName
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * @param dataName
	 *          the dataName to set
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object
	 *          the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
		this.oSet = true;
	}

	public void setInt(int i) {
		this.integer = i;
		this.iSet = true;
	}

	public void setString(String s) {
		this.string = s;
		this.sSet = true;
	}

	public LVData copy() {
		try {
			return (LVData) this.clone();
		} catch (CloneNotSupportedException err) {
			throw new RuntimeException(err);
		}
	}

	public int getInt() {
		return this.integer;
	}

	public String getString() {
		return this.string;
	}

	/**
	 * @return the integer
	 */
	public Integer getInteger() {
		return integer;
	}

	/**
	 * @return the iSet
	 */
	public boolean isiSet() {
		return iSet;
	}

	/**
	 * @return the sSet
	 */
	public boolean issSet() {
		return sSet;
	}

	/**
	 * @return the oSet
	 */
	public boolean isoSet() {
		return oSet;
	}

}
