package business.factories;

import java.util.Date;

import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.CompleteInscriptionDTO;
import business.dtos.PartialInscriptionDTO;
import business.exceptions.assistant.AssistantNotFoundException;
import business.exceptions.camp.CampNotFoundException;
import business.exceptions.dao.NotFoundException;
import business.exceptions.inscription.AfterEarlyTimeException;
import business.exceptions.inscription.AfterStartTimeException;
import business.interfaces.IDAO;
import business.utilities.Utils;

/**
 * La clase EarlyRegisterInscriptionFactory es una fábrica que crea inscripciones 
 * anticipadas (early registration) para campamentos. Estas inscripciones se 
 * caracterizan por reglas especiales relacionadas con el tiempo y el precio.
 */
public class EarlyRegisterInscriptionFactory extends AbstractInscriptionFactory {
    private IDAO<CampDTO, Integer> campRepository;
    private IDAO<AssistantDTO, Integer> assistantRepository;

    /**
     * Constructor para EarlyRegisterInscriptionFactory.
     *
     * @param campRepository       Repositorio de campamentos.
     * @param assistantRepository  Repositorio de asistentes.
     */
    public EarlyRegisterInscriptionFactory(IDAO<CampDTO, Integer> campRepository, IDAO<AssistantDTO, Integer> assistantRepository) {
        this.campRepository = campRepository;
        this.assistantRepository = assistantRepository;
    }

    /**
     * Crea una inscripción parcial para un asistente en un campamento, siguiendo las reglas de early registration.
     *
     * @param assistantId      ID del asistente.
     * @param campId           ID del campamento.
     * @param inscriptionDate  Fecha de inscripción.
     * @param price            Precio de la inscripción.
     * @return Una inscripción parcial creada de acuerdo con las reglas de early registration.
     * @throws AssistantNotFoundException    Si no se encuentra al asistente en el repositorio.
     * @throws CampNotFoundException         Si no se encuentra el campamento en el repositorio.
     * @throws AfterEarlyTimeException       Si la inscripción se realiza después del tiempo de early registration.
     * @throws AfterStartTimeException      Si la inscripción se realiza después del inicio del campamento.
     */
    @Override
    public PartialInscriptionDTO createPartial(int assistantId, int campId, Date inscriptionDate, float price) {
        try {
            this.assistantRepository.find(assistantId);
        } catch (NotFoundException e) {
            throw new AssistantNotFoundException();
        }

        try {
            CampDTO camp = this.campRepository.find(campId);

            long daysDifference = Utils.daysBetween(camp.getStart(), inscriptionDate);

            if ((daysDifference <= 15) && (daysDifference > 0)) {
                throw new AfterEarlyTimeException();
            } else if (daysDifference < 0) {
                throw new AfterStartTimeException();
            }

            return new PartialInscriptionDTO(assistantId, campId, inscriptionDate, price, true);

        } catch (NotFoundException e) {
            throw new CampNotFoundException();
        }
    }

    /**
     * Crea una inscripción completa para un asistente en un campamento, siguiendo las reglas de early registration.
     *
     * @param assistantId      ID del asistente.
     * @param campId           ID del campamento.
     * @param inscriptionDate  Fecha de inscripción.
     * @param price            Precio de la inscripción.
     * @return Una inscripción completa creada de acuerdo con las reglas de early registration.
     * @throws AssistantNotFoundException    Si no se encuentra al asistente en el repositorio.
     * @throws CampNotFoundException         Si no se encuentra el campamento en el repositorio.
     * @throws AfterEarlyTimeException       Si la inscripción se realiza después del tiempo de early registration.
     * @throws AfterStartTimeException      Si la inscripción se realiza después del inicio del campamento.
     */
    @Override
    public CompleteInscriptionDTO createComplete(int assistantId, int campId, Date inscriptionDate, float price) {
        try {
            this.assistantRepository.find(assistantId);
        } catch (NotFoundException e) {
            throw new AssistantNotFoundException();
        }

        try {
            CampDTO camp = this.campRepository.find(campId);

            long daysDifference = Utils.daysBetween(camp.getStart(), inscriptionDate);

            if ((daysDifference <= 15) && (daysDifference > 0)) {
                throw new AfterEarlyTimeException();
            } else if (daysDifference < 0) {
                throw new AfterStartTimeException();
            }

            return new CompleteInscriptionDTO(assistantId, campId, inscriptionDate, price, true);

        } catch (NotFoundException e) {
            throw new CampNotFoundException();
        }
    }
}
