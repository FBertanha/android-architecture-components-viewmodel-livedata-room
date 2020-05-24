package br.com.alura.technews.ui.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.repository.NoticiaRepository

/**
 * Created by felipebertanha on 24/May/2020
 */
class ListaNoticiasViewModelFactory(private val repository : NoticiaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListaNoticiasViewModel(repository) as T
    }
}