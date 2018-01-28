package com.phonepe.notely.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import com.phonepe.notely.R
import com.phonepe.notely.dao.model.Note
import java.util.ArrayList
import java.util.Arrays
import android.databinding.DataBindingUtil.inflate
import com.phonepe.notely.databinding.NoteListItemBinding


/**
 * Created by Kumar Gaurav on 1/24/2018.
 *
 * Adapter for showing note list
 *
 */


class NoteListAdapter(val notes: List<Note>, private val mNoteItemListener: NoteItemListener) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>(), Filterable {
    var notesFiltered: List<Note>
    var notesList: List<Note>

    public interface NoteItemListener {
        fun onFavoriteNote(note: Note)
        fun onHeartNote(note: Note)
        fun onClickNote(note: Note, position: Int)
    }

    public class NoteViewHolder(binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var mBinding: NoteListItemBinding

        init {
           mBinding = binding
        }
    }

    init {
        this.notesFiltered = notes
        this.notesList = notes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: NoteListItemBinding = inflate(inflater, R.layout.note_list_item, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesFiltered[position]
        holder.mBinding.note = note
        setListeners(holder, note, position)

    }

    private fun setListeners(holder: NoteViewHolder, note: Note, position: Int) {

        holder.mBinding.root.setOnClickListener { mNoteItemListener.onClickNote(note, position) }

        val heartCheckBox = holder.mBinding.root.findViewById<CheckBox>(R.id.heart)
        heartCheckBox.setOnClickListener {
            note.isHearted = heartCheckBox.isChecked
            mNoteItemListener.onHeartNote(note)
        }

        val favoriteCheckBox = holder.mBinding.root.findViewById<CheckBox>(R.id.favorite)
        favoriteCheckBox.setOnClickListener {
            note.isFavorite = favoriteCheckBox.isChecked
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
                    notesFiltered = notesList

                } else {

                    val filteredList = ArrayList<Note>()
                    val filters = Arrays.asList(*charString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

                    val isHeart = filters.contains("Hearted")
                    val isFavorite = filters.contains("Favorite")

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

    fun updateNoteList(t: List<Note>) {
        this.notesList = t
        this.notesFiltered = t
        notifyDataSetChanged()
    }


}