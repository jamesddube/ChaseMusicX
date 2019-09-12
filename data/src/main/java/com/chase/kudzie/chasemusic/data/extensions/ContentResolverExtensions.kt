package com.chase.kudzie.chasemusic.data.extensions


import android.content.ContentResolver
import android.database.Cursor

internal inline fun <T> ContentResolver.queryAll(
    cursor: Cursor,
    mapper: (Cursor) -> T
): List<T> {
    val result = mutableListOf<T>()
    while (cursor.moveToNext()) {
        result.add(mapper(cursor))
    }
    cursor.close()

    return result
}

internal inline fun <T> ContentResolver.queryOne(
    cursor: Cursor,
    mapper: (Cursor) -> T
): T? {
    var item: T? = null
    if (cursor.moveToFirst()) {
        item = mapper(cursor)
    }
    cursor.close()

    return item
}

internal fun ContentResolver.queryCountRow(cursor: Cursor): Int {
    val count = cursor.count
    cursor.close()
    return count
}