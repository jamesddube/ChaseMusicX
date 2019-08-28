package com.chase.kudzie.chasemusic.data.mapper

interface RequestMapper<in D, out E> {
    fun mapToEntity(domain: D): E
}