package display.cli.menus.camp;

import java.util.Scanner;

import business.managers.CampsManager;
import display.cli.menus.Common;
import display.cli.menus.camp.usecases.RegisterActivityUseCase;
import display.cli.menus.camp.usecases.RegisterCampUseCase;
import display.cli.menus.camp.usecases.RegisterMonitorInActivityUseCase;
import display.cli.menus.camp.usecases.RegisterMonitorUseCase;

public class CampManagerMenu {
	public static void execute(Scanner sc, CampsManager campsManager) {
		int optionCampManager;
		
		do {
			Common.showCampsWithActivities(campsManager.listOfCamps(), campsManager, false);
			
			System.out.println("1. Dar de alta un nuevo campamento");
			System.out.println("2. Registrar nueva actividad");
			System.out.println("3. Registrar nuevo monitor");
			System.out.println("4. Asociar un monitor a una actividad");
			System.out.println("5. Gestionar un campamento");
			System.out.println("6. Volver");
			
			optionCampManager = sc.nextInt();
			Common.clearConsole();
			switch (optionCampManager) {
				case 1: {
					RegisterCampUseCase.execute(sc, campsManager);
					break;
				}
				case 2: {
					RegisterActivityUseCase.execute(sc, campsManager);
					break;
				}
				case 3: {
					RegisterMonitorUseCase.execute(sc, campsManager);
					break;
				}
				case 4: {
					RegisterMonitorInActivityUseCase.execute(sc, campsManager);
					break;
				}
				case 5: {
					ManageCampSubMenu.execute(sc, campsManager);
					break;
				}
				case 6:
					Common.clearConsole();
				break;

				default:
					Common.clearConsole();
					System.out.println("Opción no válida");
			}
		} while (optionCampManager != 6);
	}
}
