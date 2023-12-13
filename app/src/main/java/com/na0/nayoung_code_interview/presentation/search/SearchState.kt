package com.na0.nayoung_code_interview.presentation.search

sealed class SearchState {
    object NewSearchState: SearchState()
    object NextPageState: SearchState()
    object RestoreStateState: SearchState()
}