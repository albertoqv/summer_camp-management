package display.cli.menus.assistant;

import java.util.Scanner;

import business.managers.AssistantsManager;
import display.cli.menus.Common;
import display.cli.menus.assistant.usecases.ListRegisteredAssistantsUseCase;
import display.cli.menus.assistant.usecases.ModifyRegisteredAssistantUseCase;
import display.cli.menus.assistant.usecases.RegisterAssistantUseCase;

public class AssistantManagerMenu {
	public static void execute(Scanner sc, AssistantsManager assistantsManager) {
		int optionAssistantsManager;
		
		do {
			System.out.println("1. Dar de alta un nuevo asistente");
			System.out.println("2. Modificar asistente");
			System.out.println("3. Listar asistentes");
			System.out.println("4. Volver");
			optionAssistantsManager = sc.nextInt();
			Common.clearConsole();
			
			switch (optionAssistantsManager) {
				case 1: { 
					RegisterAssistantUseCase.execute(sc, assistantsManager);
					break;
				}
				case 2: {
					ModifyRegisteredAssistantUseCase.execute(sc, assistantsManager);
					break;
				}
				case 3: {
					ListRegisteredAssistantsUseCase.execute(sc, assistantsManager);
					break;
				}
				case 4:
					Common.clearConsole();
				break;
				
				default:
					Common.clearConsole();
					System.out.println("Opción no válida");
			}
		} while (optionAssistantsManager != 4);
		
	}
}
