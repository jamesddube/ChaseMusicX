package com.chase.kudzie.chasemusic.domain.test.factory

import kotlin.random.Random

/**
 * Factory class for data instances
 */
object DataFactory {
    fun randomUuid(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun randomString(): String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz"
        return java.util.Random().ints(10, 0, source.length)
            .toArray()
            .map(source::get)
            .joinToString("")
    }

    fun randomInt(): Int {
        return Random(216327).nextInt()
    }

    fun randomLong(): Long {
        return Random(342L).nextLong()
    }

}
