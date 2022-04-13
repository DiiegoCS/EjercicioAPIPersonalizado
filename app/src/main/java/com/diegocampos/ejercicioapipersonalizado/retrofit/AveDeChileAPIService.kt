package com.diegocampos.ejercicioapipersonalizado.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AveDeChileAPIService {

    @GET("{uid}")
    fun obtenerAve(@Path("uid") uid: String): Call<AveDeChile>
}