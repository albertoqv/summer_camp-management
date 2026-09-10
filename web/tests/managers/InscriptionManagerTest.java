package managers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.CompleteInscriptionDTO;
import business.dtos.InscriptionDTO;
import business.enums.EducativeLevel;
import business.enums.TimeSlot;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.inscription.MaxAssistantExcededException;
import business.exceptions.inscription.NeedToAddAnSpecialMonitorException;
import business.exceptions.inscription.WrongEducativeLevelException;
import business.managers.AssistantsManager;
import business.managers.CampsManager;
import business.managers.InscriptionManager;
import business.utilities.Utils;
import data.memory.MapsManager;
import data.memory.daos.InMemoryActivityDAO;
import data.memory.daos.InMemoryAssistantDAO;
import data.memory.daos.InMemoryCampDAO;
import data.memory.daos.InMemoryInscriptionDAO;
import data.memory.daos.InMemoryMontiorDAO;

class InscriptionManagerTest {
	private InMemoryCampDAO campRepository;
	private InMemoryActivityDAO activityRepository;
	private InMemoryMontiorDAO monitorRepository;
	private InMemoryAssistantDAO assistantRepository;
	private InMemoryInscriptionDAO inscriptionRepository;
	private InscriptionManager inscriptionManager;
	private CampsManager campsManager;
	private AssistantsManager assistantManager;

	private void performCampAndActivitiesSetup(int assistantId, int campID, int numActivities) {
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				assistantId,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
		);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity
		);

		campRepository.save(camp);
		assistantRepository.save(assistant);

		if (numActivities >= 1) {
			for (int i = 1; i <= numActivities; i++) {
				ActivityDTO activity = new ActivityDTO(
						"Actividad" + i,
						educativeLevel,
						TimeSlot.AFTERNOON,
						10,
						0
				);
				campsManager.registerActivityInACamp(camp, activity);
			}
		}
	}


	@BeforeEach
	void SetUp() {
		MapsManager.resetInstance();
		this.campRepository = new InMemoryCampDAO();
		this.activityRepository = new InMemoryActivityDAO();
		this.monitorRepository = new InMemoryMontiorDAO();
		this.assistantRepository = new InMemoryAssistantDAO();
		this.inscriptionRepository = new InMemoryInscriptionDAO();
		this.inscriptionManager = new InscriptionManager(campRepository, activityRepository, assistantRepository, inscriptionRepository);
		this.campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		this.assistantManager = new AssistantsManager(assistantRepository);
	}

	@Test
	void enroll_whenTheDateIsMoreThan15DaysBefore_thenReturnAnEarlyRegisterInscription() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 0);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertInstanceOf(CompleteInscriptionDTO.class, inscription);
		assertEquals(1, inscription.getAssistantId());
		assertEquals(campID, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(true, inscription.canBeCanceled());
	}
	
	@Test
	void enroll_whenTheDateIsNotMoreThan15DaysBeforeAndMoreThan2DaysBefore_thenReturnAnLateRegisterInscription() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 0);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertInstanceOf(CompleteInscriptionDTO.class, inscription);
		assertEquals(1, inscription.getAssistantId());
		assertEquals(campID, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(false, inscription.canBeCanceled());
	}
	
	@Test
	void enroll_whenTheAssistantIsAlreadyEnrolledInTheCamp_throwsAssistantAlreadyEnrolledException() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 0);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertThrows(AssistantAlreadyEnrolledException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, false)
		);
	}
	
	
	@Test
	void enroll_whenISCompleteRegisteredAndNoActivities_thenPrices300_Early() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 0);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertEquals(300, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndNoActivities_thenPrices100_Early() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 0);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		assertEquals(100, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndOneActivity_thenPrices320_Early() {
		int campID = 1;
		
		performCampAndActivitiesSetup(1, 1, 1);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		
		assertEquals(320, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndOneActivity_thenPrices120_Early() {
		int campID = 1;
		
		performCampAndActivitiesSetup(1, 1, 1);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		
		assertEquals(120, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndOneActivity_thenPrices400_Early() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 5);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		
		assertEquals(400, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndOneActivity_thenPrices200_Early() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 5);
		
		Date inscriptionDate = Utils.parseDate("25/12/2023");
		
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		
		assertEquals(200, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndNoActivities_thenPrices300_Late() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 0);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertEquals(300, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndNoActivities_thenPrices100_Late() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 0);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		assertEquals(100, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndOneActivity_thenPrices320_Late() {
		int campID = 1;
		
		performCampAndActivitiesSetup(1, 1, 1);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		
		assertEquals(320, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndOneActivity_thenPrices120_Late() {
		int campID = 1;
		
		performCampAndActivitiesSetup(1, 1, 1);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		
		assertEquals(120, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISCompleteRegisteredAndOneActivity_thenPrices400_Late() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 5);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		
		assertEquals(400, inscription.getPrice());
	}
	
	@Test
	void enroll_whenISPartialRegisteredAndOneActivity_thenPrices200_Late() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		ActivityDTO activity2 = new ActivityDTO(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		ActivityDTO activity3 = new ActivityDTO(
				"Actividad3",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		ActivityDTO activity4 = new ActivityDTO(
				"Actividad4",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		ActivityDTO activity5 = new ActivityDTO(
				"Actividad5",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		
		
		campsManager.registerActivityInACamp(camp, activity);
		campsManager.registerActivityInACamp(camp, activity2);
		campsManager.registerActivityInACamp(camp, activity3);
		campsManager.registerActivityInACamp(camp, activity4);
		campsManager.registerActivityInACamp(camp, activity5);
		
		
		
		InscriptionDTO inscription = inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		
		assertEquals(200, inscription.getPrice());
	}
	
	@Test
	void enroll_whenTheAssistantNeedSpecialAttentioandthedontCampSpceialMonitor_throwsNeedToAddAnSpecialMonitorException() {
		int campID = 1;

		performCampAndActivitiesSetup(1, 1, 0);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		
				
		
		assertThrows(NeedToAddAnSpecialMonitorException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, true)
		);
	}
	
	@Test
	void avaliableCamps_whenAllCampamentsHaveNotStartAndHaveCapacity_thenReturnAllCampaments() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		CampDTO camp1 = new CampDTO(
				2,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		CampDTO camp2 = new CampDTO(
				3,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date actualDate = Utils.parseDate("10/01/2024");
		
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		
		assistantsManager.registerAssistant(assistant);
		campManager.registerCamp(camp);
		campManager.registerCamp(camp1);
		campManager.registerCamp(camp2);
		
		List<CampDTO> expectedResult = new ArrayList<CampDTO>();
		expectedResult.add(camp);
		expectedResult.add(camp1);
		expectedResult.add(camp2);
		
		assertEquals(expectedResult, inscriptionManager.avaliableCamps(actualDate));
	}
	
	@Test
	void avaliableCamps_whenThereAreSomeCampThatAlreadyStart_thenReturnOnlyCampamentsThatHaveNotStartAndHaveCapacity() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		CampDTO camp1 = new CampDTO(
				2,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		CampDTO camp2 = new CampDTO(
				3,
				Utils.parseDate("09/01/2024"),
				end,
				educativeLevel,
				capacity				
		);
		
		Date actualDate = Utils.parseDate("10/01/2024");
		
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		
		assistantsManager.registerAssistant(assistant);
		campManager.registerCamp(camp);
		campManager.registerCamp(camp1);
		campManager.registerCamp(camp2);
		
		List<CampDTO> expectedResult = new ArrayList<CampDTO>();
		expectedResult.add(camp);
		expectedResult.add(camp1);
		
		assertEquals(expectedResult, inscriptionManager.avaliableCamps(actualDate));
	}
	
	@Test
	void avaliableCamps_whenThereAreSomeCampThatIsFull_thenReturnOnlyCampamentsThatHaveNotStartAndHaveCapacity() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		CampDTO camp1 = new CampDTO(
				2,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		CampDTO camp2 = new CampDTO(
				3,
				start,
				end,
				educativeLevel,
				1				
		);
		
		Date inscriptionDate = Utils.parseDate("8/01/2024");

		Date actualDate = Utils.parseDate("10/01/2024");
		
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		CampsManager campManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		
		
		assistantsManager.registerAssistant(assistant);
		campManager.registerCamp(camp);
		campManager.registerCamp(camp1);
		campManager.registerCamp(camp2);
		inscriptionManager.enroll(assistant.getId(), 3, inscriptionDate, false, false);
		
		List<CampDTO> expectedResult = new ArrayList<CampDTO>();
		expectedResult.add(camp);
		expectedResult.add(camp1);
		
		assertEquals(expectedResult, inscriptionManager.avaliableCamps(actualDate));
	}
	
	@Test
	void enrollComplete_whenThereAreEnoughtSpace_thenAssistantIsAddedToAllActivitiesOfTheCamp() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		ActivityDTO activity2 = new ActivityDTO(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		;

		assistantManager.registerAssistant(assistant);
		campsManager.registerCamp(camp);
		
		
		campsManager.registerActivityInACamp(camp, activity);
		campsManager.registerActivityInACamp(camp, activity2);
		
		
		
		inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
		
		assertEquals(true, this.assistantRepository.getAssistantsInAnActivity(activity).contains(assistant));
		assertEquals(true, this.assistantRepository.getAssistantsInAnActivity(activity2).contains(assistant));
	}
	
	@Test
	void enrollComplete_whenThereAreNotEnoughtSpace_throwsMaxAssistantExcededException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		
		AssistantDTO assistant2 = new AssistantDTO(
				2,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		ActivityDTO activity2 = new ActivityDTO(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				1,
				0
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		assistantRepository.save(assistant2);
		
		
		
		campsManager.registerActivityInACamp(camp, activity);
		campsManager.registerActivityInACamp(camp, activity2);
		
		
		
		inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				false, 
				false);
				
		
		assertThrows(MaxAssistantExcededException.class, 
				() -> inscriptionManager.enroll(2, campID, inscriptionDate, false, false)
		);
	}
	
	@Test
	void enrollPartial_whenThereAreEnoughtSpace_thenAssistantIsAddedToMorningActivitiesOfTheCamp() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		
		AssistantDTO assistant2 = new AssistantDTO(
				2,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				educativeLevel,
				TimeSlot.MORNING,
				10,
				0
		);
		
		ActivityDTO activity2 = new ActivityDTO(
				"Actividad2",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		assistantRepository.save(assistant2);
		
		
		campsManager.registerActivityInACamp(camp, activity);
		campsManager.registerActivityInACamp(camp, activity2);
		
		
		inscriptionManager.enroll(
				2, 
				campID, 
				inscriptionDate, 
				true, 
				false);
		
		inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
		
				
		
		ActivityDTO activityPersisted = activityRepository.find(activity.getActivityName());
		ActivityDTO activity2Persisted = activityRepository.find(activity2.getActivityName());
				
		
		assertEquals(true, this.assistantRepository.getAssistantsInAnActivity(activityPersisted).contains(assistant));
		assertEquals(false, this.assistantRepository.getAssistantsInAnActivity(activity2Persisted).contains(assistant));
	}
	
	@Test
	void enrollPartial_whenThereAreNotEnoughtSpaceInMorningActivities_throwsMaxAssistantExcededException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		
		AssistantDTO assistant2 = new AssistantDTO(
				2,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		ActivityDTO activity = new ActivityDTO(
				"Actividad",
				educativeLevel,
				TimeSlot.AFTERNOON,
				10,
				0
		);
		ActivityDTO activity2 = new ActivityDTO(
				"Actividad2",
				educativeLevel,
				TimeSlot.MORNING,
				1,
				0
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		;
		campRepository.save(camp);
		assistantRepository.save(assistant);
		assistantRepository.save(assistant2);
		
		
		
		campsManager.registerActivityInACamp(camp, activity);
		campsManager.registerActivityInACamp(camp, activity2);
		
		
		
		inscriptionManager.enroll(
				1, 
				campID, 
				inscriptionDate, 
				true, 
				false);
				
		
		assertThrows(MaxAssistantExcededException.class, 
				() -> inscriptionManager.enroll(2, campID, inscriptionDate, true, false)
		);
	}
	
	@Test
	void enroll_whenTheAssistantIsElementaryAndTheActivityIsNot_throwsWrongEducativeLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.TEENAGER;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2019"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		
		
				
		
		assertThrows(WrongEducativeLevelException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, false)
		);
	}
	
	@Test
	void enroll_whenTheAssistantIsPreschoolAndTheActivityIsNot_throwsWrongEducativeLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2015"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		
		
				
		
		assertThrows(WrongEducativeLevelException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, false)
		);
	}
	
	@Test
	void enroll_whenTheAssistantIsTeenagerAndTheActivityIsNot_throwsWrongEducativeLevelException() {
		int campID = 1;
		Date start = Utils.parseDate("15/01/2024");
		Date end = Utils.parseDate("25/01/2024");
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		int capacity = 10;

		AssistantDTO assistant = new AssistantDTO(
				1,
				"José",
				"Trujillo",
				Utils.parseDate("26/01/2010"),
				true
				);
		CampDTO camp = new CampDTO(
				campID,
				start,
				end,
				educativeLevel,
				capacity				
		);
		
		Date inscriptionDate = Utils.parseDate("10/01/2024");
		
		
		campRepository.save(camp);
		assistantRepository.save(assistant);
		
		
				
		
		assertThrows(WrongEducativeLevelException.class, 
				 () -> inscriptionManager.enroll(1, campID, inscriptionDate, false, false)
		);
	}
}
