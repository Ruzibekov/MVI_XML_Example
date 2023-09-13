package uz.ruzibekov.mvi_xml_example.data.api

import retrofit2.http.GET
import uz.ruzibekov.mvi_xml_example.data.model.DataModel

interface ApiService {

    @GET("users")
    suspend fun getUsers(): DataModel
}