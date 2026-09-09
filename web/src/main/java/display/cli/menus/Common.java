package display.cli.menus;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.MonitorDTO;
import business.enums.EducativeLevel;
import business.enums.TimeSlot;
import business.managers.CampsManager;
import business.utilities.Utils;

public class Common {
	public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
	            System.out.print("\033[H\033[2J");
	            System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	public static void showAssistants(List<AssistantDTO> listOfAssistants, boolean returnOption) {
        for (int i = 0; i < listOfAssistants.size(); i++) {
        	AssistantDTO iterableAssistant = listOfAssistants.get(i);
        	int age = (int) Utils.yearsBetween(Utils.getCurrentDate(), iterableAssistant.getBirthDate());
            System.out.println((i + 1) + ". DNI:" + iterableAssistant.getId() 
            							+ ", Nombre:" + iterableAssistant.getFirstName() + " " + iterableAssistant.getLastName()
            							+ ", Edad:" + age + " " + translateEducativeLevelReverse(age)
            							+ ", Atención Especial: " + (iterableAssistant.isRequireSpecialAttention() ? "Si" : "No"));
        }
        if(returnOption) {
        	System.out.println((listOfAssistants.size() + 1) + ". Volver");
        } 
	}
	
	public static void showCamps(List<CampDTO> listOfCamps, boolean returnOption) {
        for (int i = 0; i < listOfCamps.size(); i++) {
        	CampDTO iterableCamp = listOfCamps.get(i);
            System.out.println((i + 1) + ". Campamento #" + iterableCamp.getCampID() 
        						+ ": Inicio " + Utils.getStringDate(iterableCamp.getStart()) 
        						+ ", Nivel educativo: " + translateEducativeLevel(iterableCamp.getEducativeLevel())
        						+ ".");
        }
        if(returnOption) {
        	System.out.println((listOfCamps.size() + 1) + ". Volver");
        }  
	}
	
	public static void showCampsWithActivities(List<CampDTO> listOfCamps, CampsManager campsManager, boolean returnOption) {
        for (int i = 0; i < listOfCamps.size(); i++) {
        	CampDTO iterableCamp = listOfCamps.get(i);
            System.out.println((i + 1) + ". Campamento #" + iterableCamp.getCampID() 
        						+ ": Inicio " + Utils.getStringDate(iterableCamp.getStart()) 
        						+ ", Nivel educativo: " + translateEducativeLevel(iterableCamp.getEducativeLevel())
        						+ ".");
            List<ActivityDTO> activityDTOs = campsManager.getActivitiesOfACamp(iterableCamp);
            
            System.out.println("Actividades del campamento:");
            for (int j = 0; j < activityDTOs.size(); j++) {
            	ActivityDTO iterableActivity = activityDTOs.get(j);
                System.out.println("\t- " + printActivity(iterableActivity));
                
            }
        }
        
        if(returnOption) {
        	System.out.println((listOfCamps.size() + 1) + ". Volver");
        }  
	}
	
	public static AssistantDTO getDataForAssistant(int id, Scanner sc) {
		System.out.println("Introduzca el nombre del asisitente");
		String firstName = sc.next();
		
		System.out.println("Introduzca el apellido del asisitente");
		String lastName = sc.next();
		
		
		System.out.println("Introduzca la fecha de nacimiento del asisitente en formato (dd/mm/yyyy)");
		Date birthDate = Utils.parseDate(sc.next());
		while (birthDate == null) {
			System.out.println("Por favor, introduzca la fecha con el formato dd/mm/yyyy ");
			birthDate = Utils.parseDate(sc.next());									
		}
		
		System.out.println("Introduzca si el asistente necesita atención especial (s/n)");
		String response = sc.next();
		boolean requireSpecialAttention = response.toLowerCase().equals("s");
		
		return new AssistantDTO(
				id,
				firstName,
				lastName,
				birthDate,
				requireSpecialAttention
		);
	}
	
	public static void showMonitors(List<MonitorDTO> listOfMonitors, boolean returnOption) {
	    for (int i = 0; i < listOfMonitors.size(); i++) {
	    	MonitorDTO iterableMonitor = listOfMonitors.get(i);
	    	System.out.println((i + 1) + ". DNI:" + iterableMonitor.getId() 
				+ ", Nombre:" + iterableMonitor.getFirstName() + " " + iterableMonitor.getLastName()
				+ ", Educador Especial: " + (iterableMonitor.isSpecialEducator() ? "Si" : "No"));
	    }
	    if(returnOption) {
	    	System.out.println((listOfMonitors.size() + 1) + ". Volver");
	    } 
	}
	
	private static String translateEducativeLevel(EducativeLevel educativeLevel) {
		if(educativeLevel.name()== EducativeLevel.ELEMENTARY.name()) 
		{
			return "Infantil (4-6)";
		}
		else if(educativeLevel.name()== EducativeLevel.PRESCHOOL.name()) 
		{
			return "Juvenil (7-12)";
		}
		else if(educativeLevel.name()== EducativeLevel.TEENAGER.name()) 
		{
			return "Adolescente (13-17)";
		}
		else 
		{
			return null;
		}
	}
	private static String translateEducativeLevelReverse(int assistantAge) {
		if ((assistantAge >= 4) && (assistantAge <= 6)) {
			return "(Infantil)";
		} else if ((assistantAge >= 7) && (assistantAge <= 12)) {
			return "(Juvenil)";
		} else if ((assistantAge >= 13) && (assistantAge <= 17)) {
			return "(Adolescente)";
		} else {
			return "(No valido)";
		}
	}
	
	private static String translateTimeSlot(TimeSlot timeSlot) {
		if(timeSlot.name()== TimeSlot.AFTERNOON.name()) 
		{
			return "Tarde";
		}
		else if(timeSlot.name()== TimeSlot.MORNING.name()) 
		{
			return "Mañana";
		}
		else 
		{
			return null;
		}
	}
	
	public static void showActivities(List<ActivityDTO> listOfActivities, boolean returnOption) {
	    for (int i = 0; i < listOfActivities.size(); i++) {
	    	ActivityDTO iterableActivity = listOfActivities.get(i);
	        System.out.println((i + 1) + ". " + printActivity(iterableActivity));
	    }
	    if(returnOption) {
	    	System.out.println((listOfActivities.size() + 1) + ". Volver");
	    }  
	}
	
	public static String printActivity(ActivityDTO activityDTO) {
		return activityDTO.getActivityName()
				+ ", Nivel Educativo: "+ translateEducativeLevel(activityDTO.getEducativeLevel())
				+ ", Franja horaria: " + translateTimeSlot(activityDTO.getTimeSlot());
	}
	
	public static CampDTO getDataForCamp(int id, Scanner sc) {
		
		System.out.println("Introduzca la fecha inicio del campamento en formato (dd/mm/yyyy)");
		Date startDate = Utils.parseDate(sc.next());
		while (startDate == null) {
			System.out.println("Por favor, introduzca la fecha con el formato dd/mm/yyyy ");
			startDate = Utils.parseDate(sc.next());									
		}
		
		System.out.println("Introduzca la fecha fin del campamento en formato (dd/mm/yyyy)");
		Date endDate = Utils.parseDate(sc.next());
		while (endDate == null) {
			System.out.println("Por favor, introduzca la fecha con el formato dd/mm/yyyy ");
			endDate = Utils.parseDate(sc.next());									
		}
		
		
		System.out.println("Introduzca el nivel educativo (1: infantil, 2: juvenil, 3: adolescente)");
		int educativeLevelOption = sc.nextInt();
		EducativeLevel educativeLevel = educativeLevelOption == 1 
				? EducativeLevel.ELEMENTARY
				: educativeLevelOption == 2
					? EducativeLevel.PRESCHOOL
					: EducativeLevel.TEENAGER;
		
		
		System.out.println("Introduzca la capacidad del campamento");
		int capacity = sc.nextInt();
		
		return new CampDTO(
				id,
				startDate,
				endDate,
				educativeLevel,
				capacity
		);
	}
	public static ActivityDTO getDataForActivity(String activityName, Scanner sc) {
		
		System.out.println("Introduzca el nivel educativo (1: infantil, 2: juvenil, 3: adolescente)");
		int educativeLevelOption = sc.nextInt();
		EducativeLevel educativeLevel = educativeLevelOption == 1 
				? EducativeLevel.ELEMENTARY
				: educativeLevelOption == 2
					? EducativeLevel.PRESCHOOL
					: EducativeLevel.TEENAGER;
		
		System.out.println("Introduzca la franja horaria (1: mañana, 2: tarde)");
		int timeSlotOption = sc.nextInt();
		TimeSlot timeSlot = timeSlotOption == 1 
				? TimeSlot.MORNING
				: TimeSlot.AFTERNOON;
		
		
		System.out.println("Introduzca la capacidad de la actividad");
		int capacity = sc.nextInt();
		
		System.out.println("Introduzca el número de monitores necesarios");
		int nMonitorsNeeded = sc.nextInt();
		
		return new ActivityDTO(
				activityName,
				educativeLevel,
				timeSlot,
				capacity,
				nMonitorsNeeded
		);
	}
	
	public static MonitorDTO getDataForMonitor(int id, Scanner sc) {
		System.out.println("Introduzca el nombre del monitor");
		String firstName = sc.next();
		
		System.out.println("Introduzca el apellido del monitor");
		String lastName = sc.next();
		
		System.out.println("¿Es un monitor de atencion especial? (s/n)");
		String response = sc.next();
		boolean specialAttentionMonitor = response.toLowerCase().equals("s");
		
		return new MonitorDTO(
				id,
				firstName,
				lastName,
				specialAttentionMonitor
		);
	}
}
