package com.phonepe.notely.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.phonepe.notely.R
import com.phonepe.notely.databinding.ActivityHomeBinding
import com.phonepe.notely.ui.adapter.FilterAdapter
import com.phonepe.notely.ui.model.Filter
import com.phonepe.notely.ui.model.Mode
import com.phonepe.notely.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.home_toolbar.*
import com.phonepe.notely.ui.custom.SwipeController
import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.content_home.*
import android.support.v7.widget.RecyclerView
import com.phonepe.notely.ui.custom.listener.SwipeControllerActions
import android.support.v7.widget.LinearLayoutManager
import com.phonepe.notely.dao.model.Note
import com.phonepe.notely.ui.adapter.NoteListAdapter
import android.arch.lifecycle.Observer
import com.phonepe.notely.utils.start
import android.support.v7.widget.DividerItemDecoration


/**
 * Activity to display list of notes.
 */

class HomeActivity : BaseActivity(), View.OnClickListener, NoteListAdapter.NoteItemListener {


    lateinit var homeBinding: ActivityHomeBinding
    lateinit var noteListAdapter: NoteListAdapter

    var positionToBeUpdated = -1;

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab -> {
                startNoteActivity(null, Mode.NEW)
            }

            //apply filter button click listener
            R.id.apply_button -> {
                noteListAdapter.filter.filter(Filter.getCheckedItems(filter_list_view))
                closeDrawer()
            }

            //filter menu
            R.id.action_filter -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
                    closeDrawer()
                } else {
                    openDrawer()
                }
            }
        }
    }

    /**
     * start NoteActivity.
     *
     * @param note note to be viewed/edited.
     * @param mode  mode on which note should be opened.
     */
    private fun startNoteActivity(note: Note?, mode: Mode) {
        val bundle = Bundle()
        bundle.putSerializable(NoteActivity.MODE, mode)
        bundle.putParcelable(NoteActivity.NOTE, note)
        start<NoteActivity>(bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeBinding.homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setListeners()

        homeBinding.context = this

    }

    private fun setupRecyclerView() {

        val notes = mutableListOf<Note>()

        note_list_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        noteListAdapter = NoteListAdapter(notes, this)
        note_list_recycler_view.adapter = noteListAdapter

        val swipeController = SwipeController(object : SwipeControllerActions() {
            override fun onRightClicked(position: Int) {
                deleteNoteItem(position)
            }
        })

        addSwipeControllerToRecyclerViewItem(swipeController)
    }

    private fun addSwipeControllerToRecyclerViewItem(swipeController: SwipeController) {
        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(note_list_recycler_view)

        //add swipe to delete Controller button
        note_list_recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                swipeController.onDraw(c)
            }
        })

        //add divider line to the recyclerview
        val dividerItemDecoration = DividerItemDecoration(note_list_recycler_view.getContext(),
                LinearLayoutManager.VERTICAL)
        note_list_recycler_view.addItemDecoration(dividerItemDecoration)
    }

    //delete note item on swipe and click on delete button
    private fun deleteNoteItem(position: Int) {
        homeBinding.homeViewModel!!.deleteItem(noteListAdapter.notesFiltered.get(position))
        noteListAdapter.notesFiltered.minus(position)
        noteListAdapter.notifyItemRemoved(position)
        noteListAdapter.notifyItemRangeChanged(position, noteListAdapter.getItemCount())
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
        startObserver()
    }

    override fun onPause() {
        super.onPause()
        stopObserver()
    }

    //registering observer
    private fun startObserver() {
        homeBinding.homeViewModel!!.loadNotes().observe(this@HomeActivity, observer)
    }

    //remove observer
    private fun stopObserver(){
        homeBinding.homeViewModel!!.loadNotes().removeObserver(observer)
    }

    //Observer to fetch latest notes from DB
    val observer = Observer<List<Note>> { t ->
        if (t != null) {
            note_list_recycler_view.post({
                noteListAdapter.updateNoteList(t)

            })
        }
    }

    private fun setListeners() {
        fab.setOnClickListener(this)
        apply_button.setOnClickListener(this)
        action_filter.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            closeDrawer()
        } else {
            super.onBackPressed()
        }
    }
    
    private fun openDrawer() {
        drawer_layout.openDrawer(GravityCompat.END)
    }

    private fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.END)
    }

    /**
     * @param note to be favorited
     */
    override fun onFavoriteNote(note: Note) {
        homeBinding.homeViewModel!!.updateItem(note)
    }

    /**
     * @param note to be heart
     */
    override fun onHeartNote(note: Note) {
        onFavoriteNote(note)
    }

    /**
     * @param note to be viewed
     * @param position which position note has been clicked to view on NoteActivity
     */
    override fun onClickNote(note: Note, position: Int) {
        positionToBeUpdated = position
        startNoteActivity(note, Mode.VIEW)
    }

}
