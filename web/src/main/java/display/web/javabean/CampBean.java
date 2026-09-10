package display.web.javabean;


import business.dtos.CampDTO;

public class CampBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private CampDTO camp;
	private int availableInscriptions;
	public CampDTO getCamp() {
		return camp;
	}

	public void setCamp(CampDTO camp) {
		this.camp = camp;
	}

	public int getAvailableInscriptions() {
		return availableInscriptions;
	}

	public void setAvailableInscriptions(int availableInscriptions) {
		this.availableInscriptions = availableInscriptions;
	}
}
