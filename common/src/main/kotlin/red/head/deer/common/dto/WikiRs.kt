package red.head.deer.common.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitPage(
    var type: String? = null,
    var title: String? = null,
    var namespace: Namespace? = null,
    @get:JsonProperty("wikibase_item", required = false)
    var wikiBaseItem: String? = null,
    var titles: Titles? = null,
    @get:JsonProperty("pageid", required = false)
    var pageId: Int? = null,
    var thumbnail: Thumbnail? = null,
    @get:JsonProperty("originalimage", required = false)
    var originalImage: OriginalImage? = null,
    var lang: String? = null,
    var dir: String? = null,
    var revision: String? = null,
    @get:JsonProperty("displaytitle", required = false)
    var displayTitle: String? = null,
    var tid: String? = null,
    var timestamp: String? = null,
    var description: String? = null,
    @get:JsonProperty("description_source", required = false)
    var descriptionSource: String? = null,
    @get:JsonProperty("content_urls", required = false)
    var contentUrls: ContentUrls? = null,
    var extract: String? = null,
    @get:JsonProperty("extract_html", required = false)
    var extractHtml: String? = null,
) {
    data class Namespace(
        var id: String? = null,
        var text: String? = null,
    )

    data class Titles(
        var canonical: String? = null,
        var normalized: String? = null,
        var display: String? = null,
    )

    data class Thumbnail(
        var source: String? = null,
        var width: Int? = null,
        var height: Int? = null,
    )

    data class OriginalImage(
        var source: String? = null,
        var width: Int? = null,
        var height: Int? = null,
    )

    data class ContentUrls(
        var desktop: Desktop? = null,
        var mobile: Mobile? = null,
    ) {
        data class Desktop(
            var page: String? = null,
            var revisions: String? = null,
            var edit: String? = null,
            var talk: String? = null,
        )

        data class Mobile(
            var page: String? = null,
            var revisions: String? = null,
            var edit: String? = null,
            var talk: String? = null,
        )
    }
}