package by.paulharokh.footballvalues

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main_mode.*

class ModeMenu : Fragment() {
    lateinit var navController: NavController
    lateinit var viewModel: gmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as MainActivity).get(gmViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        rv_myList_id.adapter = GameModeAdapter(viewModel, this)
        rv_myList_id.layoutManager = LinearLayoutManager(context)
}

    fun startGame(adapterPosition: Int) {
        navController.navigate(R.id.deal)
    }

}