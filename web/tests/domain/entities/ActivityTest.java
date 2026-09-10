package domain.entities;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import business.dtos.ActivityDTO;
import business.enums.EducativeLevel;
import business.enums.TimeSlot;

class ActivityTest {
	
	@Test
	void testConstructorAndGetters() {
		String activityName = "Activity";
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		TimeSlot timeSlot = TimeSlot.AFTERNOON;
		int maxAssistants = 15;
		int neededMonitors = 2;
		
		ActivityDTO activity = new ActivityDTO(
				activityName,
				educativeLevel,
				timeSlot,
				maxAssistants,
				neededMonitors
		);
		
		assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(timeSlot, activity.getTimeSlot());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
	}
	
	@Test
	void testDefaultConstructor() {
		String activityName = null;
		EducativeLevel educativeLevel = null;
		TimeSlot timeSlot = null;
		int maxAssistants = -1;
		int neededMonitors = -1;
		
		ActivityDTO activity = new ActivityDTO();
		
		assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(timeSlot, activity.getTimeSlot());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
	}
	
	@Test
	void testSetters() {
		ActivityDTO activity = new ActivityDTO();
		String activityName = "Activity";
		EducativeLevel educativeLevel = EducativeLevel.ELEMENTARY;
		TimeSlot timeSlot = TimeSlot.AFTERNOON;
		int maxAssistants = 15;
		int neededMonitors = 2;
		
		activity.setActivityName(activityName);
		activity.setEducativeLevel(educativeLevel);
		activity.setTimeSlot(timeSlot);
		activity.setMaxAssistants(maxAssistants);
		activity.setNeededMonitors(neededMonitors);
		
		assertEquals(activityName, activity.getActivityName());
        assertEquals(educativeLevel, activity.getEducativeLevel());
        assertEquals(timeSlot, activity.getTimeSlot());
        assertEquals(maxAssistants, activity.getMaxAssistants());
        assertEquals(neededMonitors, activity.getNeededMonitors());
        
	}
	@Test
	public void testToString() {
		
        String activityName = "Activity";
        EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
        TimeSlot timeSlot = TimeSlot.AFTERNOON;
        int maxAssistants = 10;
        int neededMonitors = 3;

        ActivityDTO activity = new ActivityDTO(
                activityName,
                educativeLevel,
                timeSlot,
                maxAssistants,
                neededMonitors
        );

        
        
        String expectedToString = "{activityName: 'Activity', "
        		+ "educativeLevel: PRESCHOOL, "
        		+ "timeSlot: AFTERNOON, "
        		+ "maxAssistants: 10, "
        		+ "neededMonitors: 3"
        		+ "}";
        assertEquals(expectedToString, activity.toString());
    }

	

}


