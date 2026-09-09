package business.dtos;

import java.util.Date;

import business.enums.EducativeLevel;
import business.utilities.Utils;

/**
 * La clase CampDTO representa un campamento educativo con su información básica y actividades asociadas.
 */
public class CampDTO {
    private int campID;
    private Date start;
    private Date end;
    private EducativeLevel educativeLevel;
    private int capacity;
    private MonitorDTO principalMonitor;
    private MonitorDTO specialMonitor;
    /**
     * Constructor predeterminado para crear un objeto CampDTO vacío.
     */
    public CampDTO(){
        this.campID = -1;
        this.start = null;
        this.end = null;
        this.educativeLevel = null;
        this.capacity = -1;
    }

    /**
     * Constructor para crear un nuevo objeto CampDTO con información proporcionada.
     *
     * @param campID        El identificador del campamento.
     * @param start         La fecha de inicio del campamento.
     * @param end           La fecha de finalización del campamento.
     * @param educativeLevel El nivel educativo del campamento.
     * @param capacity      La capacidad máxima de participantes del campamento.
     */
    public CampDTO(int campID, Date start, Date end, EducativeLevel educativeLevel, int capacity){
        this.campID = campID;
        this.start = start;
        this.end = end;
        this.educativeLevel = educativeLevel;
        this.capacity = capacity;
    }

	/**
     * Obtiene el monitor principal del campamento.
     *
     * @return El monitor principal del campamento.
     */
	public MonitorDTO getPrincipalMonitor() {
		return this.principalMonitor;
	}

	/**
     * Obtiene el monitor especial del campamento.
     *
     * @return El monitor especial del campamento.
     */
	public MonitorDTO getSpecialMonitor() {
		return this.specialMonitor;
	}

    /**
     * Obtiene el identificador del campamento.
     *
     * @return El identificador del campamento.
     */
    public int getCampID() {
        return campID;
    }

    /**
     * Establece el identificador del campamento.
     *
     * @param campID El nuevo identificador del campamento.
     */
    public void setCampID(int campID) {
        this.campID = campID;
    }

    /**
     * Obtiene la fecha de inicio del campamento.
     *
     * @return La fecha de inicio del campamento.
     */
    public Date getStart() {
        return start;
    }

    /**
     * Establece la fecha de inicio del campamento.
     *
     * @param start La nueva fecha de inicio del campamento.
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Obtiene la fecha de finalización del campamento.
     *
     * @return La fecha de finalización del campamento.
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Establece la fecha de finalización del campamento.
     *
     * @param end La nueva fecha de finalización del campamento.
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Obtiene el nivel educativo del campamento.
     *
     * @return El nivel educativo del campamento.
     */
    public EducativeLevel getEducativeLevel() {
        return educativeLevel;
    }

    /**
     * Establece el nivel educativo del campamento.
     *
     * @param educativeLevel El nuevo nivel educativo del campamento.
     */
    public void setEducativeLevel(EducativeLevel educativeLevel) {
        this.educativeLevel = educativeLevel;
    }

    /**
     * Obtiene la capacidad máxima de participantes del campamento.
     *
     * @return La capacidad máxima de participantes del campamento.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Establece la capacidad máxima de participantes del campamento.
     *
     * @param capacity La nueva capacidad máxima de participantes del campamento.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    /**
     * Devuelve una representación en formato de cadena del objeto Camp.
     *
     * @return Una cadena que representa el objeto Camp, incluyendo todos sus atributos.
     */
    public String toString() {
        return 
        		"{campID: " + this.campID 
        		+ ", start: " + Utils.getStringDate(start)
        		+ ", end: " + Utils.getStringDate(end)
        		+ ", educativeLevel: " + this.educativeLevel 
        		+ ", capacity: " + this.capacity 
        		+ ", principalMonitor: " + (this.principalMonitor == null ? "null" : this.principalMonitor.toString())
				+ ", specialMonitor: " + (this.principalMonitor == null ? "null" : this.specialMonitor.toString())
        		+  "}";
    }

    /**
     * Establece el monitor principal del campamento.
     *
     * @param monitor El nuevo monitor principal del campamento.
     */
	public void setPrincipalMonitor(MonitorDTO monitor) {
		this.principalMonitor = monitor;
	}

    /**
     * Establece el monitor especial del campamento.
     *
     * @param monitor El nuevo monitor especial del campamento.
     */
	public void setSpecialMonitor(MonitorDTO monitor) {
		this.specialMonitor = monitor;
	}

	
	
}
