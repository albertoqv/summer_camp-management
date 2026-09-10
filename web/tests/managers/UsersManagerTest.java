package managers;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.dtos.AssistantDTO;
import business.dtos.UserDTO;
import business.enums.UserRole;
import business.exceptions.user.InvalidRoleException;
import business.exceptions.user.UserAlreadyExistsException;
import business.exceptions.user.WrongCredentialsException;
import business.managers.AssistantsManager;
import business.managers.UsersManager;
import business.utilities.Utils;
import data.memory.MapsManager;
import data.memory.daos.InMemoryAssistantDAO;
import data.memory.daos.InMemoryUserDAO;

class UsersManagerTest {
	@BeforeEach
	void setUp() {
		MapsManager.resetInstance();
	}
	
	@Test
	void verifyCredentials_whenUserExistsAndPasswordIsCorrect_thenReturnsTheUser(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager manager = new UsersManager(userDao, assistantDao);
		
		userDao.save(new UserDTO("prueba@prueba.com", "123", UserRole.ADMIN));
		
		UserDTO user = manager.verifyCredentials("prueba@prueba.com", "123");
		
		assertEquals(user.getEmail(), "prueba@prueba.com");
		assertEquals(user.getPassword(), "123");
		assertEquals(user.getRole(), UserRole.ADMIN);
	}
	
	@Test
	void verifyCredentials_whenUserDoesNotExists_thenDontThrowsWrongCredentialsException(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager manager = new UsersManager(userDao, assistantDao);
				
		assertThrows(WrongCredentialsException.class, () -> {			
			manager.verifyCredentials("prueba@prueba.com", "123");
		});
	}
	
	@Test
	void verifyCredentials_whennUserExistsAndPasswordIsNotCorrect_thenDontThrowsWrongCredentialsException(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager manager = new UsersManager(userDao, assistantDao);
		userDao.save(new UserDTO("prueba@prueba.com", "124", UserRole.ADMIN));

		assertThrows(WrongCredentialsException.class, () -> {			
			manager.verifyCredentials("prueba@prueba.com", "123");
		});
	}
	
	@Test
	void registerUser_whenUserNotExists_thenTheUserIsRegistered(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager manager = new UsersManager(userDao, assistantDao);
		
		
		manager.registerUser("prueba@prueba.com", "123", "ASSISTANT");
		
		
		UserDTO user = manager.verifyCredentials("prueba@prueba.com", "123");
		
		assertEquals(user.getEmail(), "prueba@prueba.com");
		assertEquals(user.getPassword(), "123");
		assertEquals(user.getRole(), UserRole.ASSISTANT);
	}
	
	@Test
	void registerUser_whenUserExists_thenThrowsUserAlreadyRegisteredException(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager manager = new UsersManager(userDao, assistantDao);
		
		
		manager.registerUser("prueba@prueba.com", "123", "ASSISTANT");
		
		assertThrows(UserAlreadyExistsException.class, () -> {			
			manager.registerUser("prueba@prueba.com", "123", "ASSISTANT");
		});
	}
	
	@Test
	void registerUser_whenAnInvalidRoleIsPassed_thenThrowsInvalidRoleException(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager manager = new UsersManager(userDao, assistantDao);
		
		
		
		assertThrows(InvalidRoleException.class, () -> {			
			manager.registerUser("prueba@prueba.com", "123", "123445");
		});
	}
	
	
	@Test
	void hasAssistantInfo_whenThereAreAssistantInfo_thenReturnsTrue(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager userManager = new UsersManager(userDao, assistantDao);
		AssistantsManager assistantsManager =  new AssistantsManager(assistantDao);
		
		userManager.registerUser("prueba@prueba.com", "123", "ASSISTANT");
		UserDTO user = userManager.verifyCredentials("prueba@prueba.com", "123");
		
		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		assistantsManager.registerAssistant(assistant);
		
		assistantDao.saveAndRelateWithAnUser(assistant, user);

		assertTrue(userManager.hasAssistantInfo("prueba@prueba.com"));
	}
	
	@Test
	void hasAssistantInfo_whenThereAreNotAssistantInfo_thenReturnsFalse(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager userManager = new UsersManager(userDao, assistantDao);
		AssistantsManager assistantsManager =  new AssistantsManager(assistantDao);
		
		userManager.registerUser("prueba@prueba.com", "123", "ASSISTANT");
		userManager.verifyCredentials("prueba@prueba.com", "123");
		
		assertFalse(userManager.hasAssistantInfo("prueba@prueba.com"));
	}

	@Test
	void registerAssitant_thenTheUserHasAssistantInfo(){
		InMemoryUserDAO userDao = new InMemoryUserDAO();
		InMemoryAssistantDAO assistantDao = new InMemoryAssistantDAO();
		UsersManager userManager = new UsersManager(userDao, assistantDao);
		AssistantsManager assistantsManager =  new AssistantsManager(assistantDao);
		
		userManager.registerUser("prueba@prueba.com", "123", "ASSISTANT");
		userManager.verifyCredentials("prueba@prueba.com", "123");
		
		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		assistantsManager.registerAssistant(assistant);
		
		userManager.registerAssitantInfo("prueba@prueba.com", 1);
		
		assertTrue(userManager.hasAssistantInfo("prueba@prueba.com"));
	}
}
