package display.cli.menus.assistant.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.AssistantDTO;
import business.managers.AssistantsManager;
import display.cli.menus.Common;

public class ModifyRegisteredAssistantUseCase {
	public static void execute(Scanner sc, AssistantsManager assistantsManager) {
		List<AssistantDTO> listOfRegisteredAssistants = assistantsManager.getListOfRegisteredAssistant();
		System.out.println("Lista de asistentes:");

		Common.showAssistants(listOfRegisteredAssistants, true);
		
		System.out.println("Seleccione un asistente");
		int optionSelected = sc.nextInt();
		
		if(optionSelected == listOfRegisteredAssistants.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > listOfRegisteredAssistants.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		assistantsManager.updateAssistant(
				Common.getDataForAssistant(
						listOfRegisteredAssistants.get(optionSelected-1).getId(),
						sc
				)
		);
		
		Common.clearConsole();
		System.out.println("Asistente actualizado correctamente");
	}
}
