package com.na0.nayoung_code_interview.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.na0.nayoung_code_interview.Application
import com.na0.nayoung_code_interview.R
import com.na0.nayoung_code_interview.presentation.ui.components.BookMarkView
import com.na0.nayoung_code_interview.presentation.ui.theme.AppTheme
import com.na0.nayoung_code_interview.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BookMarkFragment: Fragment() {
    @Inject
    lateinit var application: Application

    private val viewModel: BookMarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LaunchedEffect(true) {
                    viewModel.getAll()
                }
                val likeImages = viewModel.allImages.value
                val loading = viewModel.loading.value
                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                    darkTheme = application.isDark.value,
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                modifier = Modifier.fillMaxWidth(),
                                title = {
                                    Text(
                                        text = "BookMark",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = { findNavController().navigateUp() }
                                    ) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                    }
                                },
                            )
                        },
                        scaffoldState = scaffoldState,
                    ) {
                        val likeIds: List<String> = likeImages.map { it.id }
                        BookMarkView(
                            padding = it,
                            loading = loading,
                            likeImage = likeImages,
                            onNavigateToImageDetailScreen = { likeImages ->
                                val bundle = Bundle()
                                bundle.putInt("screenType", Constants.SCREEN_TYPE_BOOKMARK)
                                bundle.putParcelable("likeImages", likeImages)
                                findNavController().navigate(
                                    R.id.action_bookMarkFragment_to_detailFragment,
                                    bundle
                                )
                            },
                            onBookMarkClick = { likeImageEntity -> viewModel.onBookMarkClick(likeImageEntity) },
                            likeIds = likeIds
                        )
                    }
                }
            }
        }
    }
}