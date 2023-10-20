package com.ayberk.cryptocrazy.ViewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ayberk.cryptocrazy.Model.CryptoListItem
import com.ayberk.cryptocrazy.Repository.CryptoRepository
import com.ayberk.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
):ViewModel(){

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>()  // indirdiğimiz verileri bu değişkene kaydediyoruz arama verileri için
    private var isSearchStarting = true

    init {
        loadCryptos()
    }

    fun searchCryptoList(query: String){
        val  listToSearch = if(isSearchStarting){
            cryptoList.value
        }else{
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default){
            if (query.isEmpty()){
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.currency.contains(query.trim(),ignoreCase = true)
            }
             if (isSearchStarting){
                 initialCryptoList = cryptoList.value
                 isSearchStarting = false
             }
            cryptoList.value = results
        }
    }


    fun loadCryptos(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
            when(result) {

                is Resource.Success ->{
                    val cryptoItems = result.data!!.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency,cryptoListItem.price)
                    } as List<CryptoListItem>

                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }

                is Resource.Error ->{
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> TODO()
            }

        }
    }
}