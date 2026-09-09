package business.exceptions.camp;

/**
 * Ws una excepción que se lanza cuando no se puede encontrar un campo (campamento) en el sistema.
 * Esta excepción extiende la clase {@code RuntimeException}, lo que significa que es una excepción no
 * comprobada y no requiere una declaración explícita o manejo en el código.
 */

public class CampNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
