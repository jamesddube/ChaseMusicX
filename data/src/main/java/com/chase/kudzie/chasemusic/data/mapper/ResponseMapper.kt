package com.chase.kudzie.chasemusic.data.mapper

interface ResponseMapper<in E, out D> {
    fun mapToDomain(entity: E): D
}