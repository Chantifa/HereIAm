package ch.ffhs.esa.hereiam.screens.entry.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ch.ffhs.esa.hereiam.R
import kotlinx.android.synthetic.main.fragment_entry_list_all.view.*


class EntryListFragment : Fragment() {
    private lateinit var viewModel: EntryListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EntryListViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_entry_list_all, container, false)
        val collection_path = getString(R.string.firestore_collection_path)

        viewModel.getEntries(collection_path, view.entry_list)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ctx = requireContext()
        viewModel.addAdadpter(ctx)
    }


}

