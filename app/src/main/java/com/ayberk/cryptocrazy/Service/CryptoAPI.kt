package com.ayberk.cryptocrazy.Service

import com.ayberk.cryptocrazy.Model.Crypto
import com.ayberk.cryptocrazy.Model.CryptoList
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {

    @GET("cryptolist.json")
    suspend fun getCryptoList(): CryptoList

    @GET("crypto.json")
    suspend fun getCrypto(): Crypto
}