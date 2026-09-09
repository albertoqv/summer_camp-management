## Uso de assertThrows
### Se cumple el assertThrow si se lanza la excepción indicada (primer parámetro) que ocurre con la acción () -> .....
```
@Test
	public void testAddMonitor_throwAnException_WhenNumberOfNeededMonitorsIsExceded(){
		Monitor monitor = new Monitor(
				1,
				"Alberto",
				"Quesada",
				true
		);

		Monitor monitor2 = new Monitor(
				2,
				"Francisco",
				"Ruíz",
				false
		);

		
		Monitor monitor3 = new Monitor(
				3,
				"Jose",
				"Trujillo",
				true
		);
		
		String activityName = "Activity";
        EducativeLevel educativeLevel = EducativeLevel.PRESCHOOL;
        TimeSlot timeSlot = TimeSlot.AFTERNOON;
        int maxAssistants = 10;
        int neededMonitors = 2;

        Activity activity = new Activity(
                activityName,
                educativeLevel,
                timeSlot,
                maxAssistants,
                neededMonitors
        );

		activity.addMonitor(monitor);
		activity.addMonitor(monitor2);


		assertThrows(MaxMonitorsAddedException.class, () -> activity.addMonitor(monitor3));
 		
	}
```