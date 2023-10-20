package com.ayberk.cryptocrazy.ViewModel

import androidx.lifecycle.ViewModel
import com.ayberk.cryptocrazy.Model.Crypto
import com.ayberk.cryptocrazy.Repository.CryptoRepository
import com.ayberk.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    suspend fun getCrypto(id: String): Resource<Crypto> {
        return repository.getCrypto(id)
    }
}
