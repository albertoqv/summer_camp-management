## Test que comprueba que una EarlyInscription no se pueda crear con una fecha no permitida para una EarlyInscription
## Dentro del cuerpo del test creamos un IRepository de tipo <Camp> llamado campRepository y lo guardamos en memoria.
## Con el metodo save almacenamos los datos de un nuevo campamento
## Creamos una factoria de tipo EarlyRegisterInscriptionFactory donde guardamos el repositorio campRepository creado anteriormente.
```
@Test
	void earlyRegisterInscriptionFactory_whenTheDateIsNotInTime_throwNotInTimeException() {
		int assistantId = 1;
		int campId = 1;
		Date inscriptionDate = Utils.parseDate("12/01/2024");
		float price = 100;
		InscriptionType inscriptionType = InscriptionType.COMPLETE;
		
		IRepository<Camp> campRepository = new InMemoryCampRepository();
		campRepository.save(new Camp(
					campId, 
					Utils.parseDate("15/01/2024"),
					Utils.parseDate("20/01/2024"),
					EducativeLevel.ELEMENTARY,
					10
					));
		
		EarlyRegisterInscriptionFactory factory = new EarlyRegisterInscriptionFactory(campRepository);
		
		assertThrows(NotInTimeException.class, 
				() -> factory.create(assistantId, campId, inscriptionDate, price, inscriptionType)
		);
	}
```