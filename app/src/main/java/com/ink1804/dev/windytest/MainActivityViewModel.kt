package com.ink1804.dev.windytest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

class MainActivityViewModel : ViewModel() {
    val summFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    fun startSummation(n: Int) {
        summFlow.value = ""
        val flows = Array(n) { index ->
            flow {
                delay((index + 1) * 100L)
                emit(index + 1)
            }
        }

        flows.asFlow()
            .flattenMerge()
            .scan(0) { accumulator, value -> accumulator + value }
            .drop(1)
            .onEach { summFlow.value += "$it " }
            .launchIn(viewModelScope)

    }
}