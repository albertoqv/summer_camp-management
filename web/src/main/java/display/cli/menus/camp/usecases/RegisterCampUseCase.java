package display.cli.menus.camp.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.activity.NotEnoughMonitorsException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.NotTheSameLevelException;
import business.managers.CampsManager;
import display.cli.menus.Common;

public class RegisterCampUseCase {
	public static void execute(Scanner sc, CampsManager campsManager) {
		int idCamp = campsManager.listOfCamps().size() + 1;
		
		System.out.println("Creando campamento con id: #" + idCamp);
		CampDTO camp = Common.getDataForCamp(idCamp, sc);
		
		List<ActivityDTO> listOfActivities = campsManager.listOfActivities();
		System.out.println("Lista de actividades en el sistema:");

		Common.showActivities(listOfActivities, true);
		
		System.out.println("Debe seleccionar alguna actividad para crear el campamento");
		int optionSelected = sc.nextInt();
		
		if(optionSelected == listOfActivities.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > listOfActivities.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		
		ActivityDTO selectedActivity = listOfActivities.get(optionSelected - 1);
		
		
		List<MonitorDTO> monitorsOfTheActivity = campsManager.getMonitorsOfAnActivity(selectedActivity);
		System.out.println("Lista de monitores de la actividad:");
		Common.showMonitors(monitorsOfTheActivity, true);
		
		System.out.println("Debe seleccionar el monitor principal del campamento");
		optionSelected = sc.nextInt();
		
		if(optionSelected == monitorsOfTheActivity.size() + 1) {
			Common.clearConsole();
			return;
		} else if (optionSelected > monitorsOfTheActivity.size() + 1) {
			Common.clearConsole();
			System.out.println("Opción invalida");
			return;
		}
		
		MonitorDTO selectedMonitor = monitorsOfTheActivity.get(optionSelected - 1);
		
		try {
			campsManager.registerCamp(camp);
			campsManager.registerActivityInACamp(camp, selectedActivity);									
			campsManager.setPrincipalMonitor(camp, selectedMonitor);
		} catch (NotTheSameLevelException e) {
			campsManager.deleteCamp(camp);
			Common.clearConsole();
			System.out.println("La actividad y el campamento deben de ser del mismo nivel educativo. ");
			return;								
		} catch (MonitorIsNotInActivityException e) {
			campsManager.deleteCamp(camp);
			Common.clearConsole();
			System.out.println("El monitor principal no pertenece a ninguna actividad del campamento. ");
			return;								
		} catch (CampAlreadyRegisteredException e) {
			Common.clearConsole();
			System.out.println("El campamento ya existe ");
			return;	
		} catch (NotEnoughMonitorsException e) {
			campsManager.deleteCamp(camp);
			Common.clearConsole();
			System.out.println("La actividad no tiene suficientes monitores para llevarse a cabo. ");
			return;		
		}

		Common.clearConsole();
		System.out.println("Campamento #" + idCamp + " creado exitosamente. ");
	}
}
