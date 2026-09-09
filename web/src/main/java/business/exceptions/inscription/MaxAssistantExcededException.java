package business.exceptions.inscription;


/**
 * Esta clase representa una excepción que se lanza cuando se excede el número máximo de asistentes permitidos.
 * Puede ocurrir, por ejemplo, en un sistema que tiene un límite de asistentes y se intenta agregar más de los permitidos.
 */

public class MaxAssistantExcededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
