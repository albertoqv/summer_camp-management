package display.cli.menus.camp;
import java.util.Scanner;

import business.dtos.CampDTO;

import business.managers.CampsManager;
import display.cli.exceptions.InvalidOptionException;
import display.cli.exceptions.ReturnException;
import display.cli.menus.Common;
import display.cli.menus.camp.usecases.RegisterActivityInCampUseCase;
import display.cli.menus.camp.usecases.RegisterPrincipalMonitorUseCase;
import display.cli.menus.camp.usecases.RegisterSpecialMonitorUseCase;
import display.cli.menus.camp.usecases.SelectCampUseCase;

public class ManageCampSubMenu {
	public static void execute(Scanner sc, CampsManager campsManager) {
		CampDTO selectedCamp = null;
		
		try {
			selectedCamp = SelectCampUseCase.execute(sc, campsManager);
		} catch (ReturnException e) {
			Common.clearConsole();
			return;
		} catch (InvalidOptionException e) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		
		Common.clearConsole();								
		
		int optionSelectedCampManager;
		do {
			System.out.println("Campamento seleccionado: ");
			System.out.println("Campamento # " + selectedCamp.getCampID() + " ");
			
			System.out.println("1. Asociar actividad al campamento.");
			System.out.println("2. Registrar monitor principal.");
			System.out.println("3. Registrar monitor secundario.");
			System.out.println("4. Volver");
			
			optionSelectedCampManager = sc.nextInt();
			
			Common.clearConsole();
			System.out.println("Campamento seleccionado: ");
			System.out.println("Campamento #" + selectedCamp.getCampID() + " ");
			
			switch (optionSelectedCampManager) {
				case 1: {
					RegisterActivityInCampUseCase.execute(sc, campsManager, selectedCamp);
					break;
				}
				case 2: {
					RegisterPrincipalMonitorUseCase.execute(sc, campsManager, selectedCamp);
					break;
				}
				case 3: {
					RegisterSpecialMonitorUseCase.execute(sc, campsManager, selectedCamp);
					break;
				}
				case 4:
					Common.clearConsole();
				break;
				
				default:
					Common.clearConsole();
					System.out.println("Opción no válida");
			}
		} while (optionSelectedCampManager != 4);
	}
}
