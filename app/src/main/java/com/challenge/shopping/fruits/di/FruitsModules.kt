package com.challenge.shopping.fruits.di

import androidx.room.Room
import com.challenge.shopping.fruits.common.data.AppConstants.FRUIT_IMAGE_API_BASE_URL
import com.challenge.shopping.fruits.common.data.AppConstants.FRUITS_API_BASE_URL
import com.challenge.shopping.fruits.common.data.RetrofitFactory
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import com.challenge.shopping.fruits.presentation.fruitsdetail.FruitsDetailViewModel
import com.challenge.shopping.fruits.presentation.fruitslist.FruitsListViewModel
import com.challenge.shopping.fruits.data.datasource.local.FruitsLocalDatabase
import com.challenge.shopping.fruits.data.datasource.remote.FruitsRemoteDataSource
import com.challenge.shopping.fruits.data.datasource.remote.FruitsRemoteDataSourceImpl
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitImageApiService
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitsApiService
import com.challenge.shopping.fruits.data.repository.FruitsRepositoryImpl
import com.challenge.shopping.fruits.domain.usecase.AddFruitOnCartUseCase
import com.challenge.shopping.fruits.domain.usecase.DeleteFruitFromCartUseCase
import com.challenge.shopping.fruits.domain.usecase.GetAllFruitsUseCase
import com.challenge.shopping.fruits.domain.usecase.GetFruitImageUseCase
import com.challenge.shopping.fruits.domain.usecase.GetFruitsOnCartUseCase
import com.challenge.shopping.fruits.domain.usecase.IsFruitOnCartUseCase
import com.challenge.shopping.fruits.presentation.SelectedFruitSharedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

private fun provideRetrofit(baseUrl: String) = RetrofitFactory.create(baseUrl)

val dataModule = module {
    // Remote
    single(named("FruitsApi")) { provideRetrofit(FRUITS_API_BASE_URL) }
    single { get<retrofit2.Retrofit>(named("FruitsApi")).create(FruitsApiService::class.java) }

    single(named("FruitImageApi")) { provideRetrofit(FRUIT_IMAGE_API_BASE_URL) }
    single { get<retrofit2.Retrofit>(named("FruitImageApi")).create(FruitImageApiService::class.java) }

    single<FruitsRemoteDataSource> { FruitsRemoteDataSourceImpl(get(), get()) }
    single<FruitsRepository> { FruitsRepositoryImpl(get(), get()) }

    // Local
    single {
        Room.databaseBuilder(
            get(),
            FruitsLocalDatabase::class.java,
            FruitsLocalDatabase.DB_NAME
        ).build()
    }
    single { get<FruitsLocalDatabase>().fruitsDao() }
}

val domainModule = module {
    // Remote
    factory { GetAllFruitsUseCase(get()) }
    factory { GetFruitImageUseCase(get()) }

    // Local
    factory { GetFruitsOnCartUseCase(get()) }
    factory { AddFruitOnCartUseCase(get()) }
    factory { DeleteFruitFromCartUseCase(get()) }
    factory { IsFruitOnCartUseCase(get()) }
}

val presentationModule = module {
    single<CoroutineDispatcher> { Dispatchers.IO }

    viewModelOf(::FruitsListViewModel)
    viewModelOf(::FruitsDetailViewModel)
    viewModelOf(::SelectedFruitSharedViewModel)
}

val fruitsModules = module {
    includes(dataModule, domainModule, presentationModule)
}