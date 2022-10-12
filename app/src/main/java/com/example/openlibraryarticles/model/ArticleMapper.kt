package com.example.openlibraryarticles.model

import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.model.response.NetworkArticle
import com.example.openlibraryarticles.utills.EntityMapper
import javax.inject.Inject

class ArticleMapper @Inject constructor() : EntityMapper<NetworkArticle, Article> {

    override fun mapFromEntity(entity: NetworkArticle): Article {
        return Article(
            title = entity.title,
            source = entity.source,
            abstract = entity.abstract,
            byline = entity.byline,
            publishedDate = entity.publishedDate,
            media = entity.media
        )

    }

    override fun mapToEntity(domainEntity: Article): NetworkArticle {
        return NetworkArticle(
            title = domainEntity.title,
            source = domainEntity.source,
            abstract = domainEntity.abstract,
            byline = domainEntity.byline,
            publishedDate = domainEntity.publishedDate,
            media = domainEntity.media
        )
    }

    fun mapFromEntityList(entities: List<NetworkArticle>): List<Article> {
        return entities.map { mapFromEntity(it) }
    }
}