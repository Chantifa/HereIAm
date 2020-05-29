package ch.ffhs.esa.hereiam.screens.entry.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.model.Entry

class EntryListAdapter : ListAdapter<Entry, EntryListAdapter.ViewHolder>(EntryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entryTitle: TextView = itemView.findViewById(R.id.entryTitle)
        private val entryContent: TextView = itemView.findViewById(R.id.entryContent)

        fun bind(
            item: Entry
        ) {
            entryTitle.text = item.entryTitle
            entryContent.text = item.entryContent
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.entry_list_item, parent, false)
                return ViewHolder(view)
            }
        }
    }
}

class EntryDiffCallback: DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem.entryUUID == newItem.entryUUID
    }

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem == newItem
    }
}