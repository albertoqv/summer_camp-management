package display.web.javabean;

import java.util.List;

import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;

public class ListOfInscriptionsBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private List<InscriptionDTO> inscriptions;

	public List<InscriptionDTO> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<InscriptionDTO> inscriptions) {
		this.inscriptions = inscriptions;
	}

	
}