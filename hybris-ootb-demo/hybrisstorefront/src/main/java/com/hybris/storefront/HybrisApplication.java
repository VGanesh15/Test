package com.hybris.storefront;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hybris OOTB Demo: Spring Boot Application entry point.
 *
 * Bootstraps all four extensions:
 * - hybriscore:       Item models, Services, DAOs, Interceptors
 * - hybriscommercefacades: DTOs, Converters, Facades
 * - hybrisocc:        OCC v2 REST API controllers
 * - hybrisstorefront: Thymeleaf storefront controllers + config
 *
 * In real SAP Commerce Cloud:
 * - The platform is bootstrapped via ApplicationContext via hybrisconfig
 * - Extensions are declared in localextensions.xml
 * - Spring XML configs are loaded from [extension]-spring.xml
 * - Database initialised via 'ant initialize' (TypeSystemSetup)
 * - Sample data loaded via ImpEx imports in SystemSetup classes
 *
 * This demo replaces:
 * - ImpEx import → DataInitializer.java (JPA bootstrap)
 * - Hybris type system → JPA entity mapping
 * - FlexibleSearch → Spring Data JPA
 * - SAP SOLR → In-memory text search
 * - Hybris cache (ehcache) → Caffeine L2 cache
 * - Hybris Spring XML → Spring Boot auto-config
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.hybris.core",
    "com.hybris.facades",
    "com.hybris.occ",
    "com.hybris.storefront"
})
@EntityScan(basePackages = "com.hybris.core.model")
@EnableJpaRepositories(basePackages = "com.hybris.core.dao")
@EnableCaching
public class HybrisApplication {

    private static final Logger LOG = LoggerFactory.getLogger(HybrisApplication.class);

    public static void main(final String[] args) {
        LOG.info("=======================================================");
        LOG.info("  SAP Hybris Commerce OOTB Demo - Starting Platform...");
        LOG.info("  Extensions: core | facades | occ | storefront");
        LOG.info("=======================================================");
        SpringApplication.run(HybrisApplication.class, args);
        LOG.info("=======================================================");
        LOG.info("  Platform started successfully!");
        LOG.info("  Storefront:  http://localhost:8080");
        LOG.info("  OCC API:     http://localhost:8080/occ/v2/electronics/products/search");
        LOG.info("  Swagger UI:  http://localhost:8080/swagger-ui.html");
        LOG.info("  H2 Console:  http://localhost:8080/h2-console");
        LOG.info("=======================================================");
    }
}
