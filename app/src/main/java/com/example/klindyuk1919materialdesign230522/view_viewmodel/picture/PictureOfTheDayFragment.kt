package com.example.klindyuk1919materialdesign230522.view_viewmodel.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentPictureOfTheDayBinding
import com.example.klindyuk1919materialdesign230522.utils.*
import com.example.klindyuk1919materialdesign230522.view_viewmodel.SettingsFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.main_activity.MainActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding!!
    private var isMain = true


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
        viewModel.sendRequest(TODAY)
        findWiki()
        stateBottomSheetBehavior()
        setActionBar()
        switchFAB()
        switchChipGroup()
    }

    private fun switchChipGroup() {
        binding.chipGroup.setOnCheckedChangeListener { group, position ->
            val chip = group.findViewById<Chip>(position)
            when (chip?.tag) {
                "chip1" -> {
                    viewModel.sendRequest(TODAY)
                }
                "chip2" -> {
                    viewModel.sendRequest(YESTERDAY)
                }
                "chip3" -> {
                    viewModel.sendRequest(TWO_DAYS_AGO)
                }
            }
        }
    }

    private fun switchFAB() {
        binding.fab.setOnClickListener {
            with(binding) {
                if (isMain) {
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_arrow_back
                        )
                    )
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_2)
                } else {
                    bottomAppBar.navigationIcon = (ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_hamburger_menu_bottom_bar
                    ))
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_plus_fab
                        )
                    )
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
                isMain = !isMain
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                Log.d("@@@", "app_bar_fav")
            }
            R.id.app_bar_settings -> {
                Log.d("@@@", "app_bar_settings")
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack("").replace(R.id.container, SettingsFragment.newInstance())
                    .commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
            R.id.app_bar_send -> {
                Log.d("@@@", "app_bar_send")
            }
            R.id.app_bar_content_paste -> {
                Log.d("@@@", "app_bar_content_paste")
            }
            R.id.app_bar_clear -> {
                Log.d("@@@", "app_bar_clear")
            }
            R.id.app_bar_archive -> {
                Log.d("@@@", "app_bar_archive")
            }
            R.id.app_bar_coronavirus -> {
                Log.d("@@@", "app_bar_coronavirus")
            }
            R.id.app_bar_downhill_skiing -> {
                Log.d("@@@", "app_bar_downhill_skiing")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }

    private fun stateBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.hackBsl.bottomSheetContainer)
        bottomSheetBehavior.state =
            BottomSheetBehavior.STATE_COLLAPSED //берет стартовую точку от app:behavior_peekHeight в XML
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
                    successView.visibility = View.GONE
                    error.errorView.visibility = View.VISIBLE
                    error.errorView.showSnackBar(trouble, R.string.exit) {
                        requireActivity().finish()
                    }
                }
            }
            is PictureOfTheDayAppState.Loading -> {
                with(binding) {
                    imageView.scaleType = ImageView.ScaleType.CENTER
                    imageView.load(R.drawable.progress_animation)
                }
            }
            is PictureOfTheDayAppState.Success -> {
                with(binding) {
                    error.errorView.visibility = View.GONE
                    successView.visibility = View.VISIBLE
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    imageView.load(appState.pictureOfTheDayResponseData.url) {
                        placeholder(R.drawable.hello)
                        transformations(CircleCropTransformation())
                        crossfade(2000) //плавный переход, изменение непрозрачности
                        scale(Scale.FILL) //заполняет картинку на весь ImageView, решает проблему c crossfade()
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
