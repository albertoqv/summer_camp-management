package display.web.javabean;

import business.enums.InscriptionType;

public class EnrollFormBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private InscriptionType type;
	private int campId;
	
	private int stage = 1;
	
	public InscriptionType getType() {
		return type;
	}
	public void setType(InscriptionType type) {
		this.type = type;
	}
	public int getCampId() {
		return campId;
	}
	public void setCampId(int campId) {
		this.campId = campId;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
}
