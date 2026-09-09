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

import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.exceptions.dao.DAOTimeoutException;
import business.exceptions.dao.NotFoundException;
import business.interfaces.ICriteria;
import business.interfaces.IDAO;
import business.interfaces.IInscriptionDAO;
import data.database.DBManager;
import data.database.criteria.InscriptionOfACampCriteria;


/**
 * La clase InDatabaseInscriptionDAO es una implementación en base de datos de un DAO de inscripciones.

 */

public class InDatabaseInscriptionDAO implements IInscriptionDAO {
	private DBManager dbConnection;
	
	/**
     * Constructor de la clase InDatabaseInscriptionDA0.
     */

    public InDatabaseInscriptionDAO() {
       this.dbConnection = DBManager.getInstance();
    }


    /**
     * Busca una inscripción por su nombre.
     *
     * @param identifier El nombre de la inscripción a buscar.
     * @return La inscripción encontrada.
     * @throws NotFoundException Si la inscripción no se encuentra en el DAO.
     */

    @Override
    public InscriptionDTO find(String identifier) {
    	String[] parts = identifier.split("-");
    	int assistantId = Integer.parseInt(parts[0]);
    	int campId = Integer.parseInt(parts[1]);
    	
    	InscriptionDTO inscription;
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("FIND_INSCRIPTION_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, assistantId);
			stmt.setInt(2, campId);
			ResultSet rs = (ResultSet) stmt.executeQuery();
			if (rs.next())
			{

				java.util.Date inscriptionDate = new java.util.Date(rs.getDate("inscriptionDate").getTime());
				float price = rs.getFloat("price");
				Boolean canBeCanceled = rs.getBoolean("canBeCanceled");
				Boolean isPartial = rs.getBoolean("isPartial");
				inscription = new InscriptionDTO(
						assistantId,
						campId,
						inscriptionDate,
						price,
						canBeCanceled,
						isPartial
				);
	
				if (stmt != null){ 
					stmt.close(); 
				}
				dbConnection.closeConnection();		
			}
			else 
			{
				throw new NotFoundException();
				
			}
		} catch (SQLTimeoutException e){
			throw new DAOTimeoutException();
		} catch (SQLException e) {
			throw new NotFoundException();
		}
		
		return inscription;
    }


    /**
     * Guarda una inscripción en el DAO.
     *
     * @param obj La inscripción a guardar en el DAO.
     */

    @Override
    public void save(InscriptionDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(
    				this.dbConnection.getQuery("SAVE_INSCRIPTION_QUERY")
			);
    		
    		ps.setInt(1, obj.getAssistantId());
    		ps.setInt(2, obj.getCampId());
    		ps.setDate(3, new Date(obj.getInscriptionDate().getTime()));
    		ps.setFloat(4, obj.getPrice());
    		ps.setBoolean(5, obj.canBeCanceled());
    		ps.setBoolean(6, obj.isPartial());
    		
    		ps.executeUpdate();
    	} catch(Exception e) { 
    		throw new RuntimeException(e);
		}
    }


    /**
     * Obtiene una lista de todas las inscripciones almacenadas en el DAO.
     *
     * @return Una lista de inscripciones.
     */

    @Override
    public List<InscriptionDTO> getAll(Optional<ICriteria> criteria) {
    	ArrayList<InscriptionDTO> listOfInscriptions = new ArrayList<InscriptionDTO>();
		try {
    		Connection con = this.dbConnection.getConnection();

			String query = this.dbConnection.getQuery("GET_ALL_INSCRIPTIONS_QUERY");
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			if (criteria.isPresent()) {
				ICriteria criteriaObj = criteria.get();
				stmt = criteriaObj.applyCriteria(stmt);
			} 
			ResultSet rs = (ResultSet) stmt.executeQuery();

			while (rs.next()) {
				int assistantId = rs.getInt("assistantId");
				int campId = rs.getInt("campId");
				Date inscriptionDate = rs.getDate("inscriptionDate");
				float price = rs.getFloat("price");
				Boolean canBeCanceled = rs.getBoolean("canBeCanceled");
				Boolean isPartial = rs.getBoolean("isPartial");
				listOfInscriptions.add(new InscriptionDTO(
						assistantId,
						campId,
						inscriptionDate,
						price,
						canBeCanceled,
						isPartial
				));
			}

			if (stmt != null){ 
				stmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.out.println(e);
			throw new DAOTimeoutException();
		}
		return listOfInscriptions;
    }

    /**
     * Elimina una inscripción del DAO.
     *
     * @param obj La inscripción a eliminar del DAO.
     */ 

    @Override
    public void delete(InscriptionDTO obj) {
    	try{
    		Connection con = this.dbConnection.getConnection();
	    	PreparedStatement ps=con.prepareStatement(this.dbConnection.getQuery("DELETE_INSCRIPTION_QUERY"));
	    	
	    	ps.setInt(1, obj.getAssistantId());
	    	ps.setInt(2, obj.getCampId());
	    	
	    	ps.executeUpdate();
    	} catch(SQLTimeoutException e){
    		throw new DAOTimeoutException();
		} catch(SQLException e) {
			throw new NotFoundException();
		}
    }


	@Override
	public List<InscriptionDTO> getInscriptionOfACamp(CampDTO camp) {
		return getAll(Optional.of(new InscriptionOfACampCriteria(camp.getCampID())));
	}


	@Override
	public void create(InscriptionDTO obj) {
		save(obj);
	}
}
