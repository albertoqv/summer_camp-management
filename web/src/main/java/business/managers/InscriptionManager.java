package business.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.enums.EducativeLevel;
import business.enums.TimeSlot;
import business.exceptions.dao.NotFoundException;
import business.exceptions.inscription.AfterEarlyTimeException;
import business.exceptions.inscription.AfterStartTimeException;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.inscription.MaxAssistantExcededException;
import business.exceptions.inscription.NeedToAddAnSpecialMonitorException;
import business.exceptions.inscription.WrongEducativeLevelException;
import business.factories.AbstractInscriptionFactory;
import business.factories.EarlyRegisterInscriptionFactory;
import business.factories.LateRegisterInscriptionFactory;
import business.interfaces.IAssistantDAO;
import business.interfaces.IDAO;
import business.interfaces.IInscriptionDAO;
import business.utilities.Utils;
import data.database.criteria.ActivityInCampCriteria;

/**
 * Esta clase gestiona las inscripciones en los campamentos y actividades.
 */

public class InscriptionManager {
	private IDAO<CampDTO, Integer> campRepository;
	private IDAO<ActivityDTO, String> activityRepository;
	private IAssistantDAO assitantRepository;
	private IInscriptionDAO inscriptionRepository;

	  /**
     * Constructor de InscriptionManager.
     *
     * @param campRepository        Repositorio de campamentos.
     * @param activityRepository    Repositorio de actividades.
     * @param assitantRepository   Repositorio de asistentes.
     * @param inscriptionRepository Repositorio de inscripciones.
	 */

	public InscriptionManager(IDAO<CampDTO, Integer> campRepository, IDAO<ActivityDTO, String> activityRepository, IAssistantDAO assitantRepository, IInscriptionDAO inscriptionRepository) {
		this.campRepository = campRepository;
		this.activityRepository = activityRepository;
		this.assitantRepository = assitantRepository;
		this.inscriptionRepository = inscriptionRepository;
	}
	
	/**
     * Realiza la inscripción de un asistente en un campamento.
     *
     * @param assistantId           ID del asistente.
     * @param campId                ID del campamento.
     * @param inscriptionDate       Fecha de inscripción.
     * @param isPartial             Indica si la inscripción es parcial.
     * @param needSpecialAttention  Indica si se necesita atención especial.
     * @return La inscripción creada.
     */

	public InscriptionDTO enroll(int assistantId, int campId, Date inscriptionDate, 
			boolean isPartial, boolean needSpecialAttention) 
	{
		this.ensureEducativeLevelIsCorrect(assistantId, campId, inscriptionDate);
		this.ensureIfThereAreAnSpecialMonitorIfIsNecessary(campId, needSpecialAttention);
		this.ensureIfTheAssistantIsAlreadyEnrolled(assistantId, campId);
		this.ensureAnyActivityIsFully(campId, isPartial);
		
		float price = this.calculatePrice(campId, isPartial);
		
		InscriptionDTO inscription;
		try {
			EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository, assitantRepository);
			inscription = create(factory, assistantId, campId, inscriptionDate, isPartial, price);
		} catch (AfterEarlyTimeException e) {
			LateRegisterInscriptionFactory factory = new LateRegisterInscriptionFactory(campRepository, assitantRepository);
			inscription = create(factory, assistantId, campId, inscriptionDate, isPartial, price);
		}
		
		
		this.inscriptionRepository.save(inscription);
		return inscription;
	}
	
	/**
	 * Crea una inscripción utilizando una fábrica de inscripciones.
	 *
	 * @param factory       Fábrica de inscripciones a utilizar.
	 * @param assistantId   ID del asistente.
	 * @param campId        ID del campamento.
	 * @param inscriptionDate Fecha de inscripción.
	 * @param isPartial     Indica si la inscripción es parcial.
	 * @param price         Precio de la inscripción.
	 * @return La inscripción creada.
	 */

	private InscriptionDTO create(AbstractInscriptionFactory factory, int assistantId, int campId, 
			Date inscriptionDate, boolean isPartial, float price ) {
		InscriptionDTO inscription;
		if (! isPartial) {
			inscription = factory.createComplete(assistantId, campId, inscriptionDate, price);
		} else {
			inscription = factory.createPartial(assistantId, campId, inscriptionDate, price);
		}
		return inscription;
	}
	
	/**
	 * Verifica si el nivel educativo del asistente es correcto para el campamento.
	 *
	 * @param assistantId   ID del asistente.
	 * @param campId        ID del campamento.
	 * @param inscriptionDate Fecha de inscripción.
	 * @throws WrongEducativeLevelException Si el nivel educativo no es adecuado.
	 */

	private void ensureEducativeLevelIsCorrect(int assistantId, int campId, Date inscriptionDate) {
		AssistantDTO assistant = this.assitantRepository.find(assistantId);
		CampDTO camp = this.campRepository.find(campId);
		
		EducativeLevel campLevel = camp.getEducativeLevel();
		
		EducativeLevel assistantLevel;
		long assistantAge = Utils.yearsBetween(inscriptionDate, assistant.getBirthDate());
		if ((assistantAge >= 4) && (assistantAge <= 6)) {
			assistantLevel = EducativeLevel.ELEMENTARY;
		} else if ((assistantAge >= 7) && (assistantAge <= 12)) {
			assistantLevel = EducativeLevel.PRESCHOOL;
		} else if ((assistantAge >= 13) && (assistantAge <= 17)) {
			assistantLevel = EducativeLevel.TEENAGER;
		} else {
			throw new WrongEducativeLevelException();
		}
		
		if (campLevel != assistantLevel) {
			throw new WrongEducativeLevelException();
		}
	}
	
	/**
	 * Verifica si se requiere un monitor especial para el campamento.
	 *
	 * @param campId                ID del campamento.
	 * @param needSpecialAttention  Indica si se necesita atención especial.
	 * @throws NeedToAddAnSpecialMonitorException Si no hay un monitor especial y se necesita atención especial.
	 */

	private void ensureIfThereAreAnSpecialMonitorIfIsNecessary(int campId, boolean needSpecialAttention) {
		CampDTO camp = campRepository.find(campId);
		if ((needSpecialAttention)&&(camp.getSpecialMonitor()== null)) {
			throw new NeedToAddAnSpecialMonitorException();
		}
	}
	
	/**
	 * Verifica si el asistente ya está inscrito en el campamento.
	 *
	 * @param assistantId   ID del asistente.
	 * @param campId        ID del campamento.
	 * @throws AssistantAlreadyEnrolledException Si el asistente ya está inscrito.
	 */

	private void ensureIfTheAssistantIsAlreadyEnrolled(int assistantId, int campId) {
		String inscriptionId = assistantId + "-" + campId;
		try {
			this.inscriptionRepository.find(inscriptionId);
			throw new AssistantAlreadyEnrolledException();
		} catch (NotFoundException e) {}
	}
	
	/**
	 * Calcula el precio de la inscripción en función del campamento y si es parcial.
	 *
	 * @param campId        ID del campamento.
	 * @param isPartial     Indica si la inscripción es parcial.
	 * @return El precio de la inscripción.
	 */

	private float calculatePrice(int campId, boolean isPartial) {
		CampDTO camp = this.campRepository.find(campId);
        List<ActivityDTO> activities = this.activityRepository.getAll(Optional.of(new ActivityInCampCriteria(camp.getCampID())));;
		int nActivities =activities.size();
		float basePrice = isPartial ? 100 : 300;
		
		return basePrice + 20*nActivities;
	}
	
	/**
	 * Verifica si alguna actividad del campamento está completamente llena.
	 *
	 * @param campId        ID del campamento.
	 * @param isPartial     Indica si la inscripción es parcial.
	 * @throws MaxAssistantExcededException Si alguna actividad está completamente llena.
	 */

	private void ensureAnyActivityIsFully(int campId, boolean isPartial) {
		CampDTO camp = this.campRepository.find(campId);
        List<ActivityDTO> activities = this.activityRepository.getAll(Optional.of(new ActivityInCampCriteria(camp.getCampID())));;
		for (int i = 0; i < activities.size(); i++) {
			ActivityDTO activity = activities.get(i);
			List<AssistantDTO> assistants = this.assitantRepository.getAssistantsInAnActivity(activity);
			TimeSlot timeSlot = activity.getTimeSlot();
			if (isPartial) {
				if ((assistants.size() == activity.getMaxAssistants()) && (timeSlot == TimeSlot.MORNING)) {
					throw new MaxAssistantExcededException();
				}
			} else {			
				if (assistants.size() == activity.getMaxAssistants()) {
					throw new MaxAssistantExcededException();
				}
			}
		}
	}
	
	/**
 * Obtiene la lista de asistentes de un campamento a partir de las inscripciones.
 *
 * @param camp El campamento del que se desea obtener la lista de asistentes.
 * @return La lista de asistentes del campamento.
 */

	private List<AssistantDTO> getAssistantsOfACamp(CampDTO camp) {
		List<InscriptionDTO> allInscriptions = this.inscriptionRepository.getAll();
		List<AssistantDTO> assistantsOfCamp = new ArrayList<AssistantDTO>();
		
		for (int i = 0; i < allInscriptions.size(); i++) {
			InscriptionDTO inscription = allInscriptions.get(i);
			if (inscription.getCampId() == camp.getCampID()) {
				AssistantDTO assistant = this.assitantRepository.find(inscription.getAssistantId());
				assistantsOfCamp.add(assistant);
			}
			
		}
		
		return assistantsOfCamp;
	}

	/**
	 * Obtiene la lista de campamentos disponibles en una fecha dada.
	 *
	 * @param actualDate Fecha actual para la comparación.
	 * @return La lista de campamentos disponibles.
	 */

	public List<CampDTO> avaliableCamps(Date actualDate){
		List<CampDTO> allCamps = this.campRepository.getAll();
		List<CampDTO> avaliableCamps = new ArrayList<CampDTO>();
		
		for (int i = 0; i < allCamps.size(); i++) {
			CampDTO camp = allCamps.get(i);
			List<AssistantDTO> assistants = getAssistantsOfACamp(camp);
			
			if (camp.getStart().after(actualDate) && (Utils.daysBetween(camp.getStart(), actualDate) > 2) && (assistants.size() < camp.getCapacity())) {
				avaliableCamps.add(camp);
			}
		}
    	
		
		return avaliableCamps;
	}
	
	public int getNumberOfInscriptions(CampDTO camp) {
		return this.inscriptionRepository.getInscriptionOfACamp(camp).size();
	}
	
	
	public int getNumberOfPartialInscriptions(CampDTO camp) {
		int n = 0;
		
		for (InscriptionDTO inscription : this.inscriptionRepository.getInscriptionOfACamp(camp)) {
			if (inscription.isPartial()) {
				n++;
			}
		}
		
		return n;
	}
	
	public int getNumberOfCompleteInscriptions(CampDTO camp) {
		int n = 0;
		
		for (InscriptionDTO inscription : this.inscriptionRepository.getInscriptionOfACamp(camp)) {
			if (!inscription.isPartial()) {
				n++;
			}
		}
		
		return n;
	}
	
	public List<InscriptionDTO> getInscriptionsOfAnAssistant(AssistantDTO assistantDTO) {
		List<InscriptionDTO> allInscriptions = this.inscriptionRepository.getAll();
		List<InscriptionDTO> inscriptionsOfAnAssistant = new ArrayList<InscriptionDTO>();
		
		for (int i = 0; i < allInscriptions.size(); i++) {
			InscriptionDTO inscription = allInscriptions.get(i);
			int inscriptionAssistantId = inscription.getAssistantId();
			int assistantId = assistantDTO.getId();
			if (inscriptionAssistantId == assistantId) {
				CampDTO camp = this.campRepository.find(inscription.getCampId());
				inscriptionsOfAnAssistant.add(inscription);
			}
			
		}
		
		return inscriptionsOfAnAssistant;
	}
	
	public boolean willBeEarly(int campId, Date inscriptionDate) {
		
		CampDTO camp = this.campRepository.find(campId);
		
		boolean isEarly = true;
		
		long daysDifference = Utils.daysBetween(camp.getStart(), inscriptionDate);

        if ((daysDifference <= 15) && (daysDifference > 0)) {
            isEarly = false;
        } else if (daysDifference < 0) {
            isEarly = false;
        }
        
        return isEarly;
	}
	
	public void deleteInscription(String inscriptionId) {
        try {
			InscriptionDTO inscription = this.inscriptionRepository.find(inscriptionId);
			this.inscriptionRepository.delete(inscription);
		} catch (NotFoundException e) {}
    }

}
