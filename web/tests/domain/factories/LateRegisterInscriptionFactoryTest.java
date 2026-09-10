package domain.factories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.enums.EducativeLevel;
import business.exceptions.assistant.AssistantNotFoundException;
import business.exceptions.camp.CampNotFoundException;
import business.exceptions.inscription.AfterLateTimeException;
import business.exceptions.inscription.AfterStartTimeException;
import business.exceptions.inscription.BeforeLateTimeException;
import business.factories.LateRegisterInscriptionFactory;
import business.interfaces.IDAO;
import business.utilities.Utils;
import data.memory.MapsManager;
import data.memory.daos.InMemoryAssistantDAO;
import data.memory.daos.InMemoryCampDAO;

class LateRegisterInscriptionFactoryTest  {

	@BeforeEach
	void setUp() {
		MapsManager.resetInstance();
	}
	
	@Test
	void createPartial_whenTheDateIsInTime_createsAnInscriptionThatCanNotBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("10/1/2024");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		InscriptionDTO inscription = factory.createPartial(assistantId, campId, inscriptionDate, price);

		assertEquals(false, inscription.canBeCanceled());
	}

	
	@Test
	void createPartial_whenTheDateIsNotInTime_throwAfterStartTimeExceptionException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("18/01/2024");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterStartTimeException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheDateIsNotInTime_throwBeforeLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("24/12/2023");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(BeforeLateTimeException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheDateIsNotInTime_throwAfterLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("13/01/2024");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterLateTimeException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}

	@Test
	void createPartial_whenTheAssistantNotExist_throwsAssistantNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, new InMemoryAssistantDAO());
		
		assertThrows(AssistantNotFoundException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createPartial_whenTheCampNotExist_throwsCamptNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(new InMemoryCampDAO(), assistantIRepository);
		
		assertThrows(CampNotFoundException.class, 
				() -> factory.createPartial(assistantId, campId, inscriptionDate, price)
		);
	}

	@Test
	void createComplete_whenTheDateIsInTime_createsAnInscriptionThatCanNotBeCancelled() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("10/1/2024");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		InscriptionDTO inscription = factory.createComplete(assistantId, campId, inscriptionDate, price);

		assertEquals(false, inscription.canBeCanceled());
	}

	
	@Test
	void createComplete_whenTheDateIsNotInTime_throwAfterStartTimeExceptionException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("18/01/2024");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterStartTimeException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheDateIsNotInTime_throwBeforeLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("24/12/2023");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(BeforeLateTimeException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheDateIsNotInTime_throwAfterLateTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("13/01/2024");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assistantIRepository);
		
		assertThrows(AfterLateTimeException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}

	@Test
	void createComplete_whenTheAssistantNotExist_throwsAssistantNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IDAO<CampDTO, Integer> campRepository = new InMemoryCampDAO();
		campRepository.save(new CampDTO(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, new InMemoryAssistantDAO());
		
		assertThrows(AssistantNotFoundException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
	
	@Test
	void createComplete_whenTheCampNotExist_throwsCamptNotFoundException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		IDAO<AssistantDTO, Integer> assistantIRepository = new InMemoryAssistantDAO();
		assistantIRepository.save(new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2001"),
				true
				));
		
		LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(new InMemoryCampDAO(), assistantIRepository);
		
		assertThrows(CampNotFoundException.class, 
				() -> factory.createComplete(assistantId, campId, inscriptionDate, price)
		);
	}
}
