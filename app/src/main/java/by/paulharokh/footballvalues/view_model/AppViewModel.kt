package by.paulharokh.footballvalues.view_model

import androidx.lifecycle.ViewModel
import by.paulharokh.footballvalues.points_db.PointsDatabase
import by.paulharokh.footballvalues.remote_model.FootballerHeader


class ViewModelGM : ViewModel() {
    var adapterPosVM: Int = -1
    var modesScoreVM: PointsDatabase? = null
}

class ViewModelFootballer : ViewModel() {
    var footballerVM: FootballerHeader? = null
}

class ViewModelRes : ViewModel() {
    var resVM: Boolean = true
}

class ViewModelLvl : ViewModel() {
    var cLvlVM: Int = 5
}
