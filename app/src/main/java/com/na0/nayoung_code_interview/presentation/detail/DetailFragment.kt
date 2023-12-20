package com.na0.nayoung_code_interview.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.na0.nayoung_code_interview.Application
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import com.na0.nayoung_code_interview.presentation.bookmark.BookMarkViewModel
import com.na0.nayoung_code_interview.presentation.ui.components.*
import com.na0.nayoung_code_interview.presentation.ui.theme.AppTheme
import com.na0.nayoung_code_interview.util.Constants.SCREEN_TYPE_BOOKMARK
import com.na0.nayoung_code_interview.util.Constants.SCREEN_TYPE_SEARCH
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

const val IMAGE_HEIGHT = 500

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment: Fragment() {
    @Inject
    lateinit var application: Application

    private val viewModel: DetailViewModel by viewModels()
    private val bookmarkViewModel: BookMarkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("screenType")?.let { screenType ->
            viewModel.onTriggerEvent(DetailState.GetScreenType(screenType))
        }

        arguments?.getParcelable<UnsplashResponse>("unsplashResponse")?.let { unsplashResponse ->
            viewModel.onTriggerEvent(DetailState.GetDetailState(unsplashResponse))
        }

        arguments?.getParcelable<LikeImageEntity>("likeImages")?.let { likeImages ->
            viewModel.onTriggerEvent(DetailState.GetLikedDetailState(likeImages))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {
                LaunchedEffect(true) {
                    bookmarkViewModel.getAll()
                }
                val likeImages = bookmarkViewModel.allImages.value

                val loading = viewModel.loading.value
                val searchDetail = viewModel.searchDetail.value
                val likedDetail = viewModel.likedDetail.value
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
                                        text = "Detail",
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
                        Box (
                            modifier = Modifier.fillMaxSize().padding(it)
                        ) {
                            if (viewModel.screenType.value == SCREEN_TYPE_SEARCH) {
                                if (loading && searchDetail == null) LoadingSearchShimmer(imageHeight = IMAGE_HEIGHT.dp)
                                else searchDetail?.let { detailData ->
                                    val likeIds: List<String> = likeImages.map { it.id }
                                    var id = ""
                                    for (likeId in likeIds) {
                                        if (likeId == detailData.id) {
                                            id = likeId
                                        }
                                    }
                                    DetailView(
                                        detail = detailData,
                                        onDetailBookMarkClick = { bookmarkViewModel.onDetailBookMarkClick(detailData) },
                                        likeId = id
                                    )
                                }
                            } else if (viewModel.screenType.value == SCREEN_TYPE_BOOKMARK) {
                                if (loading && likedDetail == null) LoadingSearchShimmer(imageHeight = IMAGE_HEIGHT.dp)
                                else likedDetail?.let { likedDetailData ->
                                    val likeIds: List<String> = likeImages.map { it.id }
                                    var id = ""
                                    for (likeId in likeIds) {
                                        if (likeId == likedDetail.id) {
                                            id = likeId
                                        }
                                    }
                                    LikeDetailView(
                                        likedDetail = likedDetailData,
                                        onDetailBookMarkClick = { bookmarkViewModel.onLikeDetailBookMarkClick(likedDetail) },
                                        likeId = id
                                    )
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                    }
                }
            }
        }
    }
}