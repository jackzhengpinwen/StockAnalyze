package com.zpw.stockanalyze.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.zpw.stockanalyze.mvvm.model.BaseRepository

abstract class BaseViewModel<M: BaseRepository> constructor(
    protected val repository: M
) : ViewModel() {
}