package config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class for database-related settings.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
public class DbConfig {

    /**
     * Configures the data source for the database.
     *
     * @param env The environment object to retrieve properties
     * @return The configured DataSource object
     */
    @Bean
    public DataSource dataSource(Environment env) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.hsqldb.jdbcDriver");
        ds.setUrl(env.getProperty("hsql.url"));
        return ds;
    }

    /**
     * Defines the SQL dialect to be used by Hibernate.
     *
     * @return The SQL dialect
     */
    @Bean
    public String dialect() {
        return "org.hibernate.dialect.HSQLDialect";
    }

    /**
     * Configures the EntityManagerFactory.
     *
     * @param dataSource The configured DataSource object
     * @param dialect    The SQL dialect to be used by Hibernate
     * @return The configured EntityManagerFactory object
     */
    @Bean
    public EntityManagerFactory entityManagerFactory(
            DataSource dataSource,
            @Qualifier("dialect") String  dialect) {

        // Initializes the database using the schema.sql file
        var populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"));

        DatabasePopulatorUtils.execute(populator, dataSource);

        // Configures the EntityManagerFactory
        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceProviderClass(
                HibernatePersistenceProvider.class);
        factory.setPackagesToScan("model");
        factory.setDataSource(dataSource);
        factory.setJpaProperties(additionalProperties(dialect));
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    /**
     * Configures the transaction manager.
     *
     * @param entityManagerFactory The configured EntityManagerFactory object
     * @return The configured PlatformTransactionManager object
     */
    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * Defines additional properties for Hibernate.
     *
     * @param dialect The SQL dialect to be used by Hibernate
     * @return The additional Hibernate properties
     */
    private Properties additionalProperties(String dialect) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "true");

        return properties;
    }

}
