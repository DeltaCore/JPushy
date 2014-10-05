package net.ccmob.apps.jpushy.sp.level;

public class LevelItem {

	String	name	= "", version = "", comment = "", file = "";

	public LevelItem() {
		this.setName("");
		this.setVersion("");
		this.setComment("");
		this.setFile("");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "[" + this.getName() + "|" + this.getVersion() + "]" + this.getComment();
	}

}
