package ch.ffhs.esa.hereiam.screens.entry.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ch.ffhs.esa.hereiam.databinding.FragmentEntryListAllBinding
import ch.ffhs.esa.hereiam.util.hide
import ch.ffhs.esa.hereiam.util.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EntryListFragment : Fragment() {
    private val viewModel: EntryListViewModel by viewModels()
    private lateinit var binding: FragmentEntryListAllBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryListAllBinding.inflate(inflater)
        val adapter = EntryListAdapter()
        binding.entriesList.adapter = adapter
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.progressbar.show()
        CoroutineScope(IO).launch {
            viewModel.loadList()
            withContext(Main) {
                binding.progressbar.hide()
            }
        }
    }
}

