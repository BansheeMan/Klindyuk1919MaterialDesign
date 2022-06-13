package com.example.klindyuk1919materialdesign230522.view_viewmodel.nasaRequestFragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.size.Scale
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentLifsBinding
import com.example.klindyuk1919materialdesign230522.utils.*
import com.example.klindyuk1919materialdesign230522.view_viewmodel.NasaRequestAppState
import com.example.klindyuk1919materialdesign230522.view_viewmodel.NasaRequestViewModel

class LifsFragment : Fragment() {

    private var _binding: FragmentLifsBinding? = null
    private val binding: FragmentLifsBinding
        get() = _binding!!

    private val viewModel: NasaRequestViewModel by lazy {
        ViewModelProvider(this).get(NasaRequestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLifsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.sendRequestLIFS()
        findWiki()
    }


    private fun findWiki() {
        binding.inputLayoutLifs.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_DOMAIN${binding.inputEditTextLifs.text.toString()}")
            })
        }
    }

    private fun renderData(appState: NasaRequestAppState) {
        when (appState) {
            is NasaRequestAppState.Error -> {
                with(binding) {
                    val trouble = appState.error.message
                    successViewLifs.visibility = View.GONE
                    error.errorView.visibility = View.VISIBLE
                    error.errorView.showSnackBar(trouble, R.string.exit) {
                        requireActivity().finish()
                    }
                }
            }
            is NasaRequestAppState.Loading -> {
                with(binding) {
                    imageViewLifs.scaleType = ImageView.ScaleType.CENTER
                    imageViewLifs.load(R.drawable.progress_animation)
                }
            }
            is NasaRequestAppState.SuccessLIFS -> {
                with(binding) {
                    error.errorView.visibility = View.GONE
                    successViewLifs.visibility = View.VISIBLE
                    imageViewLifs.scaleType = ImageView.ScaleType.FIT_CENTER
                    imageViewLifs.load(appState.lifs.url)
                    textviewLifsOne.text = appState.lifs.resource.planet
                    textviewLifsTwo.text = appState.lifs.date
                    textviewLifsThree.text = String.format(getString(R.string.coordinate_lat_lon), LIFS_DEFAULT_LAT, LIFS_DEFAULT_LON)
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LifsFragment()
    }
}
