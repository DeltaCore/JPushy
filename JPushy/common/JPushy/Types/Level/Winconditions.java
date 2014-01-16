package JPushy.Types.Level;

import java.util.ArrayList;

public class Winconditions {

	private ArrayList<Wincondition>	conditions	= new ArrayList<Wincondition>();

	public Wincondition getWinconditionByName(String name) {
		for (int i = 0; i < conditions.size(); i++) {
			if (conditions.get(i).getName().equalsIgnoreCase(name)) {
				return conditions.get(i);
			}
		}
		return null;
	}

	public boolean allCompleted() {
		for (int i = 0; i < conditions.size(); i++) {
			if (!conditions.get(i).isComplete())
				return false;
		}
		return true;
	}

	public void registerWincondition(Wincondition con) {
		conditions.add(con);
	}

}
