package display.cli.menus.camp.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.ActivityDTO;
import business.dtos.MonitorDTO;
import business.exceptions.activity.MaxMonitorsAddedException;
import business.managers.CampsManager;
import display.cli.menus.Common;

public class RegisterMonitorInActivityUseCase {
	public static void execute(Scanner sc, CampsManager campsManager) {
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
		
		ActivityDTO selectedActivity = listOfActivities.get(optionSelected-1);
		
		List<MonitorDTO> monitors = campsManager.listOfMonitors();
		
		Common.showMonitors(monitors, true);
		
		System.out.println("Seleccione un monitor");
		optionSelected = sc.nextInt();
		
		if(optionSelected == monitors.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > monitors.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		
		MonitorDTO monitorSelected = monitors.get(optionSelected-1);
		
		try {
			campsManager.registerMonitorInActivity(selectedActivity, monitorSelected);
		} catch (MaxMonitorsAddedException e) {
			Common.clearConsole();
			System.out.println("No se pueden añadir más monitores a esta actividad.");
			return;
		}
		
		Common.clearConsole();
		System.out.println("Monitor con DNI " + monitorSelected.getId() + " creado y agregado a la actividad \"" + selectedActivity.getActivityName() + "\" correctamente.");
	}
}
