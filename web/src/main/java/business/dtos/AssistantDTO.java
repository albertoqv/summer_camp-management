package business.dtos;

import java.util.Date;

import business.utilities.Utils;

/**
 * La clase AssistantDTO representa a un asistente con su información básica.
 */
public class AssistantDTO {
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private boolean requireSpecialAttention;


 /**
     * Constructor para crear un nuevo objeto AssistantDTO con información proporcionada.
     *
     * @param id                     El identificador del asistente.
     * @param firstName              El nombre del asistente.
     * @param lastName               El apellido del asistente.
     * @param birthDate              La fecha de nacimiento del asistente.
     * @param requireSpecialAttention Indica si el asistente requiere atención especial.
     */
	public AssistantDTO(int id, String firstName, String lastName, Date birthDate, boolean requireSpecialAttention) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.requireSpecialAttention = requireSpecialAttention;
	}


	/**
     * Constructor predeterminado para crear un objeto Assistant vacío.
     */
	public AssistantDTO() {
		this.id = 0;
		this.firstName = "";
		this.lastName = "";
		this.birthDate = null;
		this.requireSpecialAttention = false;
	}


	/**
     * Obtiene el identificador del asistente.
     *
     * @return El identificador del asistente.
     */
	public Integer getId() {
		return this.id;
	}


	/**
     * Obtiene el nombre del asistente.
     *
     * @return El nombre del asistente.
     */
	public String getFirstName() {
		return this.firstName;
	}

	 /**
     * Obtiene el apellido del asistente.
     *
     * @return El apellido del asistente.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Obtiene la fecha de nacimiento del asistente.
     *
     * @return La fecha de nacimiento del asistente.
     */
    public Date getBirthDate() {
        return this.birthDate;
    }

    /**
     * Verifica si el asistente requiere atención especial.
     *
     * @return true si el asistente requiere atención especial, false en caso contrario.
     */
    public boolean isRequireSpecialAttention() {
        return this.requireSpecialAttention;
    }

    /**
     * Establece el identificador del asistente.
     *
     * @param id2 El nuevo identificador del asistente.
     */
    public void setId(int id2) {
        this.id = id2;
    }

    /**
     * Establece el nombre del asistente.
     *
     * @param firstName2 El nuevo nombre del asistente.
     */
    public void setFirstName(String firstName2) {
        this.firstName = firstName2;
    }

    /**
     * Establece el apellido del asistente.
     *
     * @param lastName2 El nuevo apellido del asistente.
     */
    public void setLastName(String lastName2) {
        this.lastName = lastName2;
    }

    /**
     * Establece la fecha de nacimiento del asistente.
     *
     * @param birthDate2 La nueva fecha de nacimiento del asistente.
     */
    public void setBirthDate(Date birthDate2) {
        this.birthDate = birthDate2;
    }

    /**
     * Establece si el asistente requiere atención especial.
     *
     * @param requireSpecialAttention2 true si el asistente requiere atención especial, false en caso contrario.
     */
    public void setRequireSpecialAttention(boolean requireSpecialAttention2) {
        this.requireSpecialAttention = requireSpecialAttention2;
    }

	/**
     * Devuelve una representación en formato de cadena del objeto Assistant.
     *
     * @return Una cadena que representa el objeto Assistant, incluyendo todos sus atributos.
     */
	
	public String toString() {
		return "{id: " 
				+ this.id 
				+ ", firstName: '"
				+ this.firstName
				+ "', lastName: '"
				+ this.lastName
				+ "', birthDate: "
				+ Utils.getStringDate(this.birthDate) 
				+ ", requireSpecialAttention: "
				+ this.requireSpecialAttention + "}";
	}
	
}
