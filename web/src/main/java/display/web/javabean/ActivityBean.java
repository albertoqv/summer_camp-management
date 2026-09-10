package display.web.javabean;


import business.dtos.ActivityDTO;

public class ActivityBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private ActivityDTO activity;

	public ActivityDTO getActivity() {
		return activity;
	}

	public void setActivity(ActivityDTO activity) {
		this.activity = activity;
	}
}
