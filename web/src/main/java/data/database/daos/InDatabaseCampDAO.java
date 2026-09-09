package data.database.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.ActivityDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.enums.EducativeLevel;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.dao.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import data.database.DBManager;
import data.database.criteria.ActivityInCampCriteria;
import data.database.criteria.InscriptionOfACampCriteria;

/**
 * La clase InDatabaseCampDAO es una implementación en base de datos de un DAO de campamentos.
 
 */
public class InDatabaseCampDAO implements IDAO<CampDTO, Integer> {
	private DBManager dbConnection;
	/**
     * Constructor de la clase InDatabaseCampDAO.
     */

    public InDatabaseCampDAO() {
    	this.dbConnection = DBManager.getInstance();
    }
     /**
     * Busca un campamento por su identificador.
     *
     * @param identifier El identificador del campamento a buscar.
     * @return El campamento encontrado.
     * @throws NotFoundException Si el campamento no se encuentra en el DAO.
     */

    @Override
    public CampDTO find(Integer identifier) {
    	CampDTO camp;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_CAMP_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			
			rs.next();
			int campID = rs.getInt("campID");
			java.util.Date start = new java.util.Date(rs.getDate("start").getTime());
			java.util.Date end = new java.util.Date(rs.getDate("end").getTime());
			String educativeLevelString = rs.getString("educativeLevel");
			EducativeLevel educativeLevel =  EducativeLevel.valueOf(educativeLevelString);
			int capacity = rs.getInt("capacity");
			int principalMonitorId = rs.getInt("principalMonitorId");
			int specialMonitorId = rs.getInt("specialMonitorId");
			
			camp = new CampDTO(
					campID,
					start,
					end,
					educativeLevel,
					capacity
			);
			
			InDatabaseMonitorDAO monitorDao = new InDatabaseMonitorDAO();
			
			try{
				camp.setPrincipalMonitor(monitorDao.find(principalMonitorId));
			}catch (NotFoundException e){}
			try{
				camp.setSpecialMonitor(monitorDao.find(specialMonitorId));
			}catch (NotFoundException e){}
			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new NotFoundException();
		}
		
		
		
		return camp;
    }

    /**
     * Guarda un campamento en el DAO.
     *
     * @param obj El campamento a guardar en el DAO.
     */

    @Override
    public void save(CampDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = null ;
    		if((obj.getPrincipalMonitor()==null) && (obj.getSpecialMonitor()== null) ) 
    		{
	    			
	    		ps = con.prepareStatement(
	    				this.dbConnection.getQuery("SAVE_CAMP_QUERY")
				);
    		
	    		ps.setInt(1, obj.getCampID());
	    		ps.setDate(2, new Date(obj.getStart().getTime()));
	    		ps.setDate(3, new Date(obj.getEnd().getTime()));
	    		ps.setString(4, obj.getEducativeLevel().name());
	    		ps.setInt(5, obj.getCapacity());
    		}
    		else if ((obj.getPrincipalMonitor()!=null) && (obj.getSpecialMonitor()!= null)) 
    		{
    			ps = con.prepareStatement(
	    				this.dbConnection.getQuery("SAVE_CAMP_QUERY_WITH_BOTH_MONITORS")
				);
    		
	    		ps.setInt(1, obj.getCampID());
	    		ps.setDate(2, new Date(obj.getStart().getTime()));
	    		ps.setDate(3, new Date(obj.getEnd().getTime()));
	    		ps.setString(4, obj.getEducativeLevel().name());
	    		ps.setInt(5, obj.getCapacity());
	    		ps.setInt(6, obj.getPrincipalMonitor() != null ? obj.getPrincipalMonitor().getId() : null);
	    		ps.setInt(7, obj.getSpecialMonitor() != null ? obj.getSpecialMonitor().getId() : null);
    		}
    		else if (obj.getPrincipalMonitor()!=null) 
    		{
    			ps = con.prepareStatement(
	    				this.dbConnection.getQuery("SAVE_CAMP_QUERY_WITH_PRINCIPAL_MONITOR")
				);
    		
	    		ps.setInt(1, obj.getCampID());
	    		ps.setDate(2, new Date(obj.getStart().getTime()));
	    		ps.setDate(3, new Date(obj.getEnd().getTime()));
	    		ps.setString(4, obj.getEducativeLevel().name());
	    		ps.setInt(5, obj.getCapacity());
	    		ps.setInt(6, obj.getPrincipalMonitor() != null ? obj.getPrincipalMonitor().getId() : null);
    		}
    		else 
    		{
    			ps = con.prepareStatement(
	    				this.dbConnection.getQuery("SAVE_CAMP_QUERY_WITH_SPECIAL_MONITOR")
				);
    		
	    		ps.setInt(1, obj.getCampID());
	    		ps.setDate(2, new Date(obj.getStart().getTime()));
	    		ps.setDate(3, new Date(obj.getEnd().getTime()));
	    		ps.setString(4, obj.getEducativeLevel().name());
	    		ps.setInt(5, obj.getCapacity());
	    		ps.setInt(6, obj.getSpecialMonitor() != null ? obj.getSpecialMonitor().getId() : null);
    		}
    		
    		
    		ps.executeUpdate();
    	} catch(SQLException e) { 
    		throw new RuntimeException(e);
		}
    	
		InDatabaseActivityDAO activityDAO = new InDatabaseActivityDAO();
    	List<ActivityDTO> activities = activityDAO.getAll(Optional.of(new ActivityInCampCriteria(obj.getCampID())));
		for (ActivityDTO activity : activities) {
    		activityDAO.save(activity);
    		try{
	    		Connection con = this.dbConnection.getConnection();
	    		PreparedStatement ps = con.prepareStatement(
	    				this.dbConnection.getQuery("UPDATE_ACTIVITY_CAMP_RELATION_QUERY")
				);
	    		
	    		ps.setInt(1, obj.getCampID());
	    		ps.setString(2, activity.getActivityName());
	    		
	    		ps.executeUpdate();
	    	} catch(Exception e) { 
	    		throw new RuntimeException(e);
			}
		}
    }
    /**
     * Obtiene una lista de todos los campamentos almacenados en el DAO.
     *
     * @return Una lista de campamentos.
     */

    @Override
    public List<CampDTO> getAll(Optional<ICriteria> criteria) {
    	ArrayList<CampDTO> listOfCamps = new ArrayList<CampDTO>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_CAMP_IDS_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				stmt = criteriaObj.applyCriteria(stmt);
			} 
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				int campID = rs.getInt("campID");
				listOfCamps.add(find(campID));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLException e){
			throw new DAOTimeoutException();
		}
		return listOfCamps;
    }
        /**
     * Elimina un campamento del DAO.
     *
     * @param obj El campamento a eliminar del DAO.
     */

    @Override
    public void delete(CampDTO obj) {
    	InDatabaseActivityDAO activityDAO = new InDatabaseActivityDAO();
    	List<ActivityDTO> activities = activityDAO.getAll(Optional.of(new ActivityInCampCriteria(obj.getCampID())));
    	for (ActivityDTO activity : activities) {
    		activityDAO.save(activity);
    		try{
	    		Connection con = this.dbConnection.getConnection();
	    		PreparedStatement ps = con.prepareStatement(
	    				this.dbConnection.getQuery("DELETE_ACTIVITY_CAMP_RELATION_QUERY")
				);
	    		
	    		ps.setInt(1, obj.getCampID());
	    		ps.setString(2, activity.getActivityName());
	    		
	    		ps.executeUpdate();
	    	} catch(Exception e) { 
	    		throw new RuntimeException(e);
			}
		}
    	
    	InDatabaseInscriptionDAO inscriptionDAO = new InDatabaseInscriptionDAO();
    	List<InscriptionDTO> inscriptions = inscriptionDAO.getAll(Optional.of(new InscriptionOfACampCriteria(obj.getCampID())));
    	for (InscriptionDTO inscription : inscriptions) {
    		inscriptionDAO.delete(inscription);
    	}
    	
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_CAMP_QUERY"));
	    	
	    	ps.setInt(1, obj.getCampID());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }
		@Override
		public void create(CampDTO obj) {
			try{
	    		Connection con = this.dbConnection.getConnection();
	    		PreparedStatement ps = null ;
	    		if((obj.getPrincipalMonitor()==null) && (obj.getSpecialMonitor()== null) ) 
	    		{
		    			
		    		ps = con.prepareStatement(
		    				this.dbConnection.getQuery("CREATE_CAMP_QUERY")
					);
	    		
		    		ps.setDate(1, new Date(obj.getStart().getTime()));
		    		ps.setDate(2, new Date(obj.getEnd().getTime()));
		    		ps.setString(3, obj.getEducativeLevel().name());
		    		ps.setInt(4, obj.getCapacity());
	    		}
	    		else if ((obj.getPrincipalMonitor()!=null) && (obj.getSpecialMonitor()!= null)) 
	    		{
	    			ps = con.prepareStatement(
		    				this.dbConnection.getQuery("CREATE_CAMP_QUERY_WITH_BOTH_MONITORS")
					);
	    		
		    		ps.setDate(1, new Date(obj.getStart().getTime()));
		    		ps.setDate(2, new Date(obj.getEnd().getTime()));
		    		ps.setString(3, obj.getEducativeLevel().name());
		    		ps.setInt(4, obj.getCapacity());
		    		ps.setInt(5, obj.getPrincipalMonitor() != null ? obj.getPrincipalMonitor().getId() : null);
		    		ps.setInt(6, obj.getSpecialMonitor() != null ? obj.getSpecialMonitor().getId() : null);
	    		}
	    		else if (obj.getPrincipalMonitor()!=null) 
	    		{
	    			ps = con.prepareStatement(
		    				this.dbConnection.getQuery("CREATE_CAMP_QUERY_WITH_PRINCIPAL_MONITOR")
					);
	    		
		    		ps.setDate(1, new Date(obj.getStart().getTime()));
		    		ps.setDate(2, new Date(obj.getEnd().getTime()));
		    		ps.setString(3, obj.getEducativeLevel().name());
		    		ps.setInt(4, obj.getCapacity());
		    		ps.setInt(5, obj.getPrincipalMonitor() != null ? obj.getPrincipalMonitor().getId() : null);
	    		}
	    		else 
	    		{
	    			ps = con.prepareStatement(
		    				this.dbConnection.getQuery("CREATE_CAMP_QUERY_WITH_SPECIAL_MONITOR")
					);
	    		
		    		ps.setDate(1, new Date(obj.getStart().getTime()));
		    		ps.setDate(2, new Date(obj.getEnd().getTime()));
		    		ps.setString(3, obj.getEducativeLevel().name());
		    		ps.setInt(4, obj.getCapacity());
		    		ps.setInt(5, obj.getSpecialMonitor() != null ? obj.getSpecialMonitor().getId() : null);
	    		}
	    		
	    		
	    		ps.executeUpdate();
	    	} catch(SQLException e) { 
	    		throw new RuntimeException(e);
			}
	    	
			InDatabaseActivityDAO activityDAO = new InDatabaseActivityDAO();
	    	List<ActivityDTO> activities = activityDAO.getAll(Optional.of(new ActivityInCampCriteria(obj.getCampID())));
			for (ActivityDTO activity : activities) {
	    		activityDAO.save(activity);
	    		try{
		    		Connection con = this.dbConnection.getConnection();
		    		PreparedStatement ps = con.prepareStatement(
		    				this.dbConnection.getQuery("UPDATE_ACTIVITY_CAMP_RELATION_QUERY")
					);
		    		
		    		ps.setInt(1, obj.getCampID());
		    		ps.setString(2, activity.getActivityName());
		    		
		    		ps.executeUpdate();
		    	} catch(Exception e) { 
		    		throw new RuntimeException(e);
				}
			}
		}

}
