package ch.ffhs.esa.hereiam.screens.entry.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.ffhs.esa.hereiam.databinding.EntryListItemBinding
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.services.StorageService
import ch.ffhs.esa.hereiam.services.StorageServiceFirebase
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EntryListAdapter :
    ListAdapter<Entry, EntryListAdapter.ViewHolder>(EntryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: EntryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val storageService: StorageService = StorageServiceFirebase()

        fun bind(
            item: Entry
        ) {
            binding.entryTitle.text = item.entryTitle
            binding.entryContent.text = item.entryContent
            binding.metaContent.text = item.getMeta()
            binding.location.text = item.locationName
            item.imagePath?.let { path ->
                CoroutineScope(IO).launch {
                    try {
                        val uri = storageService.getUriFromPath(path)
                        Timber.e("Uri: $uri")
                        Timber.e("Uri: $uri")
                        withContext(Main) {
                            Glide.with(binding.root).load(uri).into(binding.entryPhoto)
                            binding.entryPhoto.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        Timber.e("Was not possible to load image. Reason: ${e.message}")
                        withContext(Main) {
                            binding.entryPhoto.visibility = View.GONE
                        }
                    }
                }
            }
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

class EntryDiffCallback : DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem.entryUUID == newItem.entryUUID
    }

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem == newItem
    }
}
