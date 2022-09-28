package com.peterchege.composenewsapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.composenewsapp.models.Article
import com.peterchege.composenewsapp.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class AllNewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository,

) :ViewModel() {
    private val _articles = mutableStateOf<List<Article>>(emptyList())
    val articles : State<List<Article>> = _articles

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> =_isError

    private val _errorMsg = mutableStateOf("")
    val errorMsg: State<String> =_errorMsg

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> =_isLoading

    private val _msg = mutableStateOf("")
    val msg: State<String> = _msg


    init {
        getTopHeadlines()
    }
    private fun getTopHeadlines(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = newsRepository.getTopHeadlines()
                _isLoading.value = false
                if(response.status != "ok"){
                    _isError.value = true

                }
                response.articles?.let {
                    _articles.value = it
                }
            }catch (e: HttpException){
                Log.e("HTTP ERROR",e.localizedMessage ?: "Http error")
                _isLoading.value = false
                _isError.value = true
                _errorMsg.value = e.localizedMessage?: "An unexpected error occurred"

            }catch (e: IOException){
                Log.e("IO ERROR",e.toString() )
                _isLoading.value = false
                _isError.value = true
                _errorMsg.value = "Please check your internet connection"
            }

        }

    }

}