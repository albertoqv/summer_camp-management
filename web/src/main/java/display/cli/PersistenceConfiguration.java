package display.cli;

import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.interfaces.IActivityDAO;
import business.interfaces.IAssistantDAO;
import business.interfaces.IDAO;
import business.interfaces.IInscriptionDAO;
import business.interfaces.IMonitorDAO;
import data.database.daos.InDatabaseActivityDAO;
import data.database.daos.InDatabaseAssistantDAO;
import data.database.daos.InDatabaseCampDAO;
import data.database.daos.InDatabaseInscriptionDAO;
import data.database.daos.InDatabaseMonitorDAO;

public class PersistenceConfiguration {
	private IDAO<CampDTO, Integer> campRepository;
	private IActivityDAO activityRepository;
	private IMonitorDAO monitorRepository;
	private IAssistantDAO assistantRepository;
	private IInscriptionDAO inscriptionRepository;
	
	private static PersistenceConfiguration instance;
	public static PersistenceConfiguration setUp() {
		if(instance == null) {
			instance = new PersistenceConfiguration();
		}
		return instance;
	}
	
	private PersistenceConfiguration() {
		this.campRepository = new InDatabaseCampDAO();
		this.activityRepository = new InDatabaseActivityDAO();
		this.monitorRepository = new InDatabaseMonitorDAO();
		this.assistantRepository = new InDatabaseAssistantDAO();
		this.inscriptionRepository = new InDatabaseInscriptionDAO();
	}

	public IDAO<CampDTO, Integer> getCampRepository() {
		return campRepository;
	}

	public IActivityDAO getActivityRepository() {
		return activityRepository;
	}

	public IMonitorDAO getMonitorRepository() {
		return monitorRepository;
	}

	public IAssistantDAO getAssistantRepository() {
		return assistantRepository;
	}

	public IInscriptionDAO getInscriptionRepository() {
		return inscriptionRepository;
	}
}
