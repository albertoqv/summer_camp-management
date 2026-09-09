package business.managers;

import java.util.Date;
import java.util.List;

import business.dtos.AssistantDTO;
import business.dtos.UserDTO;
import business.enums.UserRole;
import business.exceptions.assistant.AssistantNotFoundException;
import business.exceptions.dao.NotFoundException;
import business.exceptions.user.InvalidRoleException;
import business.exceptions.user.RegisterException;
import business.exceptions.user.UserAlreadyExistsException;
import business.exceptions.user.WrongCredentialsException;
import business.interfaces.IAssistantDAO;
import business.interfaces.IDAO;

public class UsersManager {
	private IDAO<UserDTO, String> userDAO;
	private IAssistantDAO assistantDAO;
	
	public UsersManager(IDAO<UserDTO, String> userDAO, IAssistantDAO assistantDAO) {
		this.assistantDAO = assistantDAO;
		this.userDAO = userDAO;
	}
	
	public UserDTO verifyCredentials(String email, String password) {
		UserDTO user;
		try {
			user = this.userDAO.find(email);
			if (!user.getPassword().equals(password)) {
				throw new WrongCredentialsException();
			}
		} catch (NotFoundException e) {			
			throw new WrongCredentialsException();
		}
		return user;
	}
	
	public void registerUser(String email, String pasword, String role) {
		UserRole roleUser = null;
		try {			
			roleUser = UserRole.valueOf(role);
		} catch (IllegalArgumentException e) {
			throw new InvalidRoleException();
		}
		
		try {
			userDAO.find(email);
			throw new UserAlreadyExistsException();
		}catch (NotFoundException e) {
			try {
				userDAO.save(new UserDTO(email, pasword, roleUser));		
			} catch (RuntimeException ev) {
				throw new RegisterException();
			}
		}
	}
	
	public void registerAssitantInfo(String email, int id) {
		AssistantDTO assistant = this.assistantDAO.find(id);
		
		this.assistantDAO.saveAndRelateWithAnUser(assistant, new UserDTO(email, null, null));
	}
	
	public boolean hasAssistantInfo(String email) {
		try {			
			List<AssistantDTO> result = this.assistantDAO.getAssistantsRelatedWithAnUser(new UserDTO(email, null, null));
			if(result.size() == 0) {
				return false;
			}
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}
	
	public AssistantDTO getAssistantInfo(String email) {
		try {			
			List<AssistantDTO> result = this.assistantDAO.getAssistantsRelatedWithAnUser(new UserDTO(email, null, null));
			if(result.size() == 0) {
				return null;
			}
			return result.get(0);
		} catch (NotFoundException e) {
			return null;
		}
	}
	
	public void updateUser(UserDTO user) {
        if (!isRegistered(user)) {
            throw new AssistantNotFoundException();
        }
        this.userDAO.save(user);
    }
	
	public boolean isRegistered(UserDTO user) {
        try {
            this.userDAO.find(user.getEmail());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
