package business.exceptions.assistant;

/**
 * La excepción AssistantNotFoundException se lanza cuando un asistente no se encuentra en el sistema.
 * Puede ocurrir cuando se busca un asistente por identificador y no se encuentra en la base de datos.
 */

public class AssistantNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
