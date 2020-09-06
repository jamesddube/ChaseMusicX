package com.chase.kudzie.chasemusic.domain.test.factory

interface GeneratorFactory<out T> {
    fun generateObject(): T
    fun generateList(count: Int): List<T>
}