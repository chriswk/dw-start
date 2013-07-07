package com.tka.dwstart.jdbi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.skife.jdbi.v2.DBI;

public class TestWithLiquibase {

    protected static final String DB_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    protected static final String DB_URL = "jdbc:hsqldb:mem:test";
    protected static final String DB_USERNAME = "sa";
    protected static final String DB_PASSWORD = "";


    private static Connection conn;
    private static Liquibase liquibase;

    protected DBI jdbi;

    @Before
    public void setupJdbi() {
        jdbi = new DBI(DB_URL, DB_USERNAME, DB_PASSWORD);
        jdbi.registerArgumentFactory(new DateAsTimestampArgument());
    }



    @BeforeClass
    public static void createTestData() throws SQLException,
            ClassNotFoundException, LiquibaseException {

        Class.forName(DB_DRIVER);
        conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(conn));

        liquibase = new Liquibase("migrations-test.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(null);

    }

    @AfterClass
    public static void removeTestData() throws LiquibaseException, SQLException {
        liquibase.rollback(1000, null);
        conn.close();
    }
}
