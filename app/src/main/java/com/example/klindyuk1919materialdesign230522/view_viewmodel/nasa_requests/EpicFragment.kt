package com.example.klindyuk1919materialdesign230522.view_viewmodel.nasa_requests

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
import com.example.klindyuk1919materialdesign230522.BuildConfig
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentEpicBinding
import com.example.klindyuk1919materialdesign230522.utils.WIKI_DOMAIN
import com.example.klindyuk1919materialdesign230522.utils.showSnackBar

class EpicFragment : Fragment() {

    private var _binding: FragmentEpicBinding? = null
    private val binding: FragmentEpicBinding
        get() = _binding!!

    private val viewModel: NasaRequestViewModel by lazy {
        ViewModelProvider(this).get(NasaRequestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.sendRequestEPIC()
        findWiki()
    }

    private fun renderData(appState: NasaRequestAppState) {
        when (appState) {
            is NasaRequestAppState.Error -> {
                with(binding) {
                    val trouble = appState.error.message
                    successViewEpic.visibility = View.GONE
                    error.errorView.visibility = View.VISIBLE
                    error.errorView.showSnackBar(trouble, R.string.exit) {
                        requireActivity().finish()
                    }
                }
            }
            is NasaRequestAppState.Loading -> {
                with(binding) {
                    imageViewEpic.scaleType = ImageView.ScaleType.CENTER
                    imageViewEpic.load(R.drawable.progress_animation)
                }
            }
            is NasaRequestAppState.SuccessEPIC -> {
                with(binding) {
                    error.errorView.visibility = View.GONE
                    successViewEpic.visibility = View.VISIBLE
                    imageViewEpic.scaleType = ImageView.ScaleType.FIT_CENTER
                    val strDate = appState.epic.last().date.split(" ").first()
                    val image = appState.epic.last().image
                    val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                            strDate.replace("-", "/", true) +
                            "/png/" +
                            "$image" +
                            ".png?api_key=${BuildConfig.NASA_API_KEY}"
                    imageViewEpic.load(url)
                    textviewEpicOne.text = appState.epic.last().caption
                    textviewEpicTwo.text = appState.epic.last().date
                }
            }
        }
    }

    private fun findWiki() {
        binding.inputLayoutEpic.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_DOMAIN${binding.inputEditTextEpic.text.toString()}")
            })
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = EpicFragment()
    }
}
