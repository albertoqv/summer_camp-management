package domain.entities;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import business.dtos.MonitorDTO;

class MonitorTest {
	
	@Test
	void testConstructorAndGetters() {
		int id = 1;
		String firstName = "Alberto";
		String lastName = "Quesada";
		boolean specialEducator = true;
		
		MonitorDTO monitor = new MonitorDTO(
				id,
				firstName,
				lastName,
				specialEducator
		);
		
		assertEquals(id, monitor.getId());
        assertEquals(firstName, monitor.getFirstName());
        assertEquals(lastName, monitor.getLastName());
        assertEquals(specialEducator, monitor.isSpecialEducator());
	}
	
	@Test
	void testDefaultConstructor() {
		int id = 0;
		String firstName = "";
		String lastName = "";
		boolean specialEducator = false;
		
		MonitorDTO monitor = new MonitorDTO();
		
		assertEquals(id, monitor.getId());
        assertEquals(firstName, monitor.getFirstName());
        assertEquals(lastName, monitor.getLastName());
        assertEquals(specialEducator, monitor.isSpecialEducator());
	}
	
	
	@Test
	void testSetters() {
		MonitorDTO monitor = new MonitorDTO();
		int id = 1;
		String firstName = "Alberto";
		String lastName = "Quesada";
		boolean specialEducator = true;
		
		monitor.setId(id);
		monitor.setFirstName(firstName);
		monitor.setLastName(lastName);
		monitor.setSpecialEducator(specialEducator);
		
		assertEquals(id, monitor.getId());
        assertEquals(firstName, monitor.getFirstName());
        assertEquals(lastName, monitor.getLastName());
        assertEquals(specialEducator, monitor.isSpecialEducator());
	}
	
	@Test
    public void testToString() {
		int id = 1;
		String firstName = "Alberto";
		String lastName = "Quesada";
		boolean specialEducator = true;
		
		MonitorDTO monitor = new MonitorDTO(
				id,
				firstName,
				lastName,
				specialEducator
		);

        String expectedToString = "{id: 1, firstName: 'Alberto', lastName: 'Quesada', isSpecialEducator: true}";
        assertEquals(expectedToString, monitor.toString());
    }
	

}
