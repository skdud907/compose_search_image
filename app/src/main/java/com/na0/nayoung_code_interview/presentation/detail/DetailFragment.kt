package com.na0.nayoung_code_interview.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.na0.nayoung_code_interview.Application
import com.na0.nayoung_code_interview.presentation.ui.components.*
import com.na0.nayoung_code_interview.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


const val IMAGE_HEIGHT = 260

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment: Fragment() {

    @Inject
    lateinit var application: Application

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("recipeId")?.let { imageId ->
            viewModel.onTriggerEvent(DetailState.GetDetailState(imageId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {

                val loading = viewModel.loading.value

                val detail = viewModel.detail.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                    darkTheme = application.isDark.value,
                ){
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        Box (
                            modifier = Modifier.fillMaxSize().padding(it)
                        ){
                            if (loading && detail == null) LoadingSearchShimmer(imageHeight = IMAGE_HEIGHT.dp)
                            else detail?.let {
                                if(it.id == "1") { // force an error to demo snackbar
                                    snackbarController.getScope().launch {
                                        snackbarController.showSnackbar(
                                            scaffoldState = scaffoldState,
                                            message = "An error occurred with this recipe",
                                            actionLabel = "Ok"
                                        )
                                    }
                                }
                                else{
                                    DetailView(
                                        detail = it,
                                    )
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading, verticalBias = 0.3f)
                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}