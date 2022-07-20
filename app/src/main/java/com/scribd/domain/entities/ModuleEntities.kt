package com.scribd.domain.entities

interface ModuleEntity {
    val analyticsId: String?
    val type: ModuleType
}

data class HeroContentModuleEntity(
    override val analyticsId: String?,
    val title: String,
    val subtitle: String,
    val contentType: DiscoverContentType,
) : ModuleEntity {
    override val type = ModuleType.HERO_CONTENT
}

data class FeaturedDocumentModuleEntity(
    override val analyticsId: String?,
    val title: String,
    val blurbQuote: String,
    val blurbParagraph: String,
    val document: ListItemDisplayContent
) : ModuleEntity {
    override val type = ModuleType.FEATURED_DOCUMENT
}

data class DocumentCarouselModuleEntity(
    override val analyticsId: String?,
    val title: String,
    val subtitle: String,
    val documents: List<ListItemDisplayContent>
) : ModuleEntity {
    override val type = ModuleType.DOCUMENT_CAROUSEL
}

enum class ModuleType(val id: String) {
    HERO_CONTENT("hero_content"),
    FEATURED_DOCUMENT("featured_document"),
    DOCUMENT_CAROUSEL("document_carousel")
}

/**
 * A content type (for example, "audiobooks")
 *
 * @param analyticsId The analytics id for this content type as used in this module
 * @param title Human-readable title for this content type
 * @param name Machine-readable identifier for this content type
 */
data class DiscoverContentType(
    val analyticsId: String,
    val title: String,
    val name: String
)


interface ListItemDisplayContent {
    val id: Int
    val title: String
    val author: String
    val thumbnailBadgeBitmask: Long
//    val documentType: DocumentType
//    val readerType: ReaderType
//    val enclosingMembership: EnclosingMembership
    val shortDescription: String?
    val publisherId: Int?
    val uploader: String?
//    val seriesMembership: DocumentSeriesMembership
    val globalReadingSpeedWPM: Float
    val wordCount: Int
//    val restrictionOrThrottling: DocumentRestrictionOrThrottling
    val analyticsId: String?
}
