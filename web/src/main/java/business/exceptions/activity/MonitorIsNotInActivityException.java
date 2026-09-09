package business.exceptions.activity;

/**
 * Esta excepción se lanza cuando un monitor no se encuentra en el estado de actividad esperado.
 * Normalmente indica que se está realizando una operación o acción en un monitor
 * que debería estar en una actividad específica pero no lo está.
 */

public class MonitorIsNotInActivityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}