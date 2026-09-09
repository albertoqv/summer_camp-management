package business.exceptions.activity;

/**
 * Esta excepción se lanza cuando no se puede encontrar una actividad en el sistema.
 * Puede ocurrir cuando se busca una actividad que no existe en la base de datos.
 */

public class ActivityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
