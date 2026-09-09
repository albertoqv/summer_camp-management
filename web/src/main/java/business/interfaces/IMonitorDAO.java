package business.interfaces;

import java.util.List;

import business.dtos.ActivityDTO;
import business.dtos.MonitorDTO;

/**
 * Esta interfaz representa el Objeto de Acceso a Datos (DAO) para la entidad Monitor.
 * Extiende la interfaz IDAO con MonitorDTO como la entidad y Integer como la clave.
 */
public interface IMonitorDAO extends IDAO<MonitorDTO, Integer>{

    /**
     * Este método se utiliza para obtener una lista de monitores en una actividad específica.
     * @param  activity La actividad de los monitores
      * @return Una lista de monitores DTOs.
     */
    public List<MonitorDTO> getMonitorsInAnActivity(ActivityDTO activity);
    /**
     * Este método se utiliza para guardar una lista de monitores en una actividad específica.
     * @param  activity La actividad de los monitores
     * @param  monitor Los monitores de la actividad
     */
    public void saveAndRelateWithAnActivity(MonitorDTO monitor, ActivityDTO activity);
}