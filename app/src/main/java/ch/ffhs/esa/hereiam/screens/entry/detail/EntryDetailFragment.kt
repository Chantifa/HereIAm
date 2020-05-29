package ch.ffhs.esa.hereiam.screens.entry.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.databinding.FragmentEntryDetailBinding

class EntryDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEntryDetailBinding.inflate(inflater)
        return binding.root
    }

}
