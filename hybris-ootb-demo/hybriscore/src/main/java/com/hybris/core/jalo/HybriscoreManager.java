package com.hybris.core.jalo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Hybris OOTB: Jalo Manager class for hybriscore extension.
 * Mirrors the generated [ExtensionName]Manager Jalo class pattern.
 *
 * In SAP Hybris, the Jalo layer is the legacy persistence layer (pre-ServiceLayer).
 * Each extension generates a [Name]Manager extending AbstractManager.
 * The Jalo layer is deprecated in favor of the ServiceLayer (services + DAOs + models),
 * but is still present in OOTB extensions for backward compatibility.
 *
 * Key Jalo classes generated per extension:
 * - [Name]Manager   : entry point, item creation
 * - [TypeCode]      : Jalo item implementation (e.g., ProductImpl extends GeneratedProduct)
 * - Generated[Name] : auto-generated from items.xml, never hand-edited
 *
 * The ServiceLayer calls Jalo via ModelService.getAttributeValue() which delegates
 * to the Jalo item's getter, going through JaloPropertyAccessStrategy.
 */
@Component("hybrisCorejaloManager")
public class HybriscoreManager {

    private static final Logger LOG = LoggerFactory.getLogger(HybriscoreManager.class);
    private static final String EXTENSION_NAME = "hybriscore";

    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    /**
     * Called during system initialization (SystemSetupContext).
     * In Hybris, this would be annotated with @SystemSetup(extension = "hybriscore").
     */
    public void createEssentialData() {
        LOG.info("[{}] Creating essential data (currencies, languages, countries)...", EXTENSION_NAME);
    }

    /**
     * Called during sample data import (SystemSetupContext with Type.PROJECT).
     * In Hybris, this triggers ImpEx import of sample products and categories.
     */
    public void createProjectData() {
        LOG.info("[{}] Creating project data (sample catalog, products, customers)...", EXTENSION_NAME);
    }

    /**
     * In real Hybris, this method is called by the platform during:
     * - ant initialize
     * - ant updatesystem
     * to initialize the extension's type system from items.xml.
     */
    public void initializeExtension() {
        LOG.info("[{}] Extension initialized. Type system registered.", EXTENSION_NAME);
    }
}
