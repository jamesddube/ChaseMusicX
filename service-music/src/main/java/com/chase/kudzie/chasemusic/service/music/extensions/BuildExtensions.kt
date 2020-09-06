package com.chase.kudzie.chasemusic.service.music.extensions

import android.os.Build


fun isNougat(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

fun isOreo(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

