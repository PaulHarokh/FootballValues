package by.paulharokh.footballvalues

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.paulharokh.footballvalues.points_db.Points
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_deal.*
import kotlinx.coroutines.*


class FragmentDeal : Fragment() {
    lateinit var navController: NavController
    lateinit var viewModelF: ViewModelF
    lateinit var viewModelRes: ViewModelRes
    lateinit var viewModelGM: ViewModelGM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelF = ViewModelProvider(activity as MainActivity).get(ViewModelF::class.java)
        viewModelRes = ViewModelProvider(activity as MainActivity).get(ViewModelRes::class.java)
        viewModelGM = ViewModelProvider(activity as MainActivity).get(ViewModelGM::class.java)
        return inflater.inflate(R.layout.fragment_deal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun urlToBitmap(fragment: Fragment, url: String, imageView: ImageView) {
            Glide.with(fragment)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageView.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }

        urlToBitmap(this, viewModelF.footballerVM!!.data.player.image, im_footballer_id)
        urlToBitmap(this, viewModelF.footballerVM!!.data.club.image, im_club_id)

        var clubName = viewModelF.footballerVM?.data?.club?.name
        if (clubName.equals("Vereinslos")) clubName = "Free Agent"

        tv_club_id.text = clubName
        tv_footballer_name_id.text = viewModelF.footballerVM?.data?.player?.name

        editText_id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                btn_accept_id.isEnabled = !s.isNullOrEmpty()
            }
        })

        navController = view.findNavController()

        btn_accept_id.setOnClickListener {

            btn_accept_id.visibility = View.INVISIBLE
            tv_curr_id.visibility = View.INVISIBLE
            val realVal = viewModelF.footballerVM!!.data.player.marketValue.value
            val editVal = editText_id.text.toString().toDouble().times(1000000)
            val cLvl = 5
            val rangeValMax = realVal * (1 + cLvl / 100)
            val rangeValMin = realVal * (1 - cLvl / 100)
            editText_id.visibility = View.INVISIBLE
            tv_salesman_id.visibility = View.INVISIBLE
            tv_agent_id.visibility = View.VISIBLE
            tv_agent_id.text = getString(R.string.agent)

            CoroutineScope(Dispatchers.Main).launch {
                dialogueDelay()

                tv_manager_id.visibility = View.VISIBLE
                viewModelRes.res = true
                if (editVal > rangeValMax) {
                    tv_manager_id.text = getString(R.string.manager_ban)
                    dialogueDelay()
                    viewModelRes.res = false
                } else {
                    tv_manager_id.text = getString(R.string.manager_ok)
                    dialogueDelay()
                    tv_salesman_id.visibility = View.VISIBLE
                    if (editVal < rangeValMin) {
                        tv_salesman_id.text = getString(R.string.salesman_ban)
                        viewModelRes.res = false
                    } else tv_salesman_id.text = getString(R.string.salesman_ok)
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    dialogueDelay()
                    fun incP(points: Points, dealRes: Boolean): Points {
                        points.gmP = points.gmP + 1
                        if (dealRes) points.gmPs = points.gmPs + 1
                        return points
                    }

                    val points = when (viewModelGM.adapterPosVM) {
                        0 -> viewModelGM.modesScoreVM!!.pointsDao().getStrP()
                        1 -> viewModelGM.modesScoreVM!!.pointsDao().getMidP()
                        2 -> viewModelGM.modesScoreVM!!.pointsDao().getDefP()
                        3 -> viewModelGM.modesScoreVM!!.pointsDao().getGkP()
                        else -> Points("0", 0, 0)
                    }

                    val newPoints = incP(points, viewModelRes.res)
                    viewModelGM.modesScoreVM!!.pointsDao().update(newPoints)
                    navController.navigate(R.id.dealResult)
                }
            }

        }

    }

    private suspend fun dialogueDelay() {
        delay(1500)
    }

}
