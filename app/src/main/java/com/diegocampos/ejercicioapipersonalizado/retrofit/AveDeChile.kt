package com.diegocampos.ejercicioapipersonalizado.retrofit

data class AveDeChile(
    val _links: Links,
    val audio: Audio,
    val didyouknow: String,
    val dimorphism: Boolean,
    val habitat: String,
    val images: Images,
    val iucn: Iucn,
    val map: Map,
    val migration: Boolean,
    val name: Name,
    val order: String,
    val size: String,
    val sort: Int,
    val species: String,
    val uid: String
)

data class Links(
    val parent: String,
    val self: String
)

class Audio

data class Images(
    val gallery: List<Gallery>,
    val main: String
)

data class Iucn(
    val description: String,
    val title: String
)

data class Map(
    val image: String,
    val title: String
)

data class Name(
    val english: String,
    val latin: String,
    val spanish: String
)

data class Gallery(
    val url: String
)