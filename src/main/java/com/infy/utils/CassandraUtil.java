package com.infy.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;


@Configuration
@EnableReactiveCassandraRepositories(basePackages = {"com.infy.repositories"})
public class CassandraUtil {
	private static final Logger log = LogManager.getLogger(CassandraUtil.class);
	
	@Bean
	CqlSessionFactoryBean session() {
		CqlSessionFactoryBean cqlSessionFactory = new CqlSessionFactoryBean();
		
		log.trace("Establishing connection with Cassandra");
		DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
		cqlSessionFactory.setSessionBuilderConfigurer(sessionBuilder -> sessionBuilder.withConfigLoader(loader).withKeyspace("\"TRMS\""));
		cqlSessionFactory.setKeyspaceName("\"TRMS\"");
		
		return cqlSessionFactory;
	}
	
	@Bean
	public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {
		SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
		((MappingCassandraConverter) converter).setUserTypeResolver(new SimpleUserTypeResolver(session));
		sessionFactory.setSession(session);
		sessionFactory.setConverter(converter);
		// Configure the session factory to create tables automatically. Set to NONE if table creation is to be done manually.
		sessionFactory.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);
		
		return sessionFactory;
	}
	
	@Bean
	public CassandraConverter converter(CassandraMappingContext mappingContext) {
		return new MappingCassandraConverter(mappingContext);
	}
	
	@Bean
	public CassandraMappingContext mappingContext() {
		return new CassandraMappingContext();
	}
	
	@Bean
	public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
		return new CassandraTemplate(sessionFactory, converter);
	}
}
