package com.huawei.tour.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.tour.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val jobs = mutableMapOf<String, Job>()

    protected fun <P : Any?> requestBackgroundTask(
        parameter: P,
        key: String,
        task: suspend CoroutineScope.(parameter: P) -> Unit,
    ) {
        val job = jobs[key]

        if (job == null || job.isCompleted) {
            jobs[key] = viewModelScope.launch(ioDispatcher) {
                task.invoke(this, parameter)
            }
        }
    }
}
