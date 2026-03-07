package io.github.t1geryan.penny.di.utils

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    // fun someViewModelFactory(): SomeViewModel.Factory
}
