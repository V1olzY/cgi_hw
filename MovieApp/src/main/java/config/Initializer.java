package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initializes the Spring MVC DispatcherServlet and associates it with the servlet container.
 */
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Specify configuration classes for the root application context.
     *
     * @return An array of configuration classes
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {MvcConfig.class, DbConfig.class};
    }

    /**
     * Specify configuration classes for the servlet application context.
     *
     * @return An array of configuration classes
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    /**
     * Specify the servlet mapping(s) for the DispatcherServlet.
     *
     * @return An array of servlet mappings
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/api/*"};
    }
}
