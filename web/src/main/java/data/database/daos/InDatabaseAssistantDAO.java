package data.database.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.UserDTO;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.dao.NotFoundException;
import business.interfaces.IAssistantDAO;
import business.interfaces.ICriteria;
import data.database.DBManager;
import data.database.criteria.AssistantInActivityCriteria;
import data.database.criteria.AssistantInCampCriteria;
import data.database.criteria.AssistantRelatedWithUserCriteria;


/**
 * La clase InDatabaseAssistantDAO es una implementación en base de datos de un DAO de asistentes.
 
 */
public class InDatabaseAssistantDAO implements IAssistantDAO{

	private DBManager dbConnection;
    /**
     * Constructor de la clase InDatabaseAssistantDAO.
     * Inicializa un nuevo mapa para almacenar asistentes en memoria y carga los asistentes almacenados en el DAO
     * 
     */
    public InDatabaseAssistantDAO() {
    	this.dbConnection = DBManager.getInstance();
    }
    /**
     * Busca a un asistente por su identificador.
     *
     * @param identifier El identificador del asistente a buscar.
     * @return El asistente encontrado.
     * @throws NotFoundException Si el asistente no se encuentra en el DAO.
     */
    @Override
    public AssistantDTO find(Integer identifier) {
    	AssistantDTO assistant;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_ASSISTANT_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			
			rs.next();
			int id = rs.getInt("id");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			java.util.Date birthDate = new java.util.Date(rs.getDate("birthDate").getTime());
			Boolean requiredSpecialAttention = rs.getBoolean("requireSpecialAttention");
			assistant = new AssistantDTO(
					id,
					firstName,
					lastName,
					birthDate,
					requiredSpecialAttention);

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new NotFoundException();
		}
		return assistant;    
	}
    /**
     * Guarda a un asistente en el DAO.
     *
     * @param obj El asistente a guardar en el DAO.
     */
    @Override
    public void save(AssistantDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_ASSISTANT_QUERY")
			);
    		
    		ps.setInt(1, obj.getId());
    		ps.setString(2, obj.getFirstName());
    		ps.setString(3, obj.getLastName());
    		ps.setDate(4, new Date(obj.getBirthDate().getTime()));
    		ps.setBoolean(5, obj.isRequireSpecialAttention());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		throw new RuntimeException(e);
		}
    }
    /**
     * Obtiene una lista de todos los asistentes almacenados en el DAO.
     *
     * @return Una lista de asistentes.
     */
    @Override
    public List<AssistantDTO> getAll(Optional<ICriteria> criteria) {
    	ArrayList<AssistantDTO> listOfAssistants = new ArrayList<AssistantDTO>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_ASSISTANTS_QUERY");
			
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
				Date birthDate = rs.getDate("birthDate");
				Boolean requiredSpecialAttention = rs.getBoolean("requireSpecialAttention");
				listOfAssistants.add(new AssistantDTO(
						id,
						firstName,
						lastName,
						birthDate,
						requiredSpecialAttention));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLException e){
			throw new DAOTimeoutException();
		}
		return listOfAssistants;
    }
    /**
     * Elimina a un asistente del DAO.
     *
     * @param obj El asistente a eliminar del DAO.
     */
    @Override
    public void delete(AssistantDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_ASSISTANT_QUERY"));
	    	
	    	ps.setInt(1, obj.getId());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }
	
	@Override
	public List<AssistantDTO> getAssistantsInACamp(CampDTO camp) {
		return getAll(Optional.of(new AssistantInCampCriteria(camp.getCampID())));
	}
	@Override
	public List<AssistantDTO> getAssistantsInAnActivity(ActivityDTO activity) {
		return getAll(Optional.of(new AssistantInActivityCriteria(activity.getActivityName())));
	}
	@Override
	public void saveAndRelateWithAnUser(AssistantDTO assistant, UserDTO user) {
		save(assistant);
		try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("UPDATE_ASSISTANT_USER_RELATION_QUERY")
			);
    		
    		ps.setString(1, user.getEmail());
    		ps.setInt(2, assistant.getId());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		throw new RuntimeException(e);
		}		
	}
	@Override
	public List<AssistantDTO> getAssistantsRelatedWithAnUser(UserDTO user) {
		return getAll(Optional.of(new AssistantRelatedWithUserCriteria(user.getEmail())));
	}
	@Override
	public void create(AssistantDTO obj) {
		save(obj);
	}

}