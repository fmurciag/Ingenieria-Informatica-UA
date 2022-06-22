package ppss.matriculacion.dao;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.junit.jupiter.api.*;
import ppss.matriculacion.to.AlumnoTO;

import java.time.LocalDate;
@Tag("Integracion-fase1")
public class AlumnoDAOIT {
    private JDBCAlumnoDAO alumnoDAO; //SUT
    private IDatabaseTester databaseTester;
    private IDatabaseConnection connection;

    @BeforeEach
    public void setUp() throws Exception {

        String cadena_conexionDB = "jdbc:mysql://localhost:3306/matriculacion?useSSL=false";
        databaseTester = new MiJdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
                cadena_conexionDB, "root", "ppss");
        //obtenemos la conexión con la BD
        connection = databaseTester.getConnection();
    }
    @Test
    public void testA1() throws Exception {
        AlumnoTO alumno= new AlumnoTO();
        alumno.setNif("33333333C");
        alumno.setNombre("Elena Aguirre Juarez");
        alumno.setFechaNacimiento(LocalDate.of(1985,2,22));

        //Inicializamos el dataSet con los datos iniciales de la tabla
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();
        //invocamos a nuestro SUT
        Assertions.assertDoesNotThrow(()->new FactoriaDAO().getAlumnoDAO().addAlumno(alumno));

        //recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        //Recuperamos los datos de la tabla alumnos
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla3.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testA2() throws Exception {
        AlumnoTO alumno= new AlumnoTO();
        alumno.setNif("11111111A");
        alumno.setNombre("Alfonso Ramirez Ruiz");
        alumno.setFechaNacimiento(LocalDate.of(1982,2,22));

        //Inicializamos el dataSet con los datos iniciales de la tabla
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();
        //invocamos a nuestro SUT
        DAOException ex =new DAOException();
        ex=Assertions.assertThrows(DAOException.class,
                ()->new FactoriaDAO().getAlumnoDAO().addAlumno(alumno)
        );
        Assertions.assertEquals("Error al conectar con BD",ex.getMessage());
    }
    @Test
    public void testA3() throws Exception {
        AlumnoTO alumno= new AlumnoTO();
        alumno.setNif("44444444D");
        alumno.setNombre(null);
        alumno.setFechaNacimiento(LocalDate.of(1982,2,22));

        //Inicializamos el dataSet con los datos iniciales de la tabla
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();
        //invocamos a nuestro SUT
        DAOException ex =new DAOException();
        ex=Assertions.assertThrows(DAOException.class,
                ()->new FactoriaDAO().getAlumnoDAO().addAlumno(alumno)
        );
        Assertions.assertEquals("Error al conectar con BD",ex.getMessage());
    }
    @Test
    public void testA4() throws Exception {
        AlumnoTO alumno= null;

        //Inicializamos el dataSet con los datos iniciales de la tabla
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();
        //invocamos a nuestro SUT
        DAOException ex =new DAOException();
        ex=Assertions.assertThrows(DAOException.class,
                ()->new FactoriaDAO().getAlumnoDAO().addAlumno(alumno)
        );
        Assertions.assertEquals("Alumno nulo",ex.getMessage());
    }
    @Test
    public void testA5() throws Exception {
        AlumnoTO alumno= new AlumnoTO();
        alumno.setNif(null);
        alumno.setNombre("Pedro Garcia Lopez");
        alumno.setFechaNacimiento(LocalDate.of(1982,2,22));

        //Inicializamos el dataSet con los datos iniciales de la tabla
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();
        //invocamos a nuestro SUT
        DAOException ex =new DAOException();
        ex=Assertions.assertThrows(DAOException.class,
                ()->new FactoriaDAO().getAlumnoDAO().addAlumno(alumno)
        );
        Assertions.assertEquals("Error al conectar con BD",ex.getMessage());
    }
    @Test
    public void testB1() throws Exception {
        //Inicializamos el dataSet con los datos iniciales de la tabla
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();
        //invocamos a nuestro SUT
        Assertions.assertDoesNotThrow(()->new FactoriaDAO().getAlumnoDAO().delAlumno("11111111A"));

        //recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        //Recuperamos los datos de la tabla alumnos
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla4.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(expectedTable, actualTable);
    }
    @Test
    public void testB2() throws Exception {
        AlumnoTO alumno= new AlumnoTO();
        alumno.setNif(null);
        alumno.setNombre("Pedro Garcia Lopez");
        alumno.setFechaNacimiento(LocalDate.of(1982,2,22));

        //Inicializamos el dataSet con los datos iniciales de la tabla
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();
        //invocamos a nuestro SUT
        DAOException ex =new DAOException();
        ex=Assertions.assertThrows(DAOException.class,
                ()->new FactoriaDAO().getAlumnoDAO().delAlumno("33333333C")
        );
        Assertions.assertEquals("No se ha borrado ningun alumno",ex.getMessage());
    }

}
