package br.com.fooddelivery.tialudeliveryapp.model

data class Order(
    val id: Int,
    val customerName: String,
    val status: String,
    val total: Double,
    val date: String
)