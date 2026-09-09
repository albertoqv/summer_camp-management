package business.exceptions.inscription;

/**
 * La excepción AfterStartTimeException se lanza cuando se intenta realizar una operación
 * que debe ocurrir después de un tiempo de inicio específico, pero la operación se intenta
 * antes de ese tiempo.
 * 
 * Esta excepción generalmente se utiliza para indicar que cierta acción no es válida antes de
 * un momento determinado en una aplicación.
 */

public class AfterStartTimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
