package com.example.musicplayer

import android.content.Context
import android.content.SharedPreferences
import android.provider.MediaStore

class MusicList(val context: Context) {
    var musicList: MutableList<Music> = mutableListOf()


    fun initMusicList() {
        val projection = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION)
        val selection = null //"${MediaStore.Audio.Media.IS_MUSIC} = 1"
        val selectionArgs = null
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                musicList.add(Music(cursor.getString(0), cursor.getLong(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getLong(5) ))
            }
        }
    }

    fun getCount(): Int {
        return musicList.size
    }
}