package com.phonepe.notely.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

import com.phonepe.notely.R
import com.phonepe.notely.dao.model.Note
import com.phonepe.notely.utils.DateFormatter

import java.util.ArrayList
import java.util.Arrays

/**
 * Created by Kumar Gaurav on 1/24/2018.
 *
 * Adapter for showing note list
 *
 */


class NoteListAdapter(public val notes: List<Note>, private val mNoteItemListener: NoteItemListener) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>(), Filterable {
    public var notesFiltered: List<Note>
    public var notesList: List<Note>
    var filtered = false

    public interface NoteItemListener {
        fun onFavoriteNote(note: Note)
        fun onHeartNote(note: Note)
        fun onClickNote(note: Note, position: Int)
    }

    public class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val description: TextView
        val created_at: TextView
        val favorite: CheckBox
        val heart: CheckBox

        init {
            title = view.findViewById(R.id.title)
            description = view.findViewById(R.id.description)
            created_at = view.findViewById(R.id.created_at)
            favorite = view.findViewById(R.id.favorite)
            heart = view.findViewById(R.id.heart)
        }
    }

    init {
        this.notesFiltered = notes
        this.notesList = notes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_list_item, parent, false)

        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesFiltered[position]
        holder.title.text = note.title
        holder.description.text = note.description
        holder.created_at.text = DateFormatter.getFormattedDate(note.lastUpdated)
        holder.heart.isChecked = note.isHearted
        holder.favorite.isChecked = note.isFavorite

        setListeners(holder, note, position)

    }

    private fun setListeners(holder: NoteViewHolder, note: Note, position: Int) {

        holder.itemView.setOnClickListener { mNoteItemListener.onClickNote(note, position) }

        holder.heart.setOnClickListener {
            note.isHearted = holder.heart.isChecked
            mNoteItemListener.onHeartNote(note)
        }

        holder.favorite.setOnClickListener {
            note.isFavorite = holder.favorite.isChecked
            mNoteItemListener.onFavoriteNote(note)
        }
    }

    override fun getItemCount(): Int {
        return notesFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    if (!filtered) {
                        notesList = notesFiltered
                    }
                    notesFiltered = notesList

                    filtered = false

                } else {

                    filtered = true

                    val filteredList = ArrayList<Note>()
                    val filters = Arrays.asList(*charString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

                    val isHeart = filters.contains("Hearted")
                    val isFavorite = filters.contains("Favorite")

                    notesList = notesFiltered
                    for (row in notesList) {
                        var shouldFilter = true

                        if (isHeart) {
                            shouldFilter = shouldFilter && row.isHearted
                        }
                        if (isFavorite) {
                            shouldFilter = shouldFilter && row.isFavorite
                        }

                        if (shouldFilter) {
                            filteredList.add(row)
                        }
                    }

                    notesFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = notesFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                notesFiltered = filterResults.values as ArrayList<Note>
                notifyDataSetChanged()
            }
        }
    }


}