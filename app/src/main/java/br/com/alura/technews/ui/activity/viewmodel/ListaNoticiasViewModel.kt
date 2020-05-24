package br.com.alura.technews.ui.activity.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository

/**
 * Created by felipebertanha on 24/May/2020
 */
class ListaNoticiasViewModel(private val repository: NoticiaRepository) : ViewModel() {


    fun buscaTodos(quandoSucesso: (List<Noticia>) -> Unit, quandoFalha: (String?) -> Unit) {
        repository.buscaTodos(quandoSucesso, quandoFalha)

    }
}