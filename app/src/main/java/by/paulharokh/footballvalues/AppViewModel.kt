package by.paulharokh.footballvalues

import androidx.lifecycle.ViewModel
import by.paulharokh.footballvalues.points_db.PointsDatabase


class ViewModelGM : ViewModel() {
    var adapterPosVM: Int = -1
    var modesScoreVM: PointsDatabase? = null
}

class ViewModelF : ViewModel() {
    var footballerVM: FootballerHeader? = null
}

class ViewModelRes : ViewModel() {
    var res: Boolean = true
}
