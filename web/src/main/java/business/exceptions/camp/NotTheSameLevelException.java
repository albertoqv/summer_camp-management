package business.exceptions.camp;

/**
 * Esta excepción se lanza cuando dos objetos no están en el mismo nivel.
 * Por ejemplo, si tienes una jerarquía de objetos y esperas que estén en el mismo nivel,
 * pero no lo están, esta excepción puede ser lanzada para indicar la inconsistencia.
 */


public class NotTheSameLevelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
