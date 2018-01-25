package com.phonepe.notely.dao.model

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/**
 * Created by Kumar Gaurav on 1/18/2018.
 * @Link #Note a model class for note table which will store all notes
 * @param category if 0, then poem and if 1 then story
 */
@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = "note")
data class Note(@ColumnInfo(name = "id")
                @PrimaryKey(autoGenerate = true) var id: Long,

                @ColumnInfo(name = "title")
                var title: String,

                @ColumnInfo(name = "description")
                var description: String,

                @ColumnInfo(name = "lastUpdated")
                var lastUpdated: String,

                @ColumnInfo(name = "category")
                var category: Int,

                @ColumnInfo(name = "isHearted")
                var isHearted: Boolean,

                @ColumnInfo(name = "isFavorite")
                var isFavorite: Boolean) : Parcelable