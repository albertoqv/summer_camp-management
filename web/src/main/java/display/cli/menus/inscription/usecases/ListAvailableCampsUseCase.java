package display.cli.menus.inscription.usecases;

import java.util.List;
import java.util.Scanner;

import business.dtos.CampDTO;
import business.managers.InscriptionManager;
import business.utilities.Utils;
import display.cli.menus.Common;

public class ListAvailableCampsUseCase {
	public static void execute(Scanner sc, InscriptionManager inscriptionManager) {
		List<CampDTO> listAvailableCamps = inscriptionManager.avaliableCamps(Utils.getCurrentDate());
		System.out.println("Lista de campamentos disponibles:");

		Common.showCamps(listAvailableCamps, false);

        System.out.println("Pulse enter para volver.");
        sc.nextLine();
        sc.nextLine();
        Common.clearConsole();
	}
}
