package business.interfaces;

/**
 * Esta interfaz representa un criterio que puede ser aplicado a un objeto.
 */
public interface ICriteria{

    /**
     * Este método se utiliza para aplicar el criterio a un objeto específico.
     * @param obj El objeto al que se aplicará el criterio.
     * @param <T> The type of the object to apply the criteria to.
     * @return T El objeto después de aplicarle el criterio.
     */
    <T> T applyCriteria(T obj);
}