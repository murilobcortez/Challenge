package com.challenge.shopping.fruits.di

import androidx.room.Room
import com.challenge.shopping.fruits.common.data.RetrofitFactory
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import com.challenge.shopping.fruits.presentation.fruitsdetail.FruitsDetailViewModel
import com.challenge.shopping.fruits.presentation.fruitslist.FruitsListViewModel
import com.challenge.shopping.fruits.data.datasource.local.FruitsLocalDatabase
import com.challenge.shopping.fruits.data.datasource.remote.FruitsRemoteDataSource
import com.challenge.shopping.fruits.data.datasource.remote.FruitsRemoteDataSourceImpl
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitAiImageGenerationApiService
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitsApiService
import com.challenge.shopping.fruits.data.repository.FruitsRepositoryImpl
import com.challenge.shopping.fruits.domain.usecase.AddFruitOnCartUseCase
import com.challenge.shopping.fruits.domain.usecase.DeleteFruitFromCartUseCase
import com.challenge.shopping.fruits.domain.usecase.GetAllFruitsUseCase
import com.challenge.shopping.fruits.domain.usecase.GetFruitAiGeneratedImageUseCase
import com.challenge.shopping.fruits.domain.usecase.GetFruitsOnCartUseCase
import com.challenge.shopping.fruits.domain.usecase.IsFruitOnCartUseCase
import com.challenge.shopping.fruits.presentation.SelectedFruitSharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private const val FRUITS_API_BASE_URL = "https://www.fruityvice.com/api/"
private const val AI_IMAGE_GENERATION_API_BASE_URL = "https://api.cloudflare.com/client/v4/"
private val fruitRetrofit = RetrofitFactory.create(FRUITS_API_BASE_URL)
private val fruitIaImageGenerationRetrofit = RetrofitFactory.create(AI_IMAGE_GENERATION_API_BASE_URL)

val dataModule = module {
    // Remote
    single { fruitRetrofit.create(FruitsApiService::class.java) }
    single { fruitIaImageGenerationRetrofit.create(FruitAiImageGenerationApiService::class.java) }
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
    single { get<FruitsLocalDatabase>().fruitsDao }
}

val domainModule = module {
    // Remote
    factory { GetAllFruitsUseCase(get()) }
    factory { GetFruitAiGeneratedImageUseCase(get()) }

    // Local
    factory { GetFruitsOnCartUseCase(get()) }
    factory { AddFruitOnCartUseCase(get()) }
    factory { DeleteFruitFromCartUseCase(get()) }
    factory { IsFruitOnCartUseCase(get()) }
}

val presentationModule = module {
    viewModel { FruitsListViewModel(get(), get(), get()) }
    viewModelOf(::FruitsDetailViewModel)
    viewModelOf(::SelectedFruitSharedViewModel)
}

val fruitsModules = module {
    includes(dataModule, domainModule, presentationModule)
}