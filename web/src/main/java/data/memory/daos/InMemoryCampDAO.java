package data.memory.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.CampDTO;
import business.exceptions.dao.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import data.memory.MapsManager;

/**
 * La clase InMemoryCampRepository es una implementación en memoria de un repositorio de campamentos.
 
 */
public class InMemoryCampDAO implements IDAO<CampDTO, Integer> {
    private MapsManager mapsManager;

    /**
     * Constructor de la clase InMemoryCampRepository.
     * Inicializa un nuevo mapa para almacenar campamentos en memoria.
     */
    public InMemoryCampDAO() {
        this.mapsManager = MapsManager.getInstance();
    }

    /**
     * Busca un campamento por su identificador.
     *
     * @param identifier El identificador del campamento a buscar.
     * @return El campamento encontrado.
     * @throws NotFoundException Si el campamento no se encuentra en el repositorio.
     */
    @Override
    public CampDTO find(Integer identifier) {
        if (this.mapsManager.getMapOfCamp().get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapsManager.getMapOfCamp().get(identifier);
    }

    /**
     * Guarda un campamento en el repositorio.
     *
     * @param obj El campamento a guardar en el repositorio.
     */
    @Override
    public void save(CampDTO obj) {
        this.mapsManager.getMapOfCamp().put(obj.getCampID(), obj);
    }

    /**
     * Obtiene una lista de todos los campamentos almacenados en el repositorio.
     *
     * @return Una lista de campamentos.
     */
    @Override
    public List<CampDTO> getAll(Optional<ICriteria> criteria) {
        List<CampDTO> allCamps = new ArrayList<>(this.mapsManager.getMapOfCamp().values());
        return allCamps;
    }

    /**
     * Elimina un campamento del repositorio.
     *
     * @param obj El campamento a eliminar del repositorio.
     */
    @Override
    public void delete(CampDTO obj) {
        this.mapsManager.getMapOfCamp().remove(obj.getCampID());
    }

	@Override
	public void create(CampDTO obj) {
		save(obj);		
	}
}
