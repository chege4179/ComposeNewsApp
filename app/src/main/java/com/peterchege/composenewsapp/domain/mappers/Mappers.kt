/*
 * Copyright 2023 Compose News App By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.composenewsapp.domain.mappers

import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.api.responses.Source
import com.peterchege.composenewsapp.core.room.entity.BookmarkArticleEntity
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.core.room.entity.SearchArticleEntity
import com.peterchege.composenewsapp.domain.models.ArticleUI
import kotlin.random.Random

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
fun NetworkArticle.toSearchCacheEntity(): SearchArticleEntity {
    return SearchArticleEntity(
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

fun ArticleUI.toBookmarkEntity():BookmarkArticleEntity{
    return BookmarkArticleEntity(
        id = id,
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

fun SearchArticleEntity.toPresentationModel():ArticleUI{
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

fun NetworkArticle.toPresentationModel():ArticleUI{
    return ArticleUI(
        id = Random(10).nextInt(),
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