package by.paulharokh.footballvalues

import androidx.lifecycle.ViewModel

class GMViewModel : ViewModel() {
    val modesVM = mutableListOf<GameMode>()
}

class FViewModel : ViewModel() {
    var footballerVM: FootballerHeader? = null
}

class IntViewModel : ViewModel() {
    var editVal: Double? = null
}