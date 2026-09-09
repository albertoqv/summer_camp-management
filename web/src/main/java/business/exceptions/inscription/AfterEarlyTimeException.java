package business.exceptions.inscription;

/**
 * Esta excepción se lanza cuando una operación intenta utilizar una hora 
 * que se considera posterior a una restricción temporal temprana.
 * Se trata de una excepción en tiempo de ejecución, por lo que no es necesario capturarla explícitamente.
 */

public class AfterEarlyTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

}