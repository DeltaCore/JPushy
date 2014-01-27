package JPushy.Types.ProgammingRelated;

import java.util.ArrayList;

public class WallState {

	public static ArrayList<WallState>	wallStates	   = new ArrayList<WallState>();
	public static final WallState	     cross	         = new WallState().setEast(true).setNorth(true).setSouth(true).setWest(true).setId(8).init();
	public static final WallState	     single1	       = new WallState().setNorth(true).setId(15).init();
	public static final WallState	     single2	       = new WallState().setSouth(true).setId(13).init();
	public static final WallState	     single3	       = new WallState().setEast(true).setId(16).init();
	public static final WallState	     single4	       = new WallState().setWest(true).setId(14).init();
	public static final WallState	     doubleCorner1	 = new WallState().setNorth(true).setEast(true).setId(12).init();
	public static final WallState	     doubleCorner2	 = new WallState().setEast(true).setSouth(true).setId(9).init();
	public static final WallState	     doubleCorner3	 = new WallState().setSouth(true).setWest(true).setId(10).init();
	public static final WallState	     doubleCorner4	 = new WallState().setWest(true).setNorth(true).setId(11).init();
	public static final WallState	     doubleStraight1	= new WallState().setNorth(true).setSouth(true).setId(3).init();
	public static final WallState	     doubleStraight2	= new WallState().setEast(true).setWest(true).setId(2).init();
	public static final WallState	     trippel1	       = new WallState().setNorth(true).setEast(true).setSouth(true).setId(6).init();
	public static final WallState	     trippel2	       = new WallState().setEast(true).setSouth(true).setWest(true).setId(7).init();
	public static final WallState	     trippel3	       = new WallState().setSouth(true).setWest(true).setNorth(true).setId(4).init();
	public static final WallState	     trippel4	       = new WallState().setWest(true).setNorth(true).setEast(true).setId(5).init();
	public static final WallState	     none	           = new WallState().setId(1).init();

	private boolean	                   north	         = false;
	private boolean	                   south	         = false;
	private boolean	                   east	           = false;
	private boolean	                   west	           = false;

	private int	                       id	             = 0;

	/**
	 * @return the north
	 */
	public boolean isNorth() {
		return north;
	}

	/**
	 * @param north
	 *          the north to set
	 */
	public WallState setNorth(boolean north) {
		this.north = north;
		return this;
	}

	/**
	 * @return the south
	 */
	public boolean isSouth() {
		return south;
	}

	/**
	 * @param south
	 *          the south to set
	 */
	public WallState setSouth(boolean south) {
		this.south = south;
		return this;
	}

	/**
	 * @return the east
	 */
	public boolean isEast() {
		return east;
	}

	/**
	 * @param east
	 *          the east to set
	 */
	public WallState setEast(boolean east) {
		this.east = east;
		return this;
	}

	/**
	 * @return the west
	 */
	public boolean isWest() {
		return west;
	}

	/**
	 * @param west
	 *          the west to set
	 */
	public WallState setWest(boolean west) {
		this.west = west;
		return this;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          the id to set
	 */
	public WallState setId(int id) {
		this.id = id;
		return this;
	}

	public boolean equals(WallState state) {
		if (state.isEast() == this.isEast() && state.isWest() == this.isWest() && state.isNorth() == this.isNorth() && state.isSouth() == this.isSouth()) {
			return true;
		}
		return false;
	}

	public WallState init() {
		wallStates.add(this);
		return this;
	}

	@Override
	public String toString() {
		return "[N-" + this.isNorth() + "|E-" + this.isEast() + "|S-" + this.isSouth() + "|W-" + this.isWest() + "]";
	}

}
