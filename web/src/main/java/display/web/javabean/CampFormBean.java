package display.web.javabean;


public class CampFormBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String start;
	private String end;
	private String educativeLevel;
	private int capacity;
	private String activityName;
	private int monitorId;
	
	private int stage = 1;
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
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
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public int getMonitorId() {
		return monitorId;
	}
	public void setMonitorId(int monitorId) {
		this.monitorId = monitorId;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
}