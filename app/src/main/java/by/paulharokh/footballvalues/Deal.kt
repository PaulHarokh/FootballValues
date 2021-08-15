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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_deal.*


class Deal : Fragment() {
    lateinit var navController: NavController
    lateinit var viewModelF: FViewModel
    lateinit var viewModelVal: IntViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelF = ViewModelProvider(activity as MainActivity).get(FViewModel::class.java)

        return inflater.inflate(R.layout.fragment_deal, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
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
            navController.navigate(R.id.dealResult)
            viewModelVal = ViewModelProvider(activity as MainActivity).get(IntViewModel::class.java)
            viewModelVal.editVal = editText_id.text.toString().toDouble()


        }

    }
}
