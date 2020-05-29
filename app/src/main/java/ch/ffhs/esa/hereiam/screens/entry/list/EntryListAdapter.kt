package ch.ffhs.esa.hereiam.screens.entry.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.ffhs.esa.hereiam.R

class EntryListAdapter : RecyclerView.Adapter<EntryListAdapter.ViewHolder>() {
    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entryTitle: TextView = itemView.findViewById(R.id.entryTitle)
        private val entryContent: TextView = itemView.findViewById(R.id.entryContent)

        fun bind(
            item: String
        ) {
            entryTitle.text = item
            entryContent.text = item
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