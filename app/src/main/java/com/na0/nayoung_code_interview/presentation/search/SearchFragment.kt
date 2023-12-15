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
import androidx.paging.compose.collectAsLazyPagingItems
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

                val query = viewModel.query.value
                val loading = viewModel.loading.value
                val scaffoldState = rememberScaffoldState()

                val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

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
                                onNavigationToBookMark = {
                                    findNavController().navigate(R.id.action_searchFragment_to_bookMarkFragment)
                                },
                                onSearchClick = { viewModel.searchHeroes(query = query)}
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        val likeImageIdList: List<String> = likeImages.map { it.id }

                        SearchView(
                            padding = it,
                            loading = loading,
                            images = searchedImages,
                            onNavigateToImageDetailScreen = { unsplashResponse ->
                                val bundle = Bundle()
                                bundle.putParcelable("unsplashResponse", unsplashResponse)
                                bundle.putInt("screenType", Constants.SCREEN_TYPE_SEARCH)
                                findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
                            },
                            onSearchBookMarkClick = { unsplashResponse -> bookmarkViewModel.onSearchBookMarkClick(unsplashResponse) },
                            likeIds = likeImageIdList,
                        )
                    }
                }
            }
        }
    }
}