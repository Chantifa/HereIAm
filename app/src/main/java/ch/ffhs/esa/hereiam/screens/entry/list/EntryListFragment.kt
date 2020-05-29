package ch.ffhs.esa.hereiam.screens.entry.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ch.ffhs.esa.hereiam.databinding.FragmentEntryListAllBinding


class EntryListFragment : Fragment() {
    private val viewModel: EntryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEntryListAllBinding.inflate(inflater)
        val adapter = EntryListAdapter()
        binding.entriesList.adapter = adapter
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

//        val collectionPath = getString(R.string.firestore_collection_path)

//        viewModel.getEntries(collectionPath, binding.entryList)

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val ctx = requireContext()
//        viewModel.addAdadpter(ctx)
//    }
}

