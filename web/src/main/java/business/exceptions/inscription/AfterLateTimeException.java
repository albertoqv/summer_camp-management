package business.exceptions.inscription;

/**
 * La excepción AfterLateTimeException es lanzada cuando se intenta realizar
 * una operación que no está permitida después de cierta hora o tiempo.
 * Esta excepción generalmente se utiliza para controlar restricciones de tiempo.
 */

public class AfterLateTimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
