package com.chase.kudzie.chasemusic.injection.module

import com.chase.kudzie.chasemusic.MainActivity
import dagger.Module

@Module
abstract class UIModule{

    abstract fun contributesMainActivity(): MainActivity
}