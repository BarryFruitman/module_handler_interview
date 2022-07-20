package com.scribd.app.discover_modules;

import org.jetbrains.annotations.Nullable;

public class BasicDiscoverModuleWithMetadataFactory {
    public BasicDiscoverModuleWithMetadataFactory(ModuleHandler abstractDocumentCarouselModuleHandler, DiscoverModule discoverModule, DiscoverModuleWithMetadata.ModuleMetadata metadata) {

    }

    public BasicDiscoverModuleWithMetadata createSingleDocumentModule() {
        return new BasicDiscoverModuleWithMetadata();
    }

    @Nullable
    public BasicDiscoverModuleWithMetadata createBasicDiscoverModuleWithMetadata() {
        return new BasicDiscoverModuleWithMetadata();
    }
}
