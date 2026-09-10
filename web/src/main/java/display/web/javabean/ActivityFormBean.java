package display.web.javabean;

import java.util.List;


public class ActivityFormBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String educativeLevel;
	private String timeSlot;
	private int capacity;
	private int neededMonitors;
	private List<Integer> selectedMonitors;
	
	private int stage = 1;
	
	
	public String getEducativeLevel() {
		return educativeLevel;
	}
	public void setEducativeLevel(String educativeLevel) {
		this.educativeLevel = educativeLevel;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public int getNeededMonitors() {
		return neededMonitors;
	}
	public void setNeededMonitors(int neededMonitors) {
		this.neededMonitors = neededMonitors;
	}
	public List<Integer> getSelectedMonitors() {
		return selectedMonitors;
	}
	public void setSelectedMonitors(List<Integer> selectedMonitors) {
		this.selectedMonitors = selectedMonitors;
	}
}