package data.memory.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.exceptions.dao.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import business.interfaces.IInscriptionDAO;
import data.memory.MapsManager;

/**
 * La clase InMemoryInscriptionRepository es una implementación en memoria de un repositorio de inscripciones.

 */
public class InMemoryInscriptionDAO implements IInscriptionDAO {
    private MapsManager mapsManager;

    /**
     * Constructor de la clase InMemoryInscriptionRepository.
     * Inicializa un nuevo mapa para almacenar inscripciones en memoria.
     */
    public InMemoryInscriptionDAO() {
        this.mapsManager = MapsManager.getInstance();
    }

    /**
     * Busca una inscripción por su nombre.
     *
     * @param inscriptionName El nombre de la inscripción a buscar.
     * @return La inscripción encontrada.
     * @throws NotFoundException Si la inscripción no se encuentra en el repositorio.
     */
    @Override
    public InscriptionDTO find(String inscriptionName) {
        if (this.mapsManager.getMapOfInscription().get(inscriptionName) == null) {
            throw new NotFoundException();
        }
        return this.mapsManager.getMapOfInscription().get(inscriptionName);
    }

    /**
     * Guarda una inscripción en el repositorio.
     *
     * @param activity La inscripción a guardar en el repositorio.
     */
    @Override
    public void save(InscriptionDTO activity) {
        this.mapsManager.getMapOfInscription().put(activity.getInscriptionIdentifier(), activity);
    }

    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el repositorio.
     *
     * @return Una lista de inscripciones.
     */
    @Override
    public List<InscriptionDTO> getAll(Optional<ICriteria> criteria) {
        List<InscriptionDTO> allInscriptions = new ArrayList<>(this.mapsManager.getMapOfInscription().values());
        return allInscriptions;
    }

    /**
     * Elimina una inscripción del repositorio.
     *
     * @param obj La inscripción a eliminar del repositorio.
     */
    @Override
    public void delete(InscriptionDTO obj) {
        this.mapsManager.getMapOfInscription().remove(obj.getInscriptionIdentifier());
    }

	@Override
	public List<InscriptionDTO> getInscriptionOfACamp(CampDTO camp) {
		return null;
	}

	@Override
	public void create(InscriptionDTO obj) {
		save(obj);		
	}
}
