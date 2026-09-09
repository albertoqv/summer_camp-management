package business.exceptions.activity;

/**
 * Esta clase representa una excepción que se lanza cuando se intenta agregar
 * más monitores de los permitidos en un contexto específico. Esta excepción
 * generalmente se utiliza para indicar que se ha alcanzado el límite máximo
 * de monitores permitidos.
 */

public class MaxMonitorsAddedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
