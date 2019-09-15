package com.chase.kudzie.chasemusic.domain.test.factory

import kotlin.random.Random


/**
 * Factory class for data instances
 */
class DataFactory {
    companion object Factory {

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomInt(): Int {
            return Random(216327).nextInt()
        }

        fun randomLong(): Long {
            return Random(342L).nextLong()
        }
    }
}
