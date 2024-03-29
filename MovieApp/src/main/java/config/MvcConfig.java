package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configures Spring MVC and enables component scanning for DAO and controller packages.
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"dao", "controller"})
public class MvcConfig {

}
