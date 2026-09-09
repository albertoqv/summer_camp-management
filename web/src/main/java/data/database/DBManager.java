package data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Clase para realizar las conexiones a base de datos
 */

public class DBManager {
	private static Properties _queriesProp;
	private static String _dbUrl;
	private static String _dbUsername;
	private static String _dbPassword;
	
	Map<String, String> queries;
	protected Connection connection = null;
	private static DBManager instance;

	/**
	 * Este método se utiliza para obtener la instancia de DBManager.
	 * 
	 * @return DBManager La instancia de DBManager.
	 */
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	public static void setProperties(Properties queriesProp, String dbUrl, String dbUsername, String dbPassword) {
		_queriesProp = queriesProp;
		_dbUrl = dbUrl;
		_dbUsername = dbUsername;
		_dbPassword = dbPassword;
	}

	/**
	 * Este es el constructor de la clase DBManager.
	 */
	private DBManager() {
		this.queries = new HashMap<String, String>();
		
		for (Object key : _queriesProp.keySet()) {
			this.queries.put((String) key, _queriesProp.getProperty((String) key));
		}
	}

	/**
	 * Este método se utiliza para obtener una consulta SQL a partir de una clave.
	 * 
	 * @param queryKey La clave de la consulta SQL.
	 * @return String La consulta SQL.
	 */
	public String getQuery(String queryKey) {
		return this.queries.get(queryKey);
	}

	/**
	 * Este método se utiliza para obtener una conexión a la base de datos.
	 * 
	 * @return Connection La conexión a la base de datos.
	 */
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = (Connection) DriverManager.getConnection(_dbUrl, _dbUsername, _dbPassword);
		} catch (SQLException e) {
			System.err.println("Connection to MySQL has failed!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found.");
			e.printStackTrace();
		}
		return this.connection;
	}

	/**
	 * Este método se utiliza para cerrar la conexión a la base de datos.
	 */
	public void closeConnection() {
		try {
			if (this.connection != null && !this.connection.isClosed()) {
				this.connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Error while trying to close the connection.");
			e.printStackTrace();
		}
	}
}