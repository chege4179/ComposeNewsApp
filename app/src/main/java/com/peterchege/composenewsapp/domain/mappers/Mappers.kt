package com.peterchege.composenewsapp.domain.mappers

import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.api.responses.Source
import com.peterchege.composenewsapp.core.room.entity.BookmarkArticleEntity
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.domain.models.ArticleUI

fun NetworkArticle.toBookmarkEntity(): BookmarkArticleEntity {
    return BookmarkArticleEntity(
        id = null,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun NetworkArticle.toCacheEntity():CachedArticleEntity{
    return CachedArticleEntity(
        id = null,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun BookmarkArticleEntity.toExternalModel(): NetworkArticle{
    return NetworkArticle(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt ?:"",
        source = source ?: Source("",""),
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage
    )

}

fun CachedArticleEntity.toExternalModel(): NetworkArticle{
    return NetworkArticle(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt ?:"",
        source = source ?: Source("",""),
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage
    )

}

fun CachedArticleEntity.toPresentationModel():ArticleUI{
    return ArticleUI(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt ?:"",
        source = source ?: Source("",""),
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage
    )
}

fun BookmarkArticleEntity.toPresentationModel():ArticleUI{
    return ArticleUI(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt ?:"",
        source = source ?: Source("",""),
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage
    )
}