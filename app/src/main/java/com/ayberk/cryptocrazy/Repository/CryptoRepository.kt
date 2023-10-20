package com.ayberk.cryptocrazy.Repository

import com.ayberk.cryptocrazy.Model.Crypto
import com.ayberk.cryptocrazy.Model.CryptoList
import com.ayberk.cryptocrazy.Service.CryptoAPI

import com.ayberk.cryptocrazy.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository@Inject constructor(
    private val api: CryptoAPI
) {

    suspend fun getCryptoList(): Resource<CryptoList> {
        val response = try {
            api.getCryptoList()
        } catch(e: Exception) {
            return Resource.Error("Error.")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(id: String): Resource<Crypto> {
        val response = try {
            api.getCrypto()
        } catch(e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}