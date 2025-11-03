package br.com.fooddelivery.tialudeliveryapp.data.network

import br.com.fooddelivery.tialudeliveryapp.model.Order
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderApiService {
    @GET("orders/list")
    fun getOrdersByStatus(
        @Query("status") status: String
    ): Call<List<Order>>
}