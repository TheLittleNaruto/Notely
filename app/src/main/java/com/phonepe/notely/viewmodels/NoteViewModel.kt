package com.phonepe.notely.viewmodels

import android.app.Application
import android.os.AsyncTask
import com.phonepe.notely.dao.NoteModelDao
import com.phonepe.notely.dao.model.Note



/**
 * Created by Kumar Gaurav on 1/22/2018.
 */
class NoteViewModel constructor(application: Application): BaseViewModel(application) {

    /*
    * A callback which will be called when a note is added.
    *
    **/
    interface CallBack {
        fun onAdded(note: Note)
    }

    /*   adds a note
        @param note to be added
     */
    fun addNote(note: Note, callBack: CallBack) {
        addAsyncTask(mNoteModelDao, callBack).execute(note)
    }

    private class addAsyncTask internal constructor(private val db: NoteModelDao, val callBack: CallBack) : AsyncTask<Note, Void, Note>() {
        override fun doInBackground(vararg params: Note): Note? {
            val id = db.insertNote(params[0])
            if(id > 0){
                return db.getNoteById(id)
            }
            return null
        }

        override fun onPostExecute(result: Note?) {
            super.onPostExecute(result)
            this.callBack.onAdded(result!!)
        }
    }

}