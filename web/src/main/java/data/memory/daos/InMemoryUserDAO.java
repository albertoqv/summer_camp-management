package data.memory.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.UserDTO;
import business.exceptions.dao.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import data.memory.MapsManager;

public class InMemoryUserDAO implements IDAO<UserDTO, String>{

	private MapsManager mapsManager;

    public InMemoryUserDAO() {
        this.mapsManager = MapsManager.getInstance();
    }


    @Override
    public List<UserDTO> getAll(Optional<ICriteria> criteria) {
        List<UserDTO> allUsers = new ArrayList<>(this.mapsManager.getMapOfUsers().values());
        return allUsers;
    }

	@Override
	public UserDTO find(String identifier) {
		if (this.mapsManager.getMapOfUsers().get(identifier) == null) {
            throw new NotFoundException();
        }
        return this.mapsManager.getMapOfUsers().get(identifier);
	}

	@Override
	public void save(UserDTO obj) {
        this.mapsManager.getMapOfUsers().put(obj.getEmail(), obj);
	}

	@Override
	public void delete(UserDTO obj) {
        this.mapsManager.getMapOfUsers().remove(obj.getEmail());
		
	}


	@Override
	public void create(UserDTO obj) {
		save(obj);		
	}


}
