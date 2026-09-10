package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.dtos.AssistantDTO;
import business.exceptions.assistant.AssistantAlreadyRegisteredException;
import business.exceptions.assistant.AssistantNotFoundException;
import business.managers.AssistantsManager;
import business.utilities.Utils;
import data.memory.MapsManager;
import data.memory.daos.InMemoryAssistantDAO;

class AssistantsManagerTest {

	@BeforeEach
	void setUp() {
		MapsManager.resetInstance();
	}
	
	@Test
	void registerAssistant_whenAssistantIsNotRegistered_thenRegisterTheAssistant(){
		InMemoryAssistantDAO assistantRepository = new InMemoryAssistantDAO();
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		
		assistantsManager.registerAssistant(assistant);
		
		assertEquals(true, assistantsManager.isRegistered(assistant));
	}
	
	@Test
	void registerAssistant_whenAssistantIsRegistered_throwsAssitantisAlreadyRegisteredException(){
		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		InMemoryAssistantDAO assistantRepository = new InMemoryAssistantDAO();
		assistantRepository.save(assistant);
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
	
		
	
		assertThrows(AssistantAlreadyRegisteredException.class, 
				() ->assistantsManager.registerAssistant(assistant)
		);	
	}
	
	@Test
	void updateAssistant_whenAssistantIsRegistered_thenUpdateTheAssistant(){
		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		InMemoryAssistantDAO assistantRepository = new InMemoryAssistantDAO();
		assistantRepository.save(assistant);
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		
		
		assistantsManager.updateAssistant(new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				false
			));
		
		assertEquals("José", assistantRepository.find(1).getFirstName());
		assertEquals("Trujillo", assistantRepository.find(1).getLastName());
		assertEquals(Utils.parseDate("26/01/2001").getTime(), assistantRepository.find(1).getBirthDate().getTime());
		assertEquals(false, assistantRepository.find(1).isRequireSpecialAttention());
	}
	
	@Test
	void updateAssistant_whenAssistantIsNotRegistered_throwsAssitantisNotFoundException(){
		InMemoryAssistantDAO assistantRepository = new InMemoryAssistantDAO();
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
		
		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				false
			);
		
		assertThrows(AssistantNotFoundException.class, 
				() -> assistantsManager.updateAssistant(assistant)
		);
	}
	
	@Test
	void getListOfRegisteredAssistant_returnListRegisteredAssitant(){
		InMemoryAssistantDAO assistantRepository = new InMemoryAssistantDAO();
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);		
		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				);
		
		assistantsManager.registerAssistant(assistant);
		List<AssistantDTO> list = new ArrayList<AssistantDTO>();
		list.add(assistant);
		assertEquals(list, assistantsManager.getListOfRegisteredAssistant());
	}
}
