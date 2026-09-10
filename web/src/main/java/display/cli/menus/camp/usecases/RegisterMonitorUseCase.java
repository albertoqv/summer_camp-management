package display.cli.menus.camp.usecases;

import java.util.Scanner;

import business.dtos.MonitorDTO;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.monitor.MonitorAlreadyExistException;
import business.managers.CampsManager;
import display.cli.menus.Common;

public class RegisterMonitorUseCase {
	public static void execute(Scanner sc, CampsManager campsManager) {
		System.out.println("Introduzca el DNI (sin letra) del monitor");
		int monitorId = sc.nextInt();
		MonitorDTO monitor = Common.getDataForMonitor(monitorId, sc);
		try {									
			campsManager.registerMonitor(monitor);
		} catch (MonitorAlreadyExistException e) {
			Common.clearConsole();
			System.out.println("Este DNI ya ha sido registrado en nuestro sistema");
			return;
		}
		
		Common.clearConsole();
		System.out.println("Monitor dado de alta correctamente");	
	}
}
