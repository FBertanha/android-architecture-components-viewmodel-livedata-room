package br.com.alura.technews.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

/**
 * Created by felipebertanha on 24/May/2020
 */
class ListaNoticiasViewModel(private val repository: NoticiaRepository) : ViewModel() {


    fun buscaTodos(): LiveData<Resource<List<Noticia>>> {
        return repository.buscaTodos()
    }
}