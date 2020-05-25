package br.com.alura.technews.repository

import androidx.lifecycle.MutableLiveData

/**
 * Created by felipebertanha on 24/May/2020
 */
class Resource<T>(
    val data: T?,
    val error: String? = null
)