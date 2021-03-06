package by.paulharokh.footballvalues.app_ui.drawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import by.paulharokh.footballvalues.R
import kotlinx.android.synthetic.main.fragment_web.*


class FragmentWeb : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        web_view_id.webViewClient = WebViewClient()
        web_view_id.settings.javaScriptEnabled = true
        web_view_id.loadUrl("https://www.transfermarkt.com/")

    }
}