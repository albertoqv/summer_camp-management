package display.cli.menus.camp.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.CampDTO;
import business.managers.CampsManager;
import display.cli.exceptions.InvalidOptionException;
import display.cli.exceptions.ReturnException;
import display.cli.menus.Common;

public class SelectCampUseCase {
	public static CampDTO execute(Scanner sc, CampsManager campsManager) {
		List<CampDTO> listAvailableCamps = campsManager.listOfCamps();
		System.out.println("Lista de campamentos en el sistema:");

		Common.showCamps(listAvailableCamps, true);
		
		System.out.println("Seleccione un campamento");
		int optionSelectedCamp = sc.nextInt();
		
		if(optionSelectedCamp == listAvailableCamps.size() + 1) {
			throw new ReturnException();
		} else if (optionSelectedCamp > listAvailableCamps.size() + 1) {
			throw new InvalidOptionException();
		}
		
		return listAvailableCamps.get(optionSelectedCamp -1 );
	}
}
