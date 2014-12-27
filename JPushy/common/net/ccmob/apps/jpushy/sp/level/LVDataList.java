package net.ccmob.apps.jpushy.sp.level;

import java.util.ArrayList;

public class LVDataList {

	private ArrayList<LVData>	dataField;

	public LVDataList() {
		dataField = new ArrayList<LVData>();
	}

	public boolean addData(LVData data) {
		if (dataExists(data))
			return false;
		dataField.add(data.copy());
		return true;
	}

	public boolean dataExists(LVData data) {
		for (int i = 0; i < dataField.size(); i++) {
			if (dataField.get(i).getDataName().equals(data.getDataName()))
				return true;
		}
		return false;
	}

	public boolean dataExists(String name) {
		for (int i = 0; i < dataField.size(); i++) {
			if (dataField.get(i).getDataName().equals(name))
				return true;
		}
		return false;
	}

	public LVData getDataByDataName(String dataName) {
		for (int i = 0; i < dataField.size(); i++) {
			if (dataField.get(i).getDataName().equalsIgnoreCase(dataName))
				return dataField.get(i);
		}
		return null;
	}

	public void setData(LVData data) {
		if (dataExists(data)) {
			for (int i = 0; i < dataField.size(); i++) {
				if (dataField.get(i).getDataName().equals(data.getDataName())) {
					dataField.get(i).setObject(data.getObject());
				}
			}
		}
	}

	public ArrayList<LVData> getList() {
		return dataField;
	}

}
