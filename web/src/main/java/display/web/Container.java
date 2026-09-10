package display.web;

import java.util.Properties;

import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.UserDTO;
import business.interfaces.IActivityDAO;
import business.interfaces.IAssistantDAO;
import business.interfaces.IDAO;
import business.interfaces.IInscriptionDAO;
import business.interfaces.IMonitorDAO;
import business.managers.AssistantsManager;
import business.managers.CampsManager;
import business.managers.InscriptionManager;
import business.managers.UsersManager;
import data.database.DBManager;
import data.database.daos.*;
import data.memory.daos.*;

public class Container {
	private static Container _instance;
	
	
	private IDAO<CampDTO, Integer> campRepository;
	private IActivityDAO activityRepository;
	private IMonitorDAO monitorRepository;
	private IAssistantDAO assistantRepository;
	private IInscriptionDAO inscriptionRepository;
	private IDAO<UserDTO, String> userRepository;
	
	private AssistantsManager assistantsManager;
	private CampsManager campsManager;
	private InscriptionManager inscriptionManager;
	private UsersManager userManager;
	
	
	public static Container getInstance() {
		if (_instance == null) {
			_instance = new Container();
		}
		return _instance;
	}
	
	public static void setProperties(Properties queriesProp, String dbUrl, String dbUsername, String dbPassword) {
		DBManager.setProperties(queriesProp, dbUrl, dbUsername, dbPassword);
	}
	
	private Container() {
		this.configureDatabasePersistence();
		this.setUpManagers();
	}
	
	@SuppressWarnings("unused")
	private void configureDatabasePersistence() {
		this.campRepository = new InDatabaseCampDAO();
		this.activityRepository = new InDatabaseActivityDAO();
		this.monitorRepository = new InDatabaseMonitorDAO();
		this.assistantRepository = new InDatabaseAssistantDAO();
		this.inscriptionRepository = new InDatabaseInscriptionDAO();
		this.userRepository = new InDatabaseUserDAO();
	}
	
	@SuppressWarnings("unused")
	private void configureMemoryPersistence() {
		this.campRepository = new InMemoryCampDAO();
		this.activityRepository = new InMemoryActivityDAO();
		this.monitorRepository = new InMemoryMontiorDAO();
		this.assistantRepository = new InMemoryAssistantDAO();
		this.inscriptionRepository = new InMemoryInscriptionDAO();
		this.userRepository = new InMemoryUserDAO();
	}
	
	private void setUpManagers() {		
		this.assistantsManager = new AssistantsManager(this.assistantRepository);
		this.campsManager = new CampsManager(this.campRepository, this.activityRepository, this.monitorRepository);
		this.inscriptionManager = new InscriptionManager(this.campRepository, this.activityRepository, this.assistantRepository, this.inscriptionRepository);
		this.userManager = new UsersManager(this.userRepository, this.assistantRepository);
	}

	public AssistantsManager getAssistantsManager() {
		return assistantsManager;
	}

	public CampsManager getCampsManager() {
		return campsManager;
	}

	public InscriptionManager getInscriptionManager() {
		return inscriptionManager;
	}

	public UsersManager getUserManager() {
		return userManager;
	}
}
