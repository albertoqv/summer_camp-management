package display.cli;


import java.util.Scanner;

import business.managers.AssistantsManager;
import business.managers.CampsManager;
import business.managers.InscriptionManager;
import display.cli.menus.MainMenu;


public class Main {
	private static AssistantsManager assistantsManager;
	private static CampsManager campsManager;
	private static InscriptionManager inscriptionManager;
	private static Scanner sc;
	
	public static void main(String[] args) {
		setUpManagers();
		
        setUpScanner();
        
        MainMenu.execute(sc, assistantsManager, campsManager, inscriptionManager);
	}
	
	private static void setUpManagers() {
		PersistenceConfiguration persistenceConfig = PersistenceConfiguration.setUp();
		
		assistantsManager = new AssistantsManager(persistenceConfig.getAssistantRepository());
		campsManager = new CampsManager(persistenceConfig.getCampRepository(), persistenceConfig.getActivityRepository(), persistenceConfig.getMonitorRepository());
		inscriptionManager = new InscriptionManager(persistenceConfig.getCampRepository(), persistenceConfig.getActivityRepository(),
				persistenceConfig.getAssistantRepository(), persistenceConfig.getInscriptionRepository());
	}
	
	private static void setUpScanner() {
		sc = new Scanner(System.in);
        sc.useDelimiter("\n");
	}
}
