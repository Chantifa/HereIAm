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
        holder.entryTitle.text = item
        holder.entryContent.text = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.entry_list_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val entryTitle: TextView =itemView.findViewById(R.id.entryTitle)
        val entryContent: TextView =itemView.findViewById(R.id.entryContent)
    }
}