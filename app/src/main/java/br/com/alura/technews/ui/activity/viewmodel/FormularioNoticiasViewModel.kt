package br.com.alura.technews.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

/**
 * Created by felipebertanha on 25/May/2020
 */
class FormularioNoticiasViewModel(private val repository: NoticiaRepository) : ViewModel() {


    fun salva(noticia: Noticia): LiveData<Resource<Void>> {
        return repository.salvaOuEdita(noticia)
    }

    fun buscaPorId(noticiaId: Long) : LiveData<Noticia?> {
        return repository.buscaPorId(noticiaId)
    }
}

class FormularioNoticiasViewModelFactory(private val repository: NoticiaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FormularioNoticiasViewModel(repository) as T
    }

}