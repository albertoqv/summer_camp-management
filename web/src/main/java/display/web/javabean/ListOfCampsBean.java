package display.web.javabean;

import java.util.List;

import business.dtos.CampDTO;

public class ListOfCampsBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private List<CampDTO> camps;

	public List<CampDTO> getCamps() {
		return camps;
	}

	public void setCamps(List<CampDTO> camps) {
		this.camps = camps;
	}

	
}