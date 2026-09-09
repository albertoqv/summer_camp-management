package business.interfaces;

import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
/**
 * Esta interfaz representa el Objeto de Acceso a Datos (DAO) para la entidad Actividad.
 * Extiende la interfaz IDAO con ActivityDTO como la entidad y String como la clave.
 */
public interface IActivityDAO extends IDAO<ActivityDTO, String>{
	/**
     * Este método se utiliza para obtener una lista de actividades en un campamento específico.
     * @param camp El campamento para el cual obtener las actividades.
     * @return A list of activity DTOs.
     */
    
	public List<ActivityDTO> getActivitiesInACamp( CampDTO camp);
	
	/**
     * Este método se utiliza para guardar una actividad y relacionarla con un campamento específico.
     * @param activity La actividad a guardar.
     * @param camp El campamento con el cual se va a relacionar la actividad.
     */
	public void saveAndRelateWithACamp(ActivityDTO activity, CampDTO camp);
}
