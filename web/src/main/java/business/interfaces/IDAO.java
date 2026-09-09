package business.interfaces;

import java.util.List;
import java.util.Optional;
/**
 * La interfaz IDAO define operaciones básicas de persistencia para un tipo de entidad específico.
 *
 * @param <T> El tipo de entidad que se va a manipular en el repositorio.
 * @param <I> El tipo de identificador único utilizado para buscar entidades en el repositorio.
 */
public interface IDAO<T, I> {
    /**
     * Busca una entidad en el repositorio utilizando el identificador único proporcionado.
     *
     * @param identifier El identificador único de la entidad que se desea buscar.
     * @return La entidad encontrada o null si no se encuentra ninguna entidad con el identificador dado.
     */
	public T find(I identifier);

	/**
     * Guarda una nueva entidad o actualiza una entidad existente en el repositorio.
     *
     * @param obj La entidad que se desea guardar o actualizar en el repositorio.
     */
	public void save(T obj);
	
	public void create(T obj);

	/**
     * Elimina una entidad del repositorio.
     *
     * @param obj La entidad que se desea eliminar del repositorio.
     */
	public void delete(T obj);

	/**
     * Obtiene una lista de todas las entidades del repositorio.
     *
     * @return Una lista de todas las entidades del repositorio.
     */
    default List<T> getAll() {
        return getAll(Optional.empty());
    }

    /**
     * Obtiene una lista de todas las entidades del repositorio que cumplen con los criterios especificados.
     *
     * @param criteria Los criterios que se utilizarán para filtrar los resultados.
     * @return Una lista de todas las entidades del repositorio que cumplen con los criterios especificados.
     */
    public List<T> getAll(Optional<ICriteria> criteria);
}
