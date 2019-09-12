package com.chase.kudzie.chasemusic.injection.module

import androidx.lifecycle.ViewModel
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {


}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)