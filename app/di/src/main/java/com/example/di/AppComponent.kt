package com.example.di

import com.android.build.gradle.internal.testFixtures.testFixturesClassifier
import com.sun.tools.javac.util.JCDiagnostic
import dagger.BindsInstance



@Singleton
@Component(modules = [
    DbModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    AppComponent::class
])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun viewModelFactory(): ViewModelProvider.Factory

    fun inject(activity: MainActivity)
    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailFragment)
}