package com.na0.nayoung_code_interview.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.na0.nayoung_code_interview.Application
import com.na0.nayoung_code_interview.R
import com.na0.nayoung_code_interview.presentation.bookmark.BookMarkViewModel
import com.na0.nayoung_code_interview.presentation.ui.theme.AppTheme
import com.na0.nayoung_code_interview.presentation.ui.components.*
import com.na0.nayoung_code_interview.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment: Fragment() {
    @Inject
    lateinit var application: Application

    private val viewModel: SearchViewModel by viewModels()
    private val bookmarkViewModel: BookMarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LaunchedEffect(true) {
                    bookmarkViewModel.getAll()
                }
                val likeImages = bookmarkViewModel.allImages.value

                val images = viewModel.images.value
                val query = viewModel.query.value
                val loading = viewModel.loading.value
                var page = viewModel.page.value
                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                    darkTheme = application.isDark.value,
                ) {
                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = { viewModel.onTriggerEvent(SearchState.NewSearchState) },
                                onNavigationToBookMark = {
                                    findNavController().navigate(R.id.action_searchFragment_to_bookMarkFragment)
                                }
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        val likeImageIdList: List<String> = likeImages.map { it.id }
                        val searchImageIdList: List<String> = images.map { it.id }

                        val likeIds: List<String> = likeImageIdList.filter { it in searchImageIdList }

                        SearchView(
                            padding = it,
                            loading = loading,
                            images = images,
                            onChangeScrollPosition = viewModel::onChangeImageScrollPosition,
                            page = page,
                            onTriggerNextPage = { viewModel.onTriggerEvent(SearchState.NextPageState) },
                            onNavigateToImageDetailScreen = { unsplashResponse ->
                                val bundle = Bundle()
                                bundle.putParcelable("unsplashResponse", unsplashResponse)
                                bundle.putInt("screenType", Constants.SCREEN_TYPE_SEARCH)
                                findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
                            },
                            onSearchBookMarkClick = { unsplashResponse -> bookmarkViewModel.onSearchBookMarkClick(unsplashResponse) },
                            likeIds = likeIds
                        )
                    }
                }
            }
        }
    }
}