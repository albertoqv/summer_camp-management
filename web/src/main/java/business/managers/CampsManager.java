package business.managers;

import java.util.List;


import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.exceptions.activity.ActivityAlreadyExistException;
import business.exceptions.activity.ActivityNotFoundException;
import business.exceptions.activity.MaxMonitorsAddedException;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.activity.NotEnoughMonitorsException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.IsNotAnSpecialEducator;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.camp.SpecialMonitorAlreadyRegisterException;
import business.exceptions.dao.NotFoundException;
import business.exceptions.monitor.MonitorAlreadyExistException;
import business.exceptions.monitor.MonitorNotFoundException;
import business.interfaces.IActivityDAO;
import business.interfaces.IDAO;
import business.interfaces.IMonitorDAO;


/**
 * La clase CampsManager se encarga de gestionar campamentos, actividades y monitores.
 */
public class CampsManager {
    private IDAO<CampDTO, Integer> campRepository;
    private IActivityDAO activityRepository;
    private IMonitorDAO monitorRepository;

    /**
     * Constructor de la clase CampsManager.
     * 
     * @param campRepository    El repositorio de campamentos.
     * @param activityRepository El repositorio de actividades.
     * @param monitorRepository  El repositorio de monitores.
     */
    public CampsManager(IDAO<CampDTO, Integer> campRepository, IActivityDAO activityRepository,
    		IMonitorDAO monitorRepository) {
        this.campRepository = campRepository;
        this.activityRepository = activityRepository;
        this.monitorRepository = monitorRepository;
    }

    /**
     * Registra una actividad en un campamento y verifica que tengan el mismo nivel educativo.
     * 
     * @param camp     El campamento en el que se registrará la actividad.
     * @param activity La actividad a registrar.
     * @return El campamento con la actividad registrada.
     * @throws NotTheSameLevelException Si la actividad y el campamento no tienen el mismo nivel educativo.
     */
    public CampDTO registerActivityInACamp(CampDTO camp, ActivityDTO activity) {
        if (activity.getEducativeLevel() != camp.getEducativeLevel()) {
            throw new NotTheSameLevelException();
        }
        if (getMonitorsOfAnActivity(activity).size() != activity.getNeededMonitors())
        {
        	throw new NotEnoughMonitorsException();
        }
        this.activityRepository.saveAndRelateWithACamp(activity, camp);
        return camp;
    }
    
    /**
     * Registra una actividad en la base de datos si no existe.
     * 
     * @param activity La actividad a registrar.
     * @throws ActivityAlreadyExistException Si la actividad ya está registrada.
     */
    public void registerActivity(ActivityDTO activity) {
		if (isRegisteredActivity(activity)) {
			throw new ActivityAlreadyExistException();
		}
    	activityRepository.save(activity);
    }

    /**
     * Verifica si un monitor está asignado a alguna actividad en un campamento.
     * 
     * @param activities   La actividades a verificar.
     * @param monitor El monitor a buscar.
     * @return true si el monitor está asignado a alguna actividad, false en caso contrario.
     */
    public boolean isMonitorInSomeActivity(List<ActivityDTO> activities, MonitorDTO monitor) {
        boolean founded = false;

        for (int i = 0; i < activities.size(); i++) {
            try {
            	ActivityDTO activityDTO = activities.get(i);
				List<MonitorDTO> monitorsInActivity = this.monitorRepository.getMonitorsInAnActivity(activityDTO);
				founded = monitorsInActivity.contains(monitor);
				for (MonitorDTO monitorDTO : monitorsInActivity) {
					if (monitorDTO.getId().equals(monitor.getId())) {
						founded = true;
						break;
					}
				}
			} catch (NotFoundException e) {}
        }
        return founded;
    }

    /**
     * Establece un monitor como el monitor principal de un campamento.
     * 
     * @param camp    El campamento en el que se establecerá el monitor principal.
     * @param monitor El monitor a establecer como principal.
     * @return El campamento actualizado con el monitor principal.
     * @throws MonitorIsNotInActivityException Si el monitor no está asignado a ninguna actividad en el campamento.
     */
    public CampDTO setPrincipalMonitor(CampDTO camp, MonitorDTO monitor) {
        List<ActivityDTO> activities = this.getActivitiesOfACamp(camp);
    	boolean founded = isMonitorInSomeActivity(activities, monitor);

        if (!founded) {
            throw new MonitorIsNotInActivityException();
        }
        camp.setPrincipalMonitor(monitor);

        this.monitorRepository.save(monitor);
        this.campRepository.save(camp);
        return camp;
    }

    /**
     * Establece un monitor como monitor especial de un campamento.
     * 
     * @param camp    El campamento en el que se establecerá el monitor especial.
     * @param monitor El monitor a establecer como especial.
     * @return El campamento actualizado con el monitor especial.
     * @throws SpecialMonitorAlreadyRegisterException Si el monitor ya está asignado a alguna actividad en el campamento.
     * @throws IsNotAnSpecialEducator Si el monitor no es un monitor especial.
	 *
     */
    public CampDTO setSpecialMonitor(CampDTO camp, MonitorDTO monitor) {
        List<ActivityDTO> activities = getActivitiesOfACamp(camp);
    	boolean founded = isMonitorInSomeActivity(activities, monitor);

        if (founded) {
            throw new SpecialMonitorAlreadyRegisterException();
        }
        
        if (monitor.isSpecialEducator() == false) {
        	throw new IsNotAnSpecialEducator();
        }

        camp.setSpecialMonitor(monitor);

        this.monitorRepository.save(monitor);
        this.campRepository.save(camp);
        return camp;
    }

    
    /**
     * Elimina una actividad de un campamento.
     * 
     * @param camp     El campamento del que se eliminará la actividad.
     * @param activity La actividad a eliminar.
     * @return El campamento actualizado sin la actividad.
     * @throws ActivityNotFoundException         Si la actividad no se encuentra en el campamento.
     * @throws MonitorIsNotInActivityException Si el monitor principal no está asignado a alguna actividad en el campamento.
     */
    public CampDTO deleteAnActivityOfACamp(CampDTO camp, ActivityDTO activity) {
        List<ActivityDTO> activities = getActivitiesOfACamp(camp);
        if (!activities.contains(activity)) {
            throw new ActivityNotFoundException();
        }

        activities.remove(activity);


        MonitorDTO principalMonitor = camp.getPrincipalMonitor();
        if (principalMonitor != null) {
            if (!isMonitorInSomeActivity(activities, principalMonitor)) {
                throw new MonitorIsNotInActivityException();
            }
        }


        this.activityRepository.delete(activity);
        return camp;
    }
    
    public ActivityDTO getActivity(String activityName) {
    	return this.activityRepository.find(activityName);
    }
    
    public MonitorDTO getMonitor(int monitorId) {
    	return this.monitorRepository.find(monitorId);
    }
   
    /**
     * Elimina un campamento.
     * 
     * @param camp     El campamento a eliminar.
     */
    public void deleteCamp(CampDTO camp) {
        try {
			this.campRepository.find(camp.getCampID());
			this.campRepository.delete(camp);
		} catch (NotFoundException e) {}
    }

    /**
     * Verifica si un campamento está registrado en el repositorio.
     * 
     * @param camp El campamento a verificar.
     * @return true si el campamento está registrado, false en caso contrario.
     */
    public boolean isRegisteredCamp(CampDTO camp) {
        try {
            this.campRepository.find(camp.getCampID());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }
    
    /**
     * Verifica si un monitor está registrado en el repositorio.
     * 
     * @param monitor El monitor a verificar.
     * @return true si el monitor está registrado, false en caso contrario.
     */
    public boolean isRegisteredMonitor(MonitorDTO monitor) {
        try {
            this.monitorRepository.find(monitor.getId());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }
    
    /**
     * Verifica si un monitor está registrado en el repositorio.
     * 
     * @param activity   Las actividades a verificar.
     * @return true si el monitor está registrado, false en caso contrario.
     */
    public boolean isRegisteredActivity(ActivityDTO activity) {
        try {
            this.activityRepository.find(activity.getActivityName());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    /**
     * Registra un campamento en el repositorio y verifica si ya está registrado.
     * 
     * @param camp El campamento a registrar.
     * @throws CampAlreadyRegisteredException Si el campamento ya está registrado.
     */
    public void registerCamp(CampDTO camp) {
        this.campRepository.create(camp);
    }
    
    /**
     * Recupera la lista de campamentos registrados en el sistema
     * 
     * @return Lista de campamentos registrados en el sistema
     */
    public List<CampDTO> listOfCamps() {
    	return this.campRepository.getAll();
    }
    
    /**
     * Recupera la lista de actividades registrados en el sistema
     * 
     * @return Lista de actividades registrados en el sistema
     */
    public List<ActivityDTO> listOfActivities() {
    	return this.activityRepository.getAll();
    }
    
    /**
     * Recupera la lista de monitores registrados en el sistema
     * 
     * @return Lista de monitores registrados en el sistema
     */
    public List<MonitorDTO> listOfMonitors() {
    	return this.monitorRepository.getAll();
    }
    
    /**
     * Recupera la lista de actividades relacionadas con un campamento
     * 
     * @param camp campamento seleccionado
     * @return Lista de actividades relacionadas con un campamento
     */
    public List<ActivityDTO> getActivitiesOfACamp(CampDTO camp) {
    	return this.activityRepository.getActivitiesInACamp(camp);
    }
    
    /**
     * Recupera la lista de monitores relacionados con una actividad
     * 
     * @param activity actividad seleccionada
     * @return Lista de monitores relacionados con una actividad
     */
    public List<MonitorDTO> getMonitorsOfAnActivity(ActivityDTO activity) {
    	return this.monitorRepository.getMonitorsInAnActivity(activity);
    }
    
    /**
     * Registra un monitor como monitor de una actividad.
     * 
     * @param monitorCreated El monitor a registrar.
     * @throws CampAlreadyRegisteredException Si el campamento ya está registrado.
     */
	public void registerMonitor(MonitorDTO monitorCreated) {
		if (isRegisteredMonitor(monitorCreated)) {
			throw new MonitorAlreadyExistException();
		}
    	monitorRepository.save(monitorCreated);	
	}
	
    /**
     * Registra un monitor como monitor de una actividad.
     * 
     * @param selectedActivity La actividad del monitor a registrar
     * @param monitorCreated  El monitor de la actividad
     * 
     * @throws CampAlreadyRegisteredException Si el campamento ya está registrado.
     */
	public void registerMonitorInActivity(ActivityDTO selectedActivity, MonitorDTO monitorCreated) {
		List<MonitorDTO> monitorList = this.getMonitorsOfAnActivity(selectedActivity);
		if (monitorList.size() == selectedActivity.getNeededMonitors()) {
            throw new MaxMonitorsAddedException();
        }
        monitorRepository.saveAndRelateWithAnActivity(monitorCreated, selectedActivity);		
	}
	
	public void linkMonitorWithActivity(String activityName, int monitorId) {
		ActivityDTO activityDTO = null;
		try {
			activityDTO = this.activityRepository.find(activityName);
		} catch (NotFoundException e) {
			throw new ActivityNotFoundException();
		}
		
		MonitorDTO monitorDTO = null;
		try {
			monitorDTO = this.monitorRepository.find(monitorId);
		} catch (NotFoundException e) {
			throw new MonitorNotFoundException();
		}
		
		List<MonitorDTO> monitorList = this.getMonitorsOfAnActivity(activityDTO);
		if (monitorList.size() == activityDTO.getNeededMonitors()) {
            throw new MaxMonitorsAddedException();
        }
		
		monitorRepository.saveAndRelateWithAnActivity(monitorDTO, activityDTO);
	}
	
	public void deleteActivity(ActivityDTO activity) {
		try {
			this.activityRepository.find(activity.getActivityName());
			this.activityRepository.delete(activity);
		} catch (NotFoundException e) {}		
    }
	
	public CampDTO getCamp(int campId) {
		return this.campRepository.find(campId);
	}
	
}
