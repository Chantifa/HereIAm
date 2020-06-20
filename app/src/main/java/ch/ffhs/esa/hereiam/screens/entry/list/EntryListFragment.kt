package ch.ffhs.esa.hereiam.screens.entry.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentEntryListAllBinding
import ch.ffhs.esa.hereiam.util.hide
import ch.ffhs.esa.hereiam.util.show
import ch.ffhs.esa.hereiam.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!HereIAmApplication.userLoggedIn()) {
            activity?.toast(getString(R.string.please_login_first))
            findNavController().navigate(R.id.nav_profile)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.progressbar.show()
        CoroutineScope(IO).launch {
            try {
                val list = viewModel.getList()
                withContext(Main) {
                    viewModel.entries.value = list
                }
            } catch (e: Exception) {
                val msg = getString(R.string.error_while_loading_recent_entries) + getString(R.string.error_reason) + e.message
                Timber.e(msg)
                withContext(Main) {
                    activity?.toast(msg)
                }
            }
            withContext(Main) {
                binding.progressbar.hide()
            }
        }
    }
}

