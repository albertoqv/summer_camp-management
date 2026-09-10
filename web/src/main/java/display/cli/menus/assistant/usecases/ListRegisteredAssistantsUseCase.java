package display.cli.menus.assistant.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.AssistantDTO;
import business.managers.AssistantsManager;
import display.cli.menus.Common;

public class ListRegisteredAssistantsUseCase {
	public static void execute(Scanner sc, AssistantsManager assistantsManager) {
		List<AssistantDTO> listOfRegisteredAssistants = assistantsManager.getListOfRegisteredAssistant();
		System.out.println("Lista de asistentes:");

		Common.showAssistants(listOfRegisteredAssistants, false);

        System.out.println("Pulse enter para volver.");
        sc.nextLine();
        sc.nextLine();
		
        Common.clearConsole();
	}
}
