package com.project.segunfrancis.newsnow.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.segunfrancis.newsnow.R
import kotlinx.android.synthetic.main.fragment_web_view.*

/**
 * A simple [Fragment] subclass.
 */
class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private lateinit var tempContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.tempContext = context
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.settings.javaScriptEnabled = true

        if (arguments != null) {
            val url = arguments!!.getString("URL")
            webView.loadUrl(url)
            swipeRefreshLayout.isRefreshing = true

            swipeRefreshLayout.setOnRefreshListener {
                webView.loadUrl(url)
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                swipeRefreshLayout.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.isRefreshing = false
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.isRefreshing = false
                Toast.makeText(tempContext, "Failed to load page", Toast.LENGTH_LONG)
                    .show()
                Log.d("WebViewFragment", error.toString())
            }
        }
    }
}
