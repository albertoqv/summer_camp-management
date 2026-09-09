package data.memory.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.MonitorDTO;
import business.exceptions.dao.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IMonitorDAO;
import data.memory.MapsManager;

/**
 * La clase InMemoryMontiorRepository es una implementación en memoria de un repositorio de monitores.
 
 */
public class InMemoryMontiorDAO implements IMonitorDAO {
    private MapsManager mapsManager;

    /**
     * Constructor de la clase InMemoryMontiorRepository.
     * Inicializa un nuevo mapa para almacenar monitores en memoria.
     */
    public InMemoryMontiorDAO() {
        this.mapsManager = MapsManager.getInstance();
    }

    /**
     * Busca un monitor por su identificador.
     *
     * @param identifier El identificador del monitor a buscar.
     * @return El monitor encontrado.
     * @throws NotFoundException Si el monitor no se encuentra en el repositorio.
     */
    @Override
    public MonitorDTO find(Integer identifier) {
        if (this.mapsManager.getMapOfMonitor().get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapsManager.getMapOfMonitor().get(identifier);
    }

    /**
     * Guarda un monitor en el repositorio.
     *
     * @param obj El monitor a guardar en el repositorio.
     */
    @Override
    public void save(MonitorDTO obj) {
        this.mapsManager.getMapOfMonitor().put(obj.getId(), obj);
    }

    /**
     * Obtiene una lista de todos los monitores almacenados en el repositorio.
     *
     * @return Una lista de monitores.
     */
    @Override
    public List<MonitorDTO> getAll(Optional<ICriteria> criteria) {
        List<MonitorDTO> allMonitors = new ArrayList<>(this.mapsManager.getMapOfMonitor().values());
        return allMonitors;
    }

    /**
     * Elimina un monitor del repositorio.
     *
     * @param obj El monitor a eliminar del repositorio.
     */
    @Override
    public void delete(MonitorDTO obj) {
        this.mapsManager.getMapOfMonitor().remove(obj.getId());
    }

	@Override
	public List<MonitorDTO> getMonitorsInAnActivity(ActivityDTO activity) {
		if (this.mapsManager.getMapOfMonitorUser().get(activity.getActivityName()) == null) {
            this.mapsManager.getMapOfMonitorUser().put(activity.getActivityName(), new ArrayList<MonitorDTO>());
        }
		return this.mapsManager.getMapOfMonitorUser().get(activity.getActivityName());
	}

	@Override
	public void saveAndRelateWithAnActivity(MonitorDTO monitor, ActivityDTO activity) {
		if (this.mapsManager.getMapOfMonitorUser().get(activity.getActivityName()) == null) {
            this.mapsManager.getMapOfMonitorUser().put(activity.getActivityName(), new ArrayList<MonitorDTO>());
        }
		
		this.mapsManager.getMapOfMonitorUser().get(activity.getActivityName()).add(monitor);
	}

	@Override
	public void create(MonitorDTO obj) {
		save(obj);		
	}
}
