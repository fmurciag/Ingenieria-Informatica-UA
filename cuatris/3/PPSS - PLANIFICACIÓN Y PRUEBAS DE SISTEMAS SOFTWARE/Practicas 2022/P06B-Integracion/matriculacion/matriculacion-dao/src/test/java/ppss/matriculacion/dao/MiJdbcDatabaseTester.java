package ppss.matriculacion.dao;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;

public class MiJdbcDatabaseTester extends JdbcDatabaseTester {

    public MiJdbcDatabaseTester(String driverClass, String connectionUrl, String username,
                                String password )
            throws ClassNotFoundException {
        super( driverClass, connectionUrl, username, password );
    }

    @Override
    public IDatabaseConnection getConnection() throws Exception {
        IDatabaseConnection result = super.getConnection();
        DatabaseConfig dbConfig = result.getConfig();
        //para evitar un warning al acceder a la base de datos
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        return result;
    }

}
