package com.na0.nayoung_code_interview.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.na0.nayoung_code_interview.Application
import com.na0.nayoung_code_interview.R
import com.na0.nayoung_code_interview.presentation.ui.theme.AppTheme
import com.na0.nayoung_code_interview.presentation.ui.components.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var application: Application

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.images.value

                val query = viewModel.query.value

//                val selectedCategory = viewModel.selectedCategory.value

                val loading = viewModel.loading.value

                val page = viewModel.page.value

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
                                onExecuteSearch = {
//                                    if (viewModel.selectedCategory.value?.value == "Milk") {
//                                        snackbarController.getScope().launch {
//                                            snackbarController.showSnackbar(
//                                                scaffoldState = scaffoldState,
//                                                message = "Invalid category: MILK",
//                                                actionLabel = "Hide"
//                                            )
//                                        }
//                                    } else {
                                        viewModel.onTriggerEvent(SearchState.NewSearchState)
//                                    }
                                },
//                                categories = getAllFoodCategories(),
//                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = application::toggleLightTheme
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        },
                        ) {
                        SearchView(
                            padding = it,
                            loading = loading,
                            recipes = recipes,
                            onChangeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onTriggerNextPage = { viewModel.onTriggerEvent(SearchState.NextPageState) },
                            onNavigateToRecipeDetailScreen = {
                                val bundle = Bundle()
                                bundle.putString("recipeId", it)
                                findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
                            }
                        )
                    }
                }
            }
        }
    }
}