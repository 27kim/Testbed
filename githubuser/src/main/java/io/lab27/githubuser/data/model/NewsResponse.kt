package io.lab27.githubuser.data.model

data class NewsResponse (
    val status: String = "",
    val totalResults: Long = 0L,
    val articles: List<Article> = emptyList()
)

data class Article (
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String? = null
)

data class Source (
    val id: String? = null,
    val name: String
)