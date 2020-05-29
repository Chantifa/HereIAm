package ch.ffhs.esa.hereiam.screens.entry.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.ffhs.esa.hereiam.databinding.EntryListItemBinding
import ch.ffhs.esa.hereiam.model.Entry

class EntryListAdapter : ListAdapter<Entry, EntryListAdapter.ViewHolder>(EntryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: EntryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Entry
        ) {
            binding.entryTitle.text = item.entryTitle
            binding.entryContent.text = item.entryContent
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EntryListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
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