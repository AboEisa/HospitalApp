package com.vitatrack.hospitalsystem.models

data class ModelAllUser(
    val `data`: List<DataAll>?=null,
    val message: String,
    val status: Int
)

data class DataAll(
    val avatar: String,
    val first_name: String,
    val id: Int,
    val type: String
)