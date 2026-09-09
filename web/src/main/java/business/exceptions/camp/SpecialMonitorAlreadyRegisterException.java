package business.exceptions.camp;

/**
 * Esta excepción se lanza cuando se intenta registrar un monitor especial que ya ha sido registrado previamente.
 * La clase {@code SpecialMonitorAlreadyRegisterException} extiende la clase {@code RuntimeException}.
 * Puede ocurrir en situaciones donde se intenta registrar un monitor especial en un sistema y ya existe un registro para ese monitor.
 */

public class SpecialMonitorAlreadyRegisterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
