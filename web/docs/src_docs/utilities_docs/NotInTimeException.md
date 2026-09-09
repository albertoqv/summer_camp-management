## Condición de la excepción notInTime 
### Esta excepción se disparará para comprobar que una EarlyInscription no pueda crearse debido a que tiene una fecha
### <= 15 dias antes del comienzo del campamento
### NO CONFUNDIR CON EL TEST DE LA LatelyInscription
``````
long daysDifference = Utils.daysBetween(inscriptionDate, camp.getStart());
		if (daysDifference <= 15) {
			throw new NotInTimeException();
		}
``````