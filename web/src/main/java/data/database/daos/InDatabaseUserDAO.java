package data.database.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import business.dtos.UserDTO;
import business.enums.UserRole;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.dao.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import data.database.DBManager;

public class InDatabaseUserDAO implements IDAO<UserDTO, String>{

	private DBManager dbConnection;
    
	
    public InDatabaseUserDAO() {
    	this.dbConnection = DBManager.getInstance();
    }
	
	@Override
	public UserDTO find(String identifier) {
		UserDTO user;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_USER_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, identifier);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			
			rs.next();
			String email = rs.getString("email");
			String password = rs.getString("password");
			String role = rs.getString("role");
			user = new UserDTO(
					email,
					password,
					UserRole.valueOf(role));

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new NotFoundException();
		}
		return user;    
	}

	@Override
	public void save(UserDTO obj) {
		try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_USER_QUERY")
			);
    		
    		ps.setString(1, obj.getEmail());
    		ps.setString(2, obj.getPassword());
    		ps.setString(3, obj.getRole().name());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(UserDTO obj) {
		try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_USER_QUERY"));
	    	
	    	ps.setString(1, obj.getEmail());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
		
	}

	@Override
	public List<UserDTO> getAll(Optional<ICriteria> criteria) {
		ArrayList<UserDTO> listOfUsers = new ArrayList<UserDTO>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_USERS_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				stmt = criteriaObj.applyCriteria(stmt);
			} 
			
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				String email = rs.getString("email");
				String password = rs.getString("password");
				String role = rs.getString("role");
				listOfUsers.add(new UserDTO(
						email,
						password,
						UserRole.valueOf(role)));
				
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (SQLException e){
			throw new DAOTimeoutException();
		}
		return listOfUsers;
	}

	@Override
	public void create(UserDTO obj) {
		save(obj);
	}

}
