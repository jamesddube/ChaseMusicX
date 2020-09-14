package com.chase.kudzie.chasemusic.util

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation.*
import android.graphics.drawable.GradientDrawable.RECTANGLE
import android.view.View
import androidx.palette.graphics.Palette


fun setGradientOnView(background: View, imageBitmap: Bitmap) {
    Palette.from(imageBitmap).generate { palette ->
        palette?.let {
            background.background =  makeGradientDrawable(
                extractTopColor(palette), extractMiddleColor(palette),
                extractBottomColor(palette)
            )
        }
    }
}

private fun makeGradientDrawable(top: Int, center: Int, bottom: Int): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.orientation = TOP_BOTTOM
    gradientDrawable.shape = RECTANGLE
    gradientDrawable.colors = intArrayOf(top, center, bottom)
    return gradientDrawable
}

private fun extractTopColor(palette: Palette): Int {
    return if (palette.mutedSwatch != null || palette.vibrantSwatch != null) {
        if (palette.mutedSwatch != null)
            palette.mutedSwatch!!.rgb
        else
            palette.vibrantSwatch!!.rgb
    } else {
        Color.RED
    }

}

private fun extractMiddleColor(palette: Palette): Int {
    return if (palette.lightMutedSwatch != null || palette.lightVibrantSwatch != null) {
        if (palette.lightMutedSwatch != null)
            palette.lightMutedSwatch!!.rgb
        else
            palette.lightVibrantSwatch!!.rgb
    } else {
        Color.GREEN
    }

}

private fun extractBottomColor(palette: Palette): Int {
    return if (palette.darkMutedSwatch != null || palette.darkVibrantSwatch != null) {
        if (palette.darkMutedSwatch != null)
            palette.darkMutedSwatch!!.rgb
        else
            palette.darkVibrantSwatch!!.rgb
    } else {
        Color.BLUE
    }
}