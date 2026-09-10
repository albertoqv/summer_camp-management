package display.cli.menus.assistant.usecases;

import java.util.Scanner;

import business.dtos.AssistantDTO;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.managers.AssistantsManager;
import display.cli.menus.Common;

public class RegisterAssistantUseCase {
	public static void execute(Scanner sc, AssistantsManager assistantsManager) {
		System.out.println("Introduzca el DNI (sin letra) del asistente");
		int assistantId = sc.nextInt();
		AssistantDTO assistant = Common.getDataForAssistant(assistantId, sc);
		try {									
			assistantsManager.registerAssistant(assistant);
		} catch (AssistantAlreadyEnrolledException e) {
			Common.clearConsole();
			System.out.println("Este DNI ya ha sido registrado en nuestro sistema");
			return;
		}
		
		Common.clearConsole();
		System.out.println("Asistente dado de alta correctamente");
	}
}
