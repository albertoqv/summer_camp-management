package business.interfaces;

import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.MonitorDTO;
import business.dtos.UserDTO;

/**
 * Esta interfaz representa el Objeto de Acceso a Datos (DAO) para la entidad Asistente.
 * Extiende la interfaz IDAO con AssistantDTO como la entidad y Integer como la clave.
 */
public interface IInscriptionDAO extends IDAO<InscriptionDTO, String>{
	public List<InscriptionDTO> getInscriptionOfACamp(CampDTO camp);
}