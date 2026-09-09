

package business.dtos;


import business.enums.EducativeLevel;
import business.enums.TimeSlot;
/**
 * La clase ActivityDTO representa una actividad educativa que se lleva a cabo en un horario específico
 * y se asocia con un nivel educativo, un nombre y un límite de asistentes y monitores.
 */
public class ActivityDTO {
    private String activityName;
    private EducativeLevel educativeLevel;
    private TimeSlot timeSlot;
    private int maxAssistants;
    private int neededMonitors;

    /**
     * Constructor de la clase ActivityDTO.
     * 
     * @param activityName   El nombre de la actividad.
     * @param educativeLevel El nivel educativo al que se dirige la actividad.
     * @param timeSlot       El horario en el que se lleva a cabo la actividad.
     * @param maxAssistants  El número máximo de asistentes permitidos.
     * @param neededMonitors El número de monitores necesarios para la actividad.
     */
    public ActivityDTO(String activityName, EducativeLevel educativeLevel, TimeSlot timeSlot, int maxAssistants,
            int neededMonitors) {
        this.activityName = activityName;
        this.educativeLevel = educativeLevel;
        this.timeSlot = timeSlot;
        this.maxAssistants = maxAssistants;
        this.neededMonitors = neededMonitors;
    }

    /**
     * Constructor vacío de la clase ActivityDTO.
     */
    public ActivityDTO() {
        this.activityName = null;
        this.educativeLevel = null;
        this.timeSlot = null;
        this.maxAssistants = -1;
        this.neededMonitors = -1;
    }


    /**
     * Obtiene el nombre de la actividad.
     * 
     * @return El nombre de la actividad.
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * Establece el nombre de la actividad.
     * 
     * @param activityName El nombre de la actividad a establecer.
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    /**
     * Obtiene el nivel educativo al que se dirige la actividad.
     * 
     * @return El nivel educativo de la actividad.
     */
    public EducativeLevel getEducativeLevel() {
        return educativeLevel;
    }

    /**
     * Establece el nivel educativo de la actividad.
     * 
     * @param educativeLevel El nivel educativo a establecer.
     */
    public void setEducativeLevel(EducativeLevel educativeLevel) {
        this.educativeLevel = educativeLevel;
    }

    /**
     * Obtiene el horario en el que se lleva a cabo la actividad.
     * 
     * @return El horario de la actividad.
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Establece el horario de la actividad.
     * 
     * @param timeSlot El horario a establecer.
     */
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Obtiene el número máximo de asistentes permitidos en la actividad.
     * 
     * @return El número máximo de asistentes.
     */
    public int getMaxAssistants() {
        return maxAssistants;
    }

    /**
     * Establece el número máximo de asistentes permitidos en la actividad.
     * 
     * @param maxAssistants El número máximo de asistentes a establecer.
     */
    public void setMaxAssistants(int maxAssistants) {
        this.maxAssistants = maxAssistants;
    }

    /**
     * Obtiene el número de monitores necesarios para la actividad.
     * 
     * @return El número de monitores necesarios.
     */
    public int getNeededMonitors() {
        return neededMonitors;
    }

    /**
     * Establece el número de monitores necesarios para la actividad.
     * 
     * @param neededMonitors El número de monitores necesarios a establecer.
     */
    public void setNeededMonitors(int neededMonitors) {
        this.neededMonitors = neededMonitors;
    }

    /**
     * Representa la actividad como una cadena de texto en formato JSON.
     * 
     * @return Una cadena de texto que representa la actividad en formato JSON.
     */
    public String toString() {
        return "{activityName: '" + this.activityName + "', "
        		+ "educativeLevel: " + this.educativeLevel + ", "
				+ "timeSlot: " + this.timeSlot + ", "
				+ "maxAssistants: " + this.maxAssistants + ", "
				+ "neededMonitors: " + this.neededMonitors + "}";
    }

    
    
    
}
