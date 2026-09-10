package domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import business.dtos.InscriptionDTO;
import business.utilities.Utils;

class InscriptionTest {

	@Test
	void testDefaultConstructor() {
		int assistantId = -1;
		int campId = -1;
		Date inscriptionDate = null;
		float price = -1;
		

		InscriptionDTO inscription = new InscriptionDTO();
		
		assertEquals(assistantId, inscription.getAssistantId());
        assertEquals(campId, inscription.getCampId());
        assertEquals(inscriptionDate, inscription.getInscriptionDate());
        assertEquals(price, inscription.getPrice());
	}

	@Test
	void testSetters() {
		InscriptionDTO inscription = new InscriptionDTO();
		int assistantId = 1;
		int campId = 4;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
				
		inscription.setAssistantId(assistantId);
		inscription.setCampId(campId);
		inscription.setInscriptionDate(inscriptionDate);
		inscription.setPrice(price);
		
		assertEquals(assistantId, inscription.getAssistantId());
		assertEquals(campId, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(price, inscription.getPrice());
		
	}

	@Test
	void testConstructorAndGetters() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		
		
		InscriptionDTO inscription = new InscriptionDTO(assistantId, campId, inscriptionDate, price, true, false);
		
		assertEquals(assistantId, inscription.getAssistantId());
		assertEquals(campId, inscription.getCampId());
		assertEquals(inscriptionDate, inscription.getInscriptionDate());
		assertEquals(price, inscription.getPrice());
		assertEquals(true, inscription.canBeCanceled());
	}


	@Test
    public void testToString() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		
		InscriptionDTO inscription = new InscriptionDTO(assistantId,campId,inscriptionDate,price, true, false);

        String expectedToString = "{AssistantId: 1, CampId: 1, InscriptionDate: '12/01/2024', Price: 100.0, canBeCancelled: true}";
        assertEquals(expectedToString, inscription.toString());
    }
	
	
}
