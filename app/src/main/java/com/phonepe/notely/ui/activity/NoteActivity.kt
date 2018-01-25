package com.phonepe.notely.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.phonepe.notely.R
import com.phonepe.notely.ui.model.Mode
import com.phonepe.notely.dao.model.Note
import com.phonepe.notely.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.activity_note_view.*
import java.util.*
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.phonepe.notely.utils.DateFormatter
import kotlinx.android.synthetic.main.new_or_edit_mode_layout.*
import kotlinx.android.synthetic.main.view_mode_layout.*
import android.support.design.widget.Snackbar


/*
* Activity to view, add and edit note.
* */

class NoteActivity : AppCompatActivity(), NoteViewModel.CallBack {


    var mode: Mode = Mode.NEW
    var mNote: Note? = null
    lateinit var noteViewModel : NoteViewModel


    companion object {
        val MODE: String = "MODE";
        val NOTE: String = "NOTE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_view)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        getModeAndUpdateUIData()
        handleUIOnMode()
    }

    //handle ui based on mode
    private fun handleUIOnMode() {
        if (mode == Mode.VIEW){
            view_mode_layout.visibility = View.VISIBLE
            new_or_edit_mode_layout.visibility = View.GONE
        }else{
            view_mode_layout.visibility = View.GONE
            new_or_edit_mode_layout.visibility = View.VISIBLE
        }
    }

    //show data w.r.t mode
    private fun getModeAndUpdateUIData() {
        mode = intent.getSerializableExtra(MODE) as Mode
        if (mode == Mode.EDIT || mode == Mode.VIEW) {
            mNote = intent.getParcelableExtra<Note>(NOTE)

            title_edit_text.setText(mNote!!.title)
            description_edit_text.setText(mNote!!.description)

            title_text_view.setText(mNote!!.title)
            description_text_view.setText(mNote!!.description)
            lastUpdatedAt.setText(DateFormatter.getFormattedDate(mNote!!.lastUpdated))
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.not_view_edit_activity, menu)

        toggleViewEditMode(menu)
        return true
    }

    // A menu items toggler method based on mode
    private fun toggleViewEditMode(menu: Menu?) {
        menu!!.findItem(R.id.action_edit).isVisible = !(mode == Mode.NEW || mode == Mode.EDIT)
        menu.findItem(R.id.action_undo).isVisible = mode == Mode.EDIT
        menu.findItem(R.id.action_save).isVisible = mode == Mode.NEW || mode == Mode.EDIT
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            R.id.action_edit -> {
                mode = Mode.EDIT;
                invalidateOptionsMenu()
                handleUIOnMode()
            }

            R.id.action_undo -> {
                mode = Mode.VIEW;
                invalidateOptionsMenu()
                handleUIOnMode()
            }

            R.id.action_save -> {

                if (title_edit_text.text.isEmpty() || description_edit_text.text.isEmpty()){
                    val mySnackbar = Snackbar.make(root_coordinator_layout,
                            R.string.empty_title_desc, Snackbar.LENGTH_LONG)
                    mySnackbar.show()

                }else {
                    if (mNote != null) {
                        updateNote()
                    } else {
                        addNote()
                    }
                }
            }
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    //add note
    private fun addNote() {
        val note = Note(0, title_edit_text.text.toString(), description_edit_text.text.toString(), Date().toString()
                , 0, false, false)
        noteViewModel.addNote(note, this)
    }

    //call back on note added
    override fun onAdded(note: Note) {
        finish()
    }

    //update note
    private fun updateNote() {
        mNote!!.title = title_edit_text.text.toString()
        mNote!!.description = description_edit_text.text.toString()
        noteViewModel.updateItem(mNote!!)
        finish()
    }

}
