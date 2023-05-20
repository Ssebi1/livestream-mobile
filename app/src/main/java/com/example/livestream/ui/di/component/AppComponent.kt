package com.example.livestream.ui.di.component

import com.example.livestream.ui.Application
import com.example.livestream.ui.di.modules.ApiClientModule
import com.example.livestream.ui.di.modules.DatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * The Application component of *HILT*.
 */
@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        ApiClientModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}