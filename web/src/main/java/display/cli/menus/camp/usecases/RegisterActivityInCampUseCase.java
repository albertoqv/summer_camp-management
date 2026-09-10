package display.cli.menus.camp.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.exceptions.camp.NotTheSameLevelException;
import business.managers.CampsManager;
import display.cli.menus.Common;

public class RegisterActivityInCampUseCase {
	public static void execute(Scanner sc, CampsManager campsManager, CampDTO selectedCamp) {
		List<ActivityDTO> listOfActivities = campsManager.listOfActivities();
		System.out.println("Lista de actividades en el sistema:");

		Common.showActivities(listOfActivities, true);
		
		System.out.println("Seleccione una actividad");
		int optionSelected = sc.nextInt();
		
		if(optionSelected == listOfActivities.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > listOfActivities.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		
		ActivityDTO selectedActivity = listOfActivities.get(optionSelected -1);

		try {
			campsManager.registerActivityInACamp(selectedCamp, selectedActivity);
		} catch (NotTheSameLevelException e) {
			Common.clearConsole();
			System.out.println("La actividad y el campamento deben de ser del mismo nivel educativo. ");
			return;
		}
		
		Common.clearConsole();
		System.out.println("Actividad \"" + selectedActivity.getActivityName() + "\" agregada al campamento #" + selectedCamp.getCampID() + "");
		
	}
}
