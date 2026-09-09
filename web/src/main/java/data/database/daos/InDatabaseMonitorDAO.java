package data.database.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.MonitorDTO;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.dao.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IMonitorDAO;
import data.database.DBManager;
import data.database.criteria.MonitorInActivityCriteria;
/**
 * La clase InDatabaseMonitorDAO es una implementación en base de datos de un DAO de la clase monitor.
 
 */
public class InDatabaseMonitorDAO implements IMonitorDAO{
	private DBManager dbConnection;
	/**
     * Constructor de la clase InDatabaseMonitorDAO.
     * Inicializa un nuevo mapa para almacenar monitores en memoria y recibe la ruta de la tabla como parametro
     */
    public InDatabaseMonitorDAO() {
        this.dbConnection = DBManager.getInstance();
    }
    /**
     * Busca un monitor por su identificador.
     *
     * @param identifier El identificador del monitor a buscar.
     * @return El monitor encontrado.
     * @throws NotFoundException Si el monitor no se encuentra en el DAO.
     */
    @Override
    public MonitorDTO find(Integer identifier) {
    	MonitorDTO monitor;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_MONITOR_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			Boolean isSpecialEducator = rs.getBoolean("specialEducator");
			
			monitor = new MonitorDTO(
					id,
					firstName,
					lastName,
					isSpecialEducator);

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new NotFoundException();
		}
		return monitor;
    }
    /**
     * Guarda un monitor en el DAO.
     *
     * @param obj El monitor a guardar en el  DAO.
     */
    @Override
    public void save(MonitorDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_MONITOR_QUERY")
			);
    		
    		ps.setInt(1, obj.getId());
    		ps.setString(2, obj.getFirstName());
    		ps.setString(3, obj.getLastName());
    		ps.setBoolean(4, obj.isSpecialEducator());
    		
    		ps.executeUpdate();
    	} catch(SQLException e) { 
    		throw new RuntimeException(e);
		}
    }
    /**
     * Obtiene una lista de todos los monitores almacenados en el DAO.
     *
     * @return Una lista de monitores.
     */
    @Override
    public List<MonitorDTO> getAll(Optional<ICriteria> criteria) {
    	ArrayList<MonitorDTO> listOfMonitors = new ArrayList<MonitorDTO>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_MONITORS_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				stmt = criteriaObj.applyCriteria(stmt);
			} 
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				Boolean isSpecialEducator = rs.getBoolean("specialEducator");
				listOfMonitors.add(new MonitorDTO(
						id,
						firstName,
						lastName,
						isSpecialEducator));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLException e){
			throw new DAOTimeoutException();
		}
		return listOfMonitors;
	}

    /**
     * Elimina un monitor del DAO.
     *
     * @param obj El monitor a eliminar del DAO.
     */
    @Override
    public void delete(MonitorDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_MONITOR_QUERY"));
	    	
	    	ps.setInt(1, obj.getId());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }
    /**
     * Obtiene una lista de todos los monitores relacionados con la actividad.
     *
     * @return Lista de todos los monitores relacionados con la actividad.
     */
	@Override
	public List<MonitorDTO> getMonitorsInAnActivity(ActivityDTO activity) {
		return getAll(Optional.of(new MonitorInActivityCriteria(activity.getActivityName())));
	}
	/**
     * Guarda un monitor y lo relaciona con una actividad.
     *
     * @param monitor El monitor que se va a guardar.
     * @param activity La actividad con la que estará relacionado el monitor.
     */
	@Override
	public void saveAndRelateWithAnActivity(MonitorDTO monitor, ActivityDTO activity) {
		save(monitor);
		try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("UPDATE_MONITOR_ACTIVITY_RELATION_QUERY")
			);
    		
    		ps.setInt(1, monitor.getId());
    		ps.setString(2, activity.getActivityName());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		throw new RuntimeException(e);
		}		
	}
	@Override
	public void create(MonitorDTO obj) {
		save(obj);
	}
}