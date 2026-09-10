package display.cli.menus.camp.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.exceptions.camp.IsNotAnSpecialEducator;
import business.exceptions.camp.SpecialMonitorAlreadyRegisterException;
import business.managers.CampsManager;
import display.cli.menus.Common;

public class RegisterSpecialMonitorUseCase {
	public static void execute(Scanner sc, CampsManager campsManager, CampDTO selectedCamp) {
		List<MonitorDTO> monitors = campsManager.listOfMonitors();
		System.out.println("Lista de monitores del sistema");
		
		Common.showMonitors(monitors, true);
		
		System.out.println("Seleccione un monitor");
		int optionSelected = sc.nextInt();
		
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
			campsManager.setSpecialMonitor(selectedCamp, monitorSelected);
		} catch (IsNotAnSpecialEducator e) {
			Common.clearConsole();
			System.out.println("No se puede agregar como monitor especial a un monitor que no es un educador especial. ");
			return;
		} catch (SpecialMonitorAlreadyRegisterException e) {
			Common.clearConsole();
			System.out.println("No se puede agregar como monitor especial a un monitor que ya es monitor de una actividad del campamento. ");
			return;
		}
		
		Common.clearConsole();
		System.out.println("Monitor con DNI " + monitorSelected.getId() + " asignado como monitor especial para el campamento #" + selectedCamp.getCampID() + " correctamente.");
		
	}
}
