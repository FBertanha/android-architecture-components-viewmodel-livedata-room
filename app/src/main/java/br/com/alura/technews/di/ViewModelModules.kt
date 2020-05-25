package br.com.alura.technews.di


import br.com.alura.technews.ui.activity.viewmodel.FormularioNoticiasViewModel
import br.com.alura.technews.ui.activity.viewmodel.ListaNoticiasViewModel
import br.com.alura.technews.ui.activity.viewmodel.VisualizaNoticiaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by felipebertanha on 25/May/2020
 */

val viewModelModule = module {
    viewModel<ListaNoticiasViewModel> {
        ListaNoticiasViewModel(get())
    }

    viewModel<FormularioNoticiasViewModel> {
        FormularioNoticiasViewModel(get())
    }

    viewModel<VisualizaNoticiaViewModel> { (noticiaId: Long) ->
        VisualizaNoticiaViewModel(noticiaId = noticiaId, repository = get())
    }
}