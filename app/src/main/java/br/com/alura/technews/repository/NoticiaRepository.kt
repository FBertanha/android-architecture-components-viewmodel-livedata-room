package br.com.alura.technews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import br.com.alura.technews.asynctask.BaseAsyncTask
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient

class NoticiaRepository(
    private val dao: NoticiaDAO,
    private val webclient: NoticiaWebClient
) {

    private val mediator = MediatorLiveData<Resource<List<Noticia>>>()

    fun buscaTodos(): LiveData<Resource<List<Noticia>>> {
        mediator.addSource(buscaInterno(), Observer { noticiasEncontradas ->
            mediator.value = Resource(data = noticiasEncontradas)
        })

        val falhasDaWebApi = MutableLiveData<Resource<List<Noticia>>>()
        mediator.addSource(falhasDaWebApi, Observer { resourceDeFalha ->
            val resourceAtual = mediator.value
            val resourceNovo: Resource<List<Noticia>> = if (resourceAtual != null) {
                Resource(data = resourceAtual.data, error = resourceDeFalha.error)
            } else {
                resourceDeFalha
            }
            mediator.value = resourceNovo
        })

        buscaNaApi(quandoFalha = {
            falhasDaWebApi.value = Resource(data = null, error = it)
        })

        return mediator
    }

    fun salvaOuEdita(noticia: Noticia): LiveData<Resource<Void>> {
        return if (noticia.id > 0) {
            edita(noticia)
        } else {
            salva(noticia)
        }
    }

    private fun salva(noticia: Noticia): MutableLiveData<Resource<Void>> {
        Log.i("NoticiaRep", "Salvando...")
        val resourceNoticia = MutableLiveData<Resource<Void>>()
        salvaNaApi(
            noticia,
            quandoSucesso = {
                resourceNoticia.value = Resource()
            },
            quandoFalha = { erro ->
                resourceNoticia.value = Resource(error = erro)
            })
        return resourceNoticia
    }

    fun remove(noticia: Noticia): LiveData<Resource<Void>> {
        val resourceLiveData = MutableLiveData<Resource<Void>>()
        removeNaApi(noticia, quandoSucesso = {
            resourceLiveData.value = Resource()
        }, quandoFalha = { erro ->
            resourceLiveData.value = Resource(error = erro)
        })

        return resourceLiveData
    }

    fun edita(noticia: Noticia): LiveData<Resource<Void>> {
        Log.i("NoticiaRep", "Editando...")
        val resourceNoticia = MutableLiveData<Resource<Void>>()
        editaNaApi(noticia, quandoSucesso = {
            resourceNoticia.value = Resource()
        }, quandoFalha = { erro ->
            resourceNoticia.value = Resource(error = erro)
        })
        return resourceNoticia
    }

    fun buscaPorId(noticiaId: Long): LiveData<Noticia?> {
        return dao.buscaPorId(noticiaId)
    }

    private fun buscaNaApi(quandoFalha: (erro: String?) -> Unit) {
        webclient.buscaTodas(
            quandoSucesso = { noticiasNovas ->
                noticiasNovas?.let {
                    salvaInterno(noticiasNovas)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun buscaInterno(): LiveData<List<Noticia>> {
        return dao.buscaTodos()
    }


    private fun salvaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.salva(
            noticia,
            quandoSucesso = {
                it?.let { noticiaSalva ->
                    salvaInterno(noticiaSalva, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun salvaInterno(noticias: List<Noticia>) {
        BaseAsyncTask(
            quandoExecuta = {
                dao.salva(noticias)
            }, quandoFinaliza = {}
        ).execute()
    }

    private fun salvaInterno(noticia: Noticia, quandoSucesso: () -> Unit) {
        BaseAsyncTask(quandoExecuta = {
            dao.salva(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()

    }

    private fun removeNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.remove(
            noticia.id,
            quandoSucesso = {
                removeInterno(noticia, quandoSucesso)
            },
            quandoFalha = quandoFalha
        )
    }


    private fun removeInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.remove(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()
    }

    private fun editaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.edita(
            noticia.id, noticia,
            quandoSucesso = { noticiaEditada ->
                noticiaEditada?.let {
                    salvaInterno(noticiaEditada, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

}
