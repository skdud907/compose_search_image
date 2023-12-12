package com.na0.nayoung_code_interview.presentation.search

sealed class SearchState {

    object NewSearchState : SearchState()

    object NextPageState : SearchState()

    // restore after process death
    object RestoreStateState: SearchState()
}