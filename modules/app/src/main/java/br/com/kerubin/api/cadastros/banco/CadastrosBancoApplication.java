package br.com.kerubin.api.cadastros.banco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import br.com.kerubin.api.database.core.ServiceContext;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(
		exclude = { 
		        DataSourceAutoConfiguration.class,
		        HibernateJpaAutoConfiguration.class,
		        DataSourceTransactionManagerAutoConfiguration.class
		}
		, scanBasePackages = { "br.com.kerubin.api" }
)
@EnableEurekaClient
public class CadastrosBancoApplication {

	public static void main(String[] args) {
		init();
		SpringApplication.run(CadastrosBancoApplication.class, args);
	}
	
	private static void init() {
		ServiceContext.setDefaultDomain(CadastrosBancoConstants.DOMAIN);
		ServiceContext.setDefaultService(CadastrosBancoConstants.SERVICE);
		// ServiceConnectionProvider.INSTANCE.setMigrateDefaultTenant(true);
	}
}
