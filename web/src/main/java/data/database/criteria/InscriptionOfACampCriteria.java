package data.database.criteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import business.interfaces.ICriteria;
import data.database.DBManager;

/**
 * Esta clase implementa la interfaz ICriteria y representa un criterio que se aplica a las inscripciones de un campamento.
 */
public class InscriptionOfACampCriteria implements ICriteria {
    private int campID;

    /**
     * Este es el constructor de la clase InscriptionOfACampCriteria.
     * @param campID El ID del campamento para el cual se aplicará el criterio.
     */
    public InscriptionOfACampCriteria(int campID) {
        this.campID=campID;
    }

    /**
     * Este método se utiliza para aplicar el criterio a un objeto PreparedStatement.
     * @param obj El PreparedStatement al que se aplicará el criterio.
     * @return T El PreparedStatement después de aplicarle el criterio.
     */
    @Override
    public <T> T applyCriteria(T obj) {
        DBManager dbConnection = DBManager.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = (Connection) ((PreparedStatement) obj).getConnection();
            String query = dbConnection.getQuery("GET_INSCRIPTIONS_OF_A_CAMP_QUERY");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, campID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        @SuppressWarnings("unchecked")
        T criteriaApplied =  (T) stmt;
        return criteriaApplied;
    }
}