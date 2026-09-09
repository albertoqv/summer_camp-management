package business.dtos;

import java.util.Date;

/**
 * Clase que representa una inscripción parcial a un campamento.
 * Esta clase hereda de la clase Inscription y proporciona
 * información específica sobre inscripciones parciales.
 */
public class PartialInscriptionDTO extends InscriptionDTO {

    /**
     * Constructor de la clase PartialInscription.
     *
     * @param assistantId     ID del asistente que se inscribe.
     * @param campId          ID del campamento al que se inscribe.
     * @param inscriptionDate Fecha de la inscripción.
     * @param price           Precio de la inscripción parcial.
     * @param canBeCanceled   Indica si la inscripción parcial puede ser cancelada.
     */
    public PartialInscriptionDTO(int assistantId, int campId, Date inscriptionDate, float price, boolean canBeCanceled) {
        super(assistantId, campId, inscriptionDate, price, canBeCanceled, true);
    }
}
