package br.com.alura.technews.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

class VisualizaNoticiaViewModel(
    private val noticiaId: Long,
    private val repository: NoticiaRepository
) : ViewModel() {

    private val noticiaEncontrada = buscaPorId()

    fun buscaPorId(): LiveData<Noticia?> = repository.buscaPorId(noticiaId)


    fun remove(): LiveData<Resource<Void>> {
        return noticiaEncontrada.value?.run {
            repository.remove(this)
        } ?: MutableLiveData<Resource<Void>>().also {
            it.value = Resource(error = "Noticia nao encontrada!")
        }


    }

}

class VisualizaNoticiaViewModelFactory(
    private val noticiaId: Long,
    private val repository: NoticiaRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VisualizaNoticiaViewModel(noticiaId, repository) as T
    }

}
