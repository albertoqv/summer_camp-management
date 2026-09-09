## Repositorio en el cual guardamos los datos en un array en memoria
### Utilizamos una interfaz para mas adelante implementar otro tipo de almacenamiento, ya sea una BD. Este repositorio aloja varias funciones (herramientas) que vamos a utlizar en las clases para hacer difrentes operaciones
```
public class InMemoryAssistantRepository implements IRepository<Assistant>{

	private Map<Integer, Assistant> mapOfAssistants;
	
	public InMemoryAssistantRepository() {
		this.mapOfAssistants = new HashMap<Integer, Assistant>();
	}
	
	@Override
	public Assistant find(int identifier) {
		if (this.mapOfAssistants.get(identifier) == null) {
			throw new NotFoundException();
		}
		return this.mapOfAssistants.get(identifier);
	}

	@Override
	public void save(Assistant obj) {
		this.mapOfAssistants.put(obj.getId(), obj);
	}

	@Override
	public List<Assistant> getAll() {
		return new ArrayList<Assistant>(this.mapOfAssistants.values());
	}

}
```