package com.na0.nayoung_code_interview.presentation.detail

sealed class DetailState {
    data class GetDetailState(
        val id: String
    ): DetailState()
}