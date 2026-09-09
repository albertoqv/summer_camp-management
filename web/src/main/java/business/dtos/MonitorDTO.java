package business.dtos;


/**
 * La clase MonitorDTO representa a un monitor en un sistema educativo.
 */
public class MonitorDTO {
    private int id;             
    private String firstName;   
    private String lastName;    
    private boolean specialEducator; 

    /**
     * Constructor predeterminado de la clase Monitor.
     * Inicializa los campos con valores predeterminados.
     */
    public MonitorDTO() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.specialEducator = false;
    }

    /**
     * Constructor de la clase MonitorDTO que toma parámetros.
     *
     * @param id El identificador del monitor.
     * @param firstName El primer nombre del monitor.
     * @param lastName El apellido del monitor.
     * @param SpecialEducator Indica si el monitor es educador especial.
     */
    public MonitorDTO(int id, String firstName, String lastName, boolean SpecialEducator) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialEducator = SpecialEducator;
    }

    /**
     * Obtiene el identificador del monitor.
     *
     * @return El identificador del monitor.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Obtiene el primer nombre del monitor.
     *
     * @return El primer nombre del monitor.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Obtiene el apellido del monitor.
     *
     * @return El apellido del monitor.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Comprueba si el monitor es un educador especial.
     *
     * @return `true` si el monitor es un educador especial, `false` en caso contrario.
     */
    public boolean isSpecialEducator() {
        return this.specialEducator;
    }

    /**
     * Establece el identificador del monitor.
     *
     * @param id2 El nuevo identificador del monitor.
     */
    public void setId(int id2) {
        this.id = id2;
    }

    /**
     * Establece el primer nombre del monitor.
     *
     * @param firstName2 El nuevo primer nombre del monitor.
     */
    public void setFirstName(String firstName2) {
        this.firstName = firstName2;
    }

    /**
     * Establece el apellido del monitor.
     *
     * @param lastName2 El nuevo apellido del monitor.
     */
    public void setLastName(String lastName2) {
        this.lastName = lastName2;
    }

    /**
     * Establece si el monitor es un educador especial.
     *
     * @param SpecialEducator2 `true` si el monitor es un educador especial, `false` en caso contrario.
     */
    public void setSpecialEducator(boolean SpecialEducator2) {
        this.specialEducator = SpecialEducator2;
    }

    /**
     * Devuelve una representación en cadena del monitor en formato JSON.
     *
     * @return Una cadena que representa al monitor en formato JSON.
     */
    public String toString() {
        return "{id: " + this.id + ", firstName: '" + this.firstName + "', lastName: '" + this.lastName
                + "', isSpecialEducator: " + this.specialEducator + "}";
    }
    
    
}

	

