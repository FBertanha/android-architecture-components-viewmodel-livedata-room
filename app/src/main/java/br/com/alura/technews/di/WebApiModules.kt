package br.com.alura.technews.di

import br.com.alura.technews.retrofit.AppRetrofit
import br.com.alura.technews.retrofit.service.NoticiaService
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient
import org.koin.dsl.module

/**
 * Created by felipebertanha on 25/May/2020
 */

val webApiModule = module {
    single<AppRetrofit> {
        AppRetrofit()
    }

    single<NoticiaService> {
        get<AppRetrofit>().noticiaService
    }

    single<NoticiaWebClient> {
        NoticiaWebClient(get<NoticiaService>())
    }
}