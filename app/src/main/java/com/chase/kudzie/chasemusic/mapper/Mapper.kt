package com.chase.kudzie.chasemusic.mapper

/**
 * @author Kudzai Chasinda
 */
interface Mapper<out V, in D> {
    //Implement this to take Data and map it to a view
    fun mapToView(type: D): V
}