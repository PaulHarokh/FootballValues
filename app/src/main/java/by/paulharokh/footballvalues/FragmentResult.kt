package by.paulharokh.footballvalues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_deal_result.*


class FragmentResult : Fragment() {

    lateinit var navController: NavController
    lateinit var viewModelF: ViewModelF
    lateinit var viewModelRes: ViewModelRes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelF = ViewModelProvider(activity as MainActivity).get(ViewModelF::class.java)
        viewModelRes = ViewModelProvider(activity as MainActivity).get(ViewModelRes::class.java)
        return inflater.inflate(R.layout.fragment_deal_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        val actionB = (activity as MainActivity).supportActionBar
        actionB?.hide()

        val realVal = viewModelF.footballerVM!!.data.player.marketValue.value
        val dealRes = viewModelRes.resVM
        if (dealRes) {
            im_res_id.setImageResource(R.drawable.draw_success)
            tv_deal_res_id.text = "Good job,\nreal value is ${realVal / 1000000} mln €"
        } else {
            im_res_id.setImageResource(R.drawable.draw_missed)
            tv_deal_res_id.text = "Deal missed,\nreal value is ${realVal / 1000000} mln €"
        }

        btn_menu_id.setOnClickListener {
            navController.navigate(R.id.mainMode)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val actionB = (activity as MainActivity).supportActionBar
        actionB?.show()
    }

}
