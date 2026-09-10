package display.cli.menus.camp.usecases;

import java.util.Scanner;

import business.dtos.ActivityDTO;
import business.exceptions.activity.ActivityAlreadyExistException;
import business.managers.CampsManager;
import display.cli.menus.Common;

public class RegisterActivityUseCase {
	public static void execute(Scanner sc, CampsManager campsManager) {
		System.out.println("Introduzca el nombre de la actividad ");
		
		String activityName = sc.next();
		
		
		ActivityDTO activity = Common.getDataForActivity(activityName, sc);
		try {
			campsManager.registerActivity(activity);
		} catch (ActivityAlreadyExistException e) {
			Common.clearConsole();
			System.out.println("Ya existe una actividad con ese nombre");
			return;									
		}
		
		Common.clearConsole();
		System.out.println("Actividad \"" + activityName+ "\" registrada correctamente ");
	}
}
