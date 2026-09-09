package business.dtos;

import java.util.Date;

/**
 * La clase CompleteInscriptionDTO representa una inscripción completa para un asistente en un campamento.
 * Es una subclase de Inscription.
 */
public class CompleteInscriptionDTO extends InscriptionDTO{
	/**
     * Constructor para crear un objeto CompleteInscription con la información proporcionada.
     *
     * @param assistantId       Id del asistente que desea inscribirse
     * @param campId            Id del campamento al que se desea inscribir
     * @param inscriptionDate   La fecha en la que se realiza la inscripción
     * @param price             El precio de la inscripción
     * @param canBeCanceled     Puede o no ser cancelada la inscrioción
     */
	public CompleteInscriptionDTO(int assistantId, int campId, Date inscriptionDate, float price, boolean canBeCanceled) {
		super(assistantId, campId, inscriptionDate, price, canBeCanceled, false);
	}
}
