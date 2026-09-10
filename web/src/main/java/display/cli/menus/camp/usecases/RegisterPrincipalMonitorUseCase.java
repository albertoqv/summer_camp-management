package display.cli.menus.camp.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.managers.CampsManager;
import display.cli.menus.Common;

public class RegisterPrincipalMonitorUseCase {
	public static void execute(Scanner sc, CampsManager campsManager, CampDTO selectedCamp) {
		List<ActivityDTO> listOfActivitiesOfTheSelectedCamp = campsManager.getActivitiesOfACamp(selectedCamp);
		System.out.println("Lista de actividades del campamento:");

		Common.showActivities(listOfActivitiesOfTheSelectedCamp, true);
		
		System.out.println("Seleccione una actividad");
		int optionSelected = sc.nextInt();
		
		if(optionSelected == listOfActivitiesOfTheSelectedCamp.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > listOfActivitiesOfTheSelectedCamp.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		ActivityDTO selectedActivity = listOfActivitiesOfTheSelectedCamp.get(optionSelected - 1);

		Common.clearConsole();
		System.out.println("Campamento seleccionado: ");
		System.out.println("Campamento # " + selectedCamp.getCampID() + " ");
		System.out.println("Actividad seleccionada: ");
		System.out.println("Actividad \"" + selectedActivity.getActivityName() + "\" ");
		
		List<MonitorDTO> listOfMonitorsOfTheSelectedActivity = campsManager.getMonitorsOfAnActivity(selectedActivity);
		System.out.println("Lista de monitores de la actividad:");

		Common.showMonitors(listOfMonitorsOfTheSelectedActivity, true);
		
		System.out.println("Seleccione un monitor");
		optionSelected = sc.nextInt();
		
		if(optionSelected == listOfMonitorsOfTheSelectedActivity.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > listOfMonitorsOfTheSelectedActivity.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		
		MonitorDTO selectedMonitor = listOfMonitorsOfTheSelectedActivity.get(optionSelected - 1);
		
		
		try {
			campsManager.setPrincipalMonitor(selectedCamp, selectedMonitor);												
		} catch (MonitorIsNotInActivityException e) {
			Common.clearConsole();
			System.out.println("El monitor principal no pertenece a ninguna actividad del campamento. ");
			return;
		}
		Common.clearConsole();
		System.out.println("Monitor con DNI " + selectedMonitor.getId() + " asignado como monitor principal para el campamento #" + selectedCamp.getCampID() + " correctamente.");
	}
}
