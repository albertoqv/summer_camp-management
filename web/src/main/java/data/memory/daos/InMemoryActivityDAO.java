package data.memory.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.exceptions.dao.NotFoundException;
import business.interfaces.IActivityDAO;
import business.interfaces.ICriteria;
import data.memory.MapsManager;

/**
 * La clase InMemoryActivityRepository es una implementación en memoria de un repositorio de actividades.
 */
public class InMemoryActivityDAO implements IActivityDAO {
    private MapsManager mapsManager;

    /**
     * Constructor de la clase InMemoryActivityRepository.
     * Inicializa un nuevo mapa para almacenar actividades en memoria.
     */
    public InMemoryActivityDAO() {
        this.mapsManager = MapsManager.getInstance();
    }

    /**
     * Busca una actividad por su nombre.
     *
     * @param activityName El nombre de la actividad a buscar.
     * @return La actividad encontrada.
     * @throws NotFoundException Si la actividad no se encuentra en el repositorio.
     */
    @Override
    public ActivityDTO find(String activityName) {
        if (this.mapsManager.getMapOfActivity().get(activityName) == null) {
            throw new NotFoundException();
        }
        return this.mapsManager.getMapOfActivity().get(activityName);
    }

    /**
     * Guarda una actividad en el repositorio.
     *
     * @param activity La actividad a guardar en el repositorio.
     */
    @Override
    public void save(ActivityDTO activity) {
        this.mapsManager.getMapOfActivity().put(activity.getActivityName(), activity);
    }

    /**
     * Obtiene una lista de todas las actividades almacenadas en el repositorio.
     *
     * @return Una lista de actividades.
     */
    @Override
    public List<ActivityDTO> getAll(Optional<ICriteria> criteria) {
        List<ActivityDTO> allActivities = new ArrayList<>(this.mapsManager.getMapOfActivity().values());
        return allActivities;
    }

    /**
     * Elimina una actividad del repositorio.
     *
     * @param obj La actividad a eliminar del repositorio.
     */
    @Override
    public void delete(ActivityDTO obj) {
        this.mapsManager.getMapOfActivity().remove(obj.getActivityName());
        for (int key : this.mapsManager.getMapOfActivityCamp().keySet()) {
        	List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
        	for (ActivityDTO iterable_element : this.mapsManager.getMapOfActivityCamp().get(key)) {
				if (iterable_element.getActivityName() != obj.getActivityName()) {
					activities.add(iterable_element);
				}
			}
        	this.mapsManager.getMapOfActivityCamp().put(key, activities);
        }
        
    }

	@Override
	public List<ActivityDTO> getActivitiesInACamp(CampDTO camp) {
		if (this.mapsManager.getMapOfActivityCamp().get(camp.getCampID()) == null) {
            this.mapsManager.getMapOfActivityCamp().put(camp.getCampID(), new ArrayList<ActivityDTO>());
        }
		return this.mapsManager.getMapOfActivityCamp().get(camp.getCampID());
	}

	@Override
	public void saveAndRelateWithACamp(ActivityDTO activity, CampDTO camp) {
		save(activity);
		if (this.mapsManager.getMapOfActivityCamp().get(camp.getCampID()) == null) {
            this.mapsManager.getMapOfActivityCamp().put(camp.getCampID(), new ArrayList<ActivityDTO>());
        }
		
		this.mapsManager.getMapOfActivityCamp().get(camp.getCampID()).add(activity);
	}

	@Override
	public void create(ActivityDTO obj) {
		save(obj);
	}
}
