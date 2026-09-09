package display.cli.menus;

import java.util.Scanner;

import business.managers.AssistantsManager;
import business.managers.CampsManager;
import business.managers.InscriptionManager;
import display.cli.menus.assistant.AssistantManagerMenu;
import display.cli.menus.camp.CampManagerMenu;
import display.cli.menus.inscription.InscriptionManagerMenu;

public class MainMenu {
	public static void execute(Scanner sc, AssistantsManager assistantsManager, CampsManager campsManager, InscriptionManager inscriptionManager) {
		int option;
		Common.clearConsole();
		do {
            System.out.println("1. Gestor de asistentes");
            System.out.println("2. Gestor de campamentos");
            System.out.println("3. Gestor de inscripciones");
            System.out.println("4. Salir del programa");
            
            option = sc.nextInt();

            switch (option) {
                case 1:
					AssistantManagerMenu.execute(sc, assistantsManager);
                    break;
                case 2:
					CampManagerMenu.execute(sc, campsManager);
					break;

				case 3:
					InscriptionManagerMenu.execute(sc, inscriptionManager, assistantsManager);
                    break;
                 case 4:
                	Common.clearConsole();
                	System.out.println("Saliendo del programa. . .");
                    System.exit(0);
                    break;
                default:
                	Common.clearConsole();
                    System.out.println("Opción no válida");
            }
        } while (option != 4);
	}
}
