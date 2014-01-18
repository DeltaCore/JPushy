package JPushy.Types.Items;

public class ItemUseEvent {

	private boolean	canceled	= false;
	private boolean	handled	 = false;

	/**
	 * @return the canceled
	 */
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * @param canceled
	 *          the canceled to set
	 */
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	/**
	 * @return the handled
	 */
	public boolean isHandled() {
		return handled;
	}

	/**
	 * @param handled
	 *          the handled to set
	 */
	public void setHandled(boolean handled) {
		this.handled = handled;
	}

}
