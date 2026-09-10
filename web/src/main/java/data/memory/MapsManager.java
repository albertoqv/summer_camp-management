package data.memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.MonitorDTO;
import business.dtos.UserDTO;
import business.enums.UserRole;
/**
 * Esta clase se utiliza para gestionar los mapas de los datos de la aplicación.
 */
public class MapsManager {
	private static MapsManager instance;
	private Map<String, ActivityDTO> mapOfActivity;
    private Map<Integer, List<ActivityDTO>> mapOfActivityCamp;
    private Map<Integer, AssistantDTO> mapOfAssistants;
    private Map<String, UserDTO> mapOfUsers;
    private Map<Integer, CampDTO> mapOfCamp;
    private Map<String, InscriptionDTO> mapOfInscription;
    private Map<Integer, MonitorDTO> mapOfMonitor;
    private Map<String, List<MonitorDTO>> mapOfMonitorActivity;
    private Map<String, List<AssistantDTO>> mapOfAssistantUser;
	
	/**
     * Este método se utiliza para obtener la instancia de MapsManager.
     * @return  La instancia de MapsManager.
     */
	public static MapsManager getInstance() {
		if (instance == null) {
			instance = new MapsManager();
		}
		return instance;
	}
	/**
     * Este método se utiliza para restablecer la instancia de MapsManager.
     */
	public static void resetInstance() {
		instance = new MapsManager();
	}
	/**
     * Este es el constructor de la clase MapsManager.
     */
	private MapsManager() {
		this.mapOfActivity = new HashMap<String, ActivityDTO>();
        this.mapOfActivityCamp = new HashMap<Integer, List<ActivityDTO>>();
        this.mapOfAssistants = new HashMap<Integer, AssistantDTO>();
        this.mapOfCamp = new HashMap<Integer, CampDTO>();
        this.mapOfInscription = new HashMap<String, InscriptionDTO>();
        this.mapOfMonitor = new HashMap<Integer, MonitorDTO>();
        this.mapOfMonitorActivity = new HashMap<String, List<MonitorDTO>>();
        this.setMapOfAssistantUser(new HashMap<String, List<AssistantDTO>>());
        this.mapOfUsers = new HashMap<String, UserDTO>();
	}
	/**
     * Obtiene el mapa de actividades.
     *
     * @return Mapa El mapa de actividades.
     */
    public Map<String, ActivityDTO> getMapOfActivity() {
        return mapOfActivity;
    }

    /**
     * Establece el mapa de actividades.
     *
     * @param mapOfActivity El nuevo mapa de actividades.
     */
    public void setMapOfActivity(Map<String, ActivityDTO> mapOfActivity) {
        this.mapOfActivity = mapOfActivity;
    }

    /**
     * Obtiene el mapa de actividades por campamento.
     *
     * @return  El mapa de actividades por campamento.
     */
    public Map<Integer, List<ActivityDTO>> getMapOfActivityCamp() {
        return mapOfActivityCamp;
    }

    /**
     * Establece el mapa de actividades por campamento.
     *
     * @param mapOfActivityCamp El nuevo mapa de actividades por campamento.
     */
    public void setMapOfActivityCamp(Map<Integer, List<ActivityDTO>> mapOfActivityCamp) {
        this.mapOfActivityCamp = mapOfActivityCamp;
    }

    /**
     * Obtiene el mapa de asistentes.
     *
     * @return  El mapa de asistentes.
     */
    public Map<Integer, AssistantDTO> getMapOfAssistants() {
        return mapOfAssistants;
    }

    /**
     * Establece el mapa de asistentes.
     *
     * @param mapOfAssistants El nuevo mapa de asistentes.
     */
    public void setMapOfAssistants(Map<Integer, AssistantDTO> mapOfAssistants) {
        this.mapOfAssistants = mapOfAssistants;
    }

    /**
     * Obtiene el mapa de campamentos.
     *
     * @return  El mapa de campamentos.
     */
    public Map<Integer, CampDTO> getMapOfCamp() {
        return mapOfCamp;
    }

    /**
     * Establece el mapa de campamentos.
     *
     * @param mapOfCamp El nuevo mapa de campamentos.
     */
    public void setMapOfCamp(Map<Integer, CampDTO> mapOfCamp) {
        this.mapOfCamp = mapOfCamp;
    }

    /**
     * Obtiene el mapa de inscripciones.
     *
     * @return  El mapa de inscripciones.
     */
    public Map<String, InscriptionDTO> getMapOfInscription() {
        return mapOfInscription;
    }

    /**
     * Establece el mapa de inscripciones.
     *
     * @param mapOfInscription El nuevo mapa de inscripciones.
     */
    public void setMapOfInscription(Map<String, InscriptionDTO> mapOfInscription) {
        this.mapOfInscription = mapOfInscription;
    }

    /**
     * Obtiene el mapa de monitores.
     *
     * @return El mapa de monitores.
     */
    public Map<Integer, MonitorDTO> getMapOfMonitor() {
        return mapOfMonitor;
    }

    /**
     * Establece el mapa de monitores.
     *
     * @param mapOfMonitor El nuevo mapa de monitores.
     */
    public void setMapOfMonitor(Map<Integer, MonitorDTO> mapOfMonitor) {
        this.mapOfMonitor = mapOfMonitor;
    }

    /**
     * Obtiene el mapa de monitores por actividad.
     *
     * @return El mapa de monitores por actividad.
     */
    public Map<String, List<MonitorDTO>> getMapOfMonitorUser() {
        return mapOfMonitorActivity;
    }

    /**
     * Establece el mapa de monitores por actividad.
     *
     * @param mapOfMonitorActivity El nuevo mapa de monitores por actividad.
     */
    public void setMapOfMonitorActivity(Map<String, List<MonitorDTO>> mapOfMonitorActivity) {
        this.mapOfMonitorActivity = mapOfMonitorActivity;
    }
	public Map<String, List<AssistantDTO>> getMapOfAssistantUser() {
		return mapOfAssistantUser;
	}
	public void setMapOfAssistantUser(Map<String, List<AssistantDTO>> mapOfAssistantUser) {
		this.mapOfAssistantUser = mapOfAssistantUser;
	}
	public Map<String, UserDTO> getMapOfUsers() {
		return mapOfUsers;
	}
	public void setMapOfUsers(Map<String, UserDTO> mapOfUsers) {
		this.mapOfUsers = mapOfUsers;
	}
}
