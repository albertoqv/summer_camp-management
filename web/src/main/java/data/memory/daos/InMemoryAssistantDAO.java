package data.memory.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.MonitorDTO;
import business.dtos.UserDTO;
import business.enums.TimeSlot;
import business.exceptions.dao.NotFoundException;
import business.interfaces.IAssistantDAO;
import business.interfaces.ICriteria;
import data.memory.MapsManager;

/**
 * La clase InMemoryAssistantRepository es una implementación en memoria de un repositorio de asistentes.
 
 */
public class InMemoryAssistantDAO implements IAssistantDAO {
    private MapsManager mapsManager;

    /**
     * Constructor de la clase InMemoryAssistantRepository.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria.
     */
    public InMemoryAssistantDAO() {
        this.mapsManager = MapsManager.getInstance();
    }

    /**
     * Busca a un asistente por su identificador.
     *
     * @param identifier El identificador del asistente a buscar.
     * @return El asistente encontrado.
     * @throws NotFoundException Si el asistente no se encuentra en el repositorio.
     */
    @Override
    public AssistantDTO find(Integer identifier) {
        if (this.mapsManager.getMapOfAssistants().get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapsManager.getMapOfAssistants().get(identifier);
    }

    /**
     * Guarda a un asistente en el repositorio.
     *
     * @param obj El asistente a guardar en el repositorio.
     */
    @Override
    public void save(AssistantDTO obj) {
        this.mapsManager.getMapOfAssistants().put(obj.getId(), obj);
    }

    /**
     * Obtiene una lista de todos los asistentes almacenados en el repositorio.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<AssistantDTO> getAll(Optional<ICriteria> criteria) {
        List<AssistantDTO> allAssistants = new ArrayList<>(this.mapsManager.getMapOfAssistants().values());
        return allAssistants;
    }

    /**
     * Elimina a un asistente del repositorio.
     *
     * @param obj El asistente a eliminar del repositorio.
     */
    @Override
    public void delete(AssistantDTO obj) {
        this.mapsManager.getMapOfAssistants().remove(obj.getId());
    }

	@Override
	public List<AssistantDTO> getAssistantsInACamp(CampDTO camp) {
		InMemoryInscriptionDAO inscriptionRepository = new InMemoryInscriptionDAO();
		List<InscriptionDTO> inscriptions = inscriptionRepository.getAll();
		
		List<AssistantDTO> assistants = new ArrayList<AssistantDTO>();
		for (InscriptionDTO inscriptionDTO : inscriptions) {
			if(inscriptionDTO.getCampId() == camp.getCampID()) {
				assistants.add(find(inscriptionDTO.getAssistantId()));
			}
		}
		return assistants;
	}

	@Override
	public List<AssistantDTO> getAssistantsInAnActivity(ActivityDTO activity) {
		InMemoryInscriptionDAO inscriptionRepository = new InMemoryInscriptionDAO();
		InMemoryActivityDAO activityRepository = new InMemoryActivityDAO();
		InMemoryCampDAO campRepository = new InMemoryCampDAO();
		List<InscriptionDTO> inscriptions = inscriptionRepository.getAll();
		
		List<AssistantDTO> assistants = new ArrayList<AssistantDTO>();
		for (InscriptionDTO inscriptionDTO : inscriptions) {
			CampDTO camp = campRepository.find(inscriptionDTO.getCampId());
			List<ActivityDTO> activities = activityRepository.getActivitiesInACamp(camp);
			for (ActivityDTO activityi: activities) {				
				if(activityi.getActivityName() == activity.getActivityName()) {
					if (inscriptionDTO.isPartial()) {
						if (activity.getTimeSlot() == TimeSlot.MORNING) {
							assistants.add(find(inscriptionDTO.getAssistantId()));
						}
					}else {
						assistants.add(find(inscriptionDTO.getAssistantId()));
					}
					
				}
			}
			
		}
		return assistants;
	}

	@Override
	public void saveAndRelateWithAnUser(AssistantDTO assistant, UserDTO user) {
		if (this.mapsManager.getMapOfAssistantUser().get(user.getEmail()) == null) {
            this.mapsManager.getMapOfAssistantUser().put(user.getEmail(), new ArrayList<AssistantDTO>());
        }
		
		this.mapsManager.getMapOfAssistantUser().get(user.getEmail()).add(assistant);
	}

	@Override
	public List<AssistantDTO> getAssistantsRelatedWithAnUser(UserDTO user) {
		List<AssistantDTO> assistants = new ArrayList<AssistantDTO>();
		
		if (this.mapsManager.getMapOfAssistantUser().get(user.getEmail()) != null) {
			assistants = this.mapsManager.getMapOfAssistantUser().get(user.getEmail());
		}
		
		return assistants;
	}

	@Override
	public void create(AssistantDTO obj) {
		save(obj);		
	}
}
