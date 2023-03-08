
package com.wiiisdom.hr.wp;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.core.UriBuilder;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

public class Main {

    public static final URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(9998).build();

    
    public static void main(String[] args) throws Exception {


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Connection connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?seUnicode=true"
                        + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                System.getenv("dbhost"), System.getenv().getOrDefault("dbport", "3306"),
                System.getenv("database")),
                System.getenv("dbuser"), System.getenv("dbpass"));
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        try(Liquibase liquibase = new Liquibase("/changelog.xml", new ClassLoaderResourceAccessor(),
                database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
        final ResourceConfig config = new RestApplication().configureApplication();
        Server jettyServer = JettyHttpContainerFactory.createServer(BASE_URI, config);
        jettyServer.setHandler(context);
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }    
}
