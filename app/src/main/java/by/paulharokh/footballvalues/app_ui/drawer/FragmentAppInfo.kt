package by.paulharokh.footballvalues.app_ui.drawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.paulharokh.footballvalues.R
import kotlinx.android.synthetic.main.fragment_app_info.*


class FragmentAppInfo : Fragment() {

    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_app_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        tv_tranfermarkt_id.setOnClickListener {
            navController.navigate(R.id.fragmentWeb)
        }
    }

}