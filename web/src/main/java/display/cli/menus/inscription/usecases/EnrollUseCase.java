package display.cli.menus.inscription.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.exceptions.inscription.AfterLateTimeException;
import business.exceptions.inscription.AfterStartTimeException;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.inscription.MaxAssistantExcededException;
import business.exceptions.inscription.NeedToAddAnSpecialMonitorException;
import business.exceptions.inscription.WrongEducativeLevelException;
import business.managers.AssistantsManager;
import business.managers.InscriptionManager;
import business.utilities.Utils;
import display.cli.menus.Common;

public class EnrollUseCase {
	public static void execute(Scanner sc, InscriptionManager inscriptionManager, AssistantsManager assistantsManager) {
		List<AssistantDTO> listOfAssistants = assistantsManager.getListOfRegisteredAssistant();
		System.out.println("Lista de asistentes en el sistema:");

		Common.showAssistants(listOfAssistants, true);
		
		System.out.println("Seleccione un asistente");
		int optionSelected = sc.nextInt();
		
		if(optionSelected == listOfAssistants.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > listOfAssistants.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		
		AssistantDTO selectedAssistant = listOfAssistants.get(optionSelected - 1);
		
		Common.clearConsole();
		System.out.println("Asistente seleccionado: ");
		System.out.println("DNI: " + selectedAssistant.getId() + " Nombre: " + selectedAssistant.getFirstName() + " " + selectedAssistant.getLastName() + " ");
		
		List<CampDTO> listAvailableCamps = inscriptionManager.avaliableCamps(Utils.getCurrentDate());
		System.out.println("Lista de campamentos disponibles:");

		Common.showCamps(listAvailableCamps, true);
		
		System.out.println("Seleccione un campamento");
		optionSelected = sc.nextInt();
		
		if(optionSelected == listAvailableCamps.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > listAvailableCamps.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		
		CampDTO selectedCamp = listAvailableCamps.get(optionSelected - 1);
		
		Common.clearConsole();
		System.out.println("Asistente seleccionado: ");
		System.out.println("DNI: " + selectedAssistant.getId() + " Nombre: " + selectedAssistant.getFirstName() + " " + selectedAssistant.getLastName() + "");
		System.out.println("Campamento seleccionado: ");
		System.out.println("Campamento # " + selectedCamp.getCampID() + " ");
		
		System.out.println("¿En que modalidad es la inscripción? (1: Parcial, 2: Completa)");
		int option = sc.nextInt();
		boolean isPartial = option == 1
				? true
				: false;
		
		InscriptionDTO inscription;
		try {
			inscription = inscriptionManager.enroll(
					selectedAssistant.getId(),
					selectedCamp.getCampID(), 
					Utils.getCurrentDate(), 
					isPartial, 
					selectedAssistant.isRequireSpecialAttention()
			);
		} catch (WrongEducativeLevelException e) {
			Common.clearConsole();
			System.out.println("El nivel educativo de este campamento no es adecuado para el asistente. ");
			return;
		} catch (NeedToAddAnSpecialMonitorException e) {
			Common.clearConsole();
			System.out.println("Es necesario añadir un monitor de atención especial antes de inscribir al asistente. ");
			return;
		} catch (AssistantAlreadyEnrolledException e) {
			Common.clearConsole();
			System.out.println("Este asistente ya está inscrito en el campamento. ");
			return;
		} catch (MaxAssistantExcededException e) {
			Common.clearConsole();
			System.out.println("No hay plazas libres para inscribir al asistente a todas las actividades de la modalidad seleccionada del campamento. ");
			return;
		} catch (AfterStartTimeException e) {
			Common.clearConsole();
			System.out.println("El campamento ya ha comenzado. ");
			return;
		} catch (AfterLateTimeException e) {
			Common.clearConsole();
			System.out.println("No es posible inscribirse a un campamento si no se hace con al menos 48h de antelación. ");
			return;
		}
		
		Common.clearConsole();
		System.out.println("Inscripción realizada correctamente. Precio: " + inscription.getPrice() + " euros. ");
	}
}
