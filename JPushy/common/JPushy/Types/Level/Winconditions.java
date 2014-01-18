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

	public boolean existsCondition(String name) {
		for (int i = 0; i < conditions.size(); i++) {
			if (conditions.get(i).getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public void newWinConWincondition(String name) {
		Wincondition conWincondition = new Wincondition();
		conWincondition.setName(name);
		if (!existsCondition(name)) {
			registerWincondition(conWincondition);
		}
	}

	public boolean allCompleted() {
		for (int i = 0; i < conditions.size(); i++) {
			if (!conditions.get(i).isComplete()) {
				System.out.println("-->" + conditions.get(i).getName() + " : " + conditions.get(i).isComplete());
				return false;
			}
		}
		return true;
	}

	public void registerWincondition(Wincondition con) {
		conditions.add(con);
	}

	public void completeCondition(String name) {
		for (int i = 0; i < conditions.size(); i++) {
			if (!conditions.get(i).isComplete() && conditions.get(i).getName().equals(name))
				conditions.get(i).onComplete();
		}
	}

	public void completeCondition(Wincondition con) {
		con.onComplete();
	}

	public ArrayList<Wincondition> getList() {
		return conditions;
	}

}
