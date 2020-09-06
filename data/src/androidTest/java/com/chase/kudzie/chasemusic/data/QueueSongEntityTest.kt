package com.chase.kudzie.chasemusic.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chase.kudzie.chasemusic.data.database.ChaseMusicDatabase
import com.chase.kudzie.chasemusic.data.database.dao.QueueDao
import com.chase.kudzie.chasemusic.data.factory.SongFactory

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.io.IOException


@RunWith(AndroidJUnit4::class)
class QueueSongEntityTest {
    private lateinit var db: ChaseMusicDatabase
    private lateinit var dao: QueueDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ChaseMusicDatabase::class.java).build()
        dao = db.queueDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun db_insertAndRetrieveQueueSongs() = runBlocking {
        val songs = SongFactory.makeFakeSongs()
        //Insert into queue
        dao.insertIntoQueue(songs)
        yield()

        val queuedSongs = dao.getQueuedSongs(songs)
        yield()

        assert(songs.size == queuedSongs.size)
    }

    @Test
    @Throws(Exception::class)
    fun db_clearsAll() = runBlocking {
        val songs = SongFactory.makeFakeSongs()

        dao.insertIntoQueue(songs)
        yield()

        dao.clearAll()

        assert(dao.getAll().isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun db_correctSongExistsInQueue() = runBlocking {
        val songs = SongFactory.makeFakeSongs()

        dao.insertIntoQueue(songs)
        yield()

        val queuedSongs = dao.getQueuedSongs(songs)
        yield()

        //I've picked a random song I know exists.
        val single = queuedSongs.first { song -> song.id == 3.toLong() }

        assert(single.artistName == "Galantis")
    }
}