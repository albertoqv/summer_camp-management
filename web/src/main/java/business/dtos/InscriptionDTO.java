package business.dtos;

import java.util.Date;

import business.utilities.Utils;
/**
 * La clase InscriptionDTO representa una inscripción para un asistente en un campamento.
 */
public class InscriptionDTO {
	private String inscriptionIdentifier;
	private int assistantId;
	private int campId;
	private Date inscriptionDate;
	private float price;
	private boolean canBeCanceled;
	private boolean partial;

	 /**
     * Constructor para crear un objeto InscriptionDTO con la información proporcionada.
     *
     * @param assistantId     El ID del asistente inscrito.
     * @param campId          El ID del campamento al que se ha inscrito el asistente.
     * @param inscriptionDate La fecha en que se realizó la inscripción.
     * @param price           El precio total de la inscripción.
     * @param canBeCanceled   Indica si la inscripción se puede cancelar o no.
     * @param partial 		  Indica el tipo de inscripción
     */
	public InscriptionDTO(int assistantId, int campId, Date inscriptionDate, float price, boolean canBeCanceled, boolean partial) {
		this.assistantId = assistantId;
		this.campId = campId;
		this.inscriptionDate = inscriptionDate;
		this.price = price;
		this.canBeCanceled = canBeCanceled;
		this.partial = partial;
		this.reloadIdentifier();
	}
	
	/**
	* Recarga el identificador de la inscripción basándose en el ID del asistente y el ID del campamento.
 	* Si cualquiera de estos valores es nulo o igual a -1, el identificador se establece como una cadena vacía.
 	* Si ambos valores son válidos, el identificador se forma concatenando el ID del asistente y el ID del campamento con un guion.
 	*/
	private void reloadIdentifier() {
		if ((this.inscriptionDate == null) || (this.assistantId == -1) || (this.campId == -1)) {
			this.inscriptionIdentifier = "";
		}else {
			this.inscriptionIdentifier = this.assistantId + "-" + this.campId;
		}
	}
	/**
 	* Constructor predeterminado para crear un objeto InscriptionDTO vacío con valores predeterminados.
 	* El ID del asistente se inicializa como -1, el ID del campamento se inicializa como -1,
 	* la fecha de la inscripción se inicializa como null, el precio se inicializa como -1,
 	* y el identificador de la inscripción se recarga utilizando los valores iniciales.
 	*/
	public InscriptionDTO() {
		this.assistantId = -1;
		this.campId = -1;
		this.inscriptionDate = null;
		this.price = -1;
		this.reloadIdentifier();

	}
	/**
 	* Obtiene el identificador único de la inscripción, formado por el ID del asistente y el ID del campamento concatenados con un guion.
 	*
	* @return El identificador único de la inscripción.
	*/
	public String getInscriptionIdentifier() {
		return this.inscriptionIdentifier;
	}
	
	/**
 	* Obtiene el ID del asistente inscrito en esta inscripción.
	*
	* @return El ID del asistente inscrito.
 	*/
	public Integer getAssistantId() {
		return this.assistantId;
	}

	/**
 	* Obtiene el ID del campamento al que se ha inscrito el asistente en esta inscripción.
	*
 	* @return El ID del campamento al que se ha inscrito el asistente.
 	*/
	public Integer getCampId() {
		return this.campId;
	}

	/**
 	* Obtiene la fecha en que se realizó la inscripción.
 	*
 	* @return La fecha en que se realizó la inscripción.
 	*/
	public Date getInscriptionDate() {
		return this.inscriptionDate;
	}

	/**
 	* Obtiene el precio total de la inscripción.
 	*
 	* @return El precio total de la inscripción.
 	*/
	public Float getPrice() {
		return this.price;
	}

	/**
 	* Establece el ID del asistente inscrito y actualiza el identificador de la inscripción.
 	*
 	* @param assistantId El nuevo ID del asistente inscrito.
 	*/
	public void setAssistantId(int assistantId ){
		this.assistantId = assistantId;
		this.reloadIdentifier();
		
	}

	/**
 	* Establece el ID del campamento y actualiza el identificador de la inscripción.
 	*
 	* @param campId El nuevo ID del campamento.
 	*/
	public void setCampId(int campId) {
		this.campId = campId;
		this.reloadIdentifier();
	}
	
	/**
	 * Establece la fecha de la inscripción y actualiza el identificador de la inscripción.
 	*
 	* @param inscriptionDate La nueva fecha de la inscripción.
 	*/
	public void setInscriptionDate(Date inscriptionDate){
		this.inscriptionDate = inscriptionDate;
		this.reloadIdentifier();
	}
	
	/**
	 * Establece el precio total de la inscripción.
 	*
 	* @param price El nuevo precio total de la inscripción.
 	*/
	public void setPrice(float price){
		this.price = price;
	}

	/**
 	* Devuelve una representación en formato de cadena del objeto Inscription.
 	*
 	* @return Una cadena que representa el objeto Inscription, incluyendo todos sus atributos.
 	*/
	public String toString() {
		return "{"
				+ "AssistantId: " + this.assistantId 
				+ ", CampId: " + this.campId
				+ ", InscriptionDate: '" +Utils.getStringDate(this.inscriptionDate)
				+ "', Price: " + this.price + ", "
				+ "canBeCancelled: " + this.canBeCanceled + "}";
		
	}
	/**
 	* Indica si la inscripción es parcial o no
 	*
 	* @return true si la inscripción es parcial, false en caso contrario.
 	*/
	public boolean isPartial() {
		return partial;
	}
	/**
 	* Indica si la inscripción se puede cancelar o no.
 	*
 	* @return true si la inscripción se puede cancelar, false en caso contrario.
 	*/
	public boolean canBeCanceled() {
		return canBeCanceled;
	}
	


}
