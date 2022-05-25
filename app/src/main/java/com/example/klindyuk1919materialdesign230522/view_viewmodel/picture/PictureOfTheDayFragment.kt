package com.example.klindyuk1919materialdesign230522.view_viewmodel.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentPictureOfTheDayBinding
import com.example.klindyuk1919materialdesign230522.utils.WIKI_DOMAIN
import com.example.klindyuk1919materialdesign230522.utils.showSnackBar
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        //TODO уточнить у Андрея; можно ли передавать context по MVVM;
        viewModel.sendRequest(requireContext())
        findWiki()
        stateBottomSheetL()
    }

    private fun stateBottomSheetL() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.hackBsl.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED //берет стартовую точку от app:behavior_peekHeight в XML
        bottomSheetBehavior.addBottomSheetCallback( object : BottomSheetBehavior.BottomSheetCallback(){
            //ЗАКЛАДКИ!!!
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                /*when(newState){
                    BottomSheetBehavior.STATE_DRAGGING ->{}
                    BottomSheetBehavior.STATE_COLLAPSED -> {}
                    BottomSheetBehavior.STATE_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_SETTLING -> {}
                }*/
            }
            //ЗАКЛАДКИ!!!
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //Log.d("@@@","$slideOffset")
            }

        })
    }

    private fun findWiki() {
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_DOMAIN${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun renderData(appState: PictureOfTheDayAppState) {
        when (appState) {
            is PictureOfTheDayAppState.Error -> {
                with(binding) {
                    val trouble = appState.error.message
                    loadingView.visibility = View.GONE
                    successView.visibility = View.GONE
                    errorView.visibility = View.VISIBLE
                    errorView.showSnackBar(trouble, R.string.exit) {
                        requireActivity().finish()
                    }
                }
            }
            is PictureOfTheDayAppState.Loading -> {
                with(binding) {
                    errorView.visibility = View.GONE
                    successView.visibility = View.GONE
                    loadingView.visibility = View.VISIBLE
                }
            }
            is PictureOfTheDayAppState.Success -> {
                with(binding) {
                    errorView.visibility = View.GONE
                    loadingView.visibility = View.GONE
                    successView.visibility = View.VISIBLE
                    imageView.load(appState.pictureOfTheDayResponseData.url) {
                        placeholder(R.drawable.welcome)   //TODO взависимости от картинки placeholder меняет выводимый размер основной картинки
                        transformations(CircleCropTransformation())
                        crossfade(2000) //плавный переход, изменение непрозрачности
                    }
                    hackBsl.title.text = appState.pictureOfTheDayResponseData.title
                    hackBsl.explanation.text = appState.pictureOfTheDayResponseData.explanation

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
        fun newInstance() = PictureOfTheDayFragment()
    }
}
