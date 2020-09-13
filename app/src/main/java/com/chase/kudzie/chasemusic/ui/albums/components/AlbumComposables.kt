package com.chase.kudzie.chasemusic.ui.albums.components

import android.net.Uri
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import coil.request.ImageRequest
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.util.getAlbumArtUri
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ArtworkComponent(
    modifier: Modifier = Modifier,
    imageComposable: @Composable () -> Unit
) {
    Card(
        modifier.clip(RoundedCornerShape(4.dp)),
        elevation = 8.dp
    ) {
        imageComposable()
    }
}

@Composable
fun AlbumItem(
    album: Album,
    modifier: Modifier = Modifier
) {
    MediaItem(
        title = album.title,
        subtitle = album.artistName,
        uri = getAlbumArtUri(album.id),
        modifier = modifier
    )
}

@Composable
fun MediaItem(
    title: String,
    subtitle: String,
    uri: Uri,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.clickable(onClick = {
            //TODO add click action
        })
    ) {
        ArtworkComponent(modifier) {
            CoilImage(
                request = ImageRequest.Builder(ContextAmbient.current)
                    .data(uri)
                    .error(R.drawable.ic_albums)
                    .placeholder(R.drawable.ic_albums)
                    .build(),
                modifier = Modifier.aspectRatio(1f)
            )

        }
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            modifier = Modifier
                .padding(4.dp)
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.overline,
            maxLines = 1,
            modifier = Modifier
                .padding(
                    start = 4.dp,
                    end = 4.dp,
                    bottom = 8.dp
                )
        )
    }
}

@Preview
@Composable
fun PreviewMediaItem() {
    val album = Album(
        id = 30,
        artistId = -1,
        artistName = "Bastille",
        songCount = 20,
        title = "Wild World",
        year = 2015
    )
    MaterialTheme {
        AlbumItem(album = album)
    }
}


