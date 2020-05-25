package br.com.alura.technews.di

import androidx.room.Room
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.database.NOME_BANCO_DE_DADOS
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.repository.NoticiaRepository
import org.koin.dsl.module

/**
 * Created by felipebertanha on 25/May/2020
 */
val persistenceModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }

    single<NoticiaDAO> {
        get<AppDatabase>().noticiaDAO
    }

    single<NoticiaRepository> {
        NoticiaRepository(dao = get(), webclient = get())
    }
}