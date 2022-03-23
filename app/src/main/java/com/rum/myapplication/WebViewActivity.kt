package com.rum.myapplication

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rum.myapplication.databinding.ActivityWebviewBinding
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWebViewSettings()
        showWebView(
            "http://espatpharmacy.emed-healthtech.in/api/payment",//"http://www.2capsules.net/qa/api/payment"
            "1",
            "96",
            "my_remark",
            "",
            "",
            "",
            "product",
            "",
            "0",
            ""
        )
    }

    private fun setWebViewSettings() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, progress: Int) {
                binding.progressBar.progress = progress
                if (progress.equals(100)) {
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }


    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    private fun showWebView(
        url: String,
        userID: String,
        addressID: String,
        remarks: String,
        walletAmount: String,
        promocode: String,
        expectedDeliveryDate: String,
        orderType: String,
        orderID: String,
        deliveryType: String,
        pharmacyId: String
    ) {
        binding.webView.settings.javaScriptEnabled = true // enable javascript
        binding.webView.addJavascriptInterface(
            WebAppInterface(
                this
            ), "Android"
        )
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
        val mProgressBar: ProgressBar = findViewById(R.id.progress_bar)
        binding.webView.webViewClient =
            MyWebViewClient(
                mProgressBar
            )
        try {
            val postData = ("user_id=" + URLEncoder.encode(userID, "UTF-8")
                    + "&address_id=" + URLEncoder.encode(addressID, "UTF-8")
                    + "&remarks=" + URLEncoder.encode(remarks, "UTF-8")
                    + "&wallet_amount=" + URLEncoder.encode(walletAmount, "UTF-8")
                    + "&promocode=" + URLEncoder.encode(promocode, "UTF-8")
                    + "&expected_delivery_date=" + URLEncoder.encode(expectedDeliveryDate, "UTF-8")
                    + "&order_type=" + URLEncoder.encode(orderType, "UTF-8")
                    + "&order_id=" + URLEncoder.encode(orderID, "UTF-8")
                    + "&delivery_type=" + URLEncoder.encode(deliveryType, "UTF-8")
                    + "&chemist_id=" + URLEncoder.encode(pharmacyId, "UTF-8"))
            binding.webView.postUrl(url, postData.toByteArray())
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    class WebAppInterface
    /**
     * Instantiate the interface and set the context
     */ internal constructor(var mContext: Context) {
        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        fun cINETpaymentCompleted(appointmentID: String) {
            Toast.makeText(
                mContext,
                "CCAvenue Payment Completed = $appointmentID",
                Toast.LENGTH_SHORT
            ).show()
            //            paymentCompletedAndReturn(appointmentID);
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        fun cINETpaymentFailed() {
            Toast.makeText(mContext, "CCAvenue Payment Failed", Toast.LENGTH_SHORT).show()
        }
    }

    class MyWebViewClient internal constructor(private val progressBar: ProgressBar) :
        WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val uri = Uri.parse(url)
            return handleUri(uri)
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = request.url
            return handleUri(uri)
        }

        private fun handleUri(uri: Uri): Boolean {
            // Based on some condition you need to determine if you are going to load the url
            // in your web view itself or in a browser.
            // You can use `host` or `scheme` or any part of the `uri` to decide.
            try {
                Log.d("WWWWW", "uri.toString()$uri")
                Log.d("WWWWW", "uri.getHost()" + uri.host)
                Log.d("WWWWW", "uri.getScheme()" + uri.scheme)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        init {
            progressBar.visibility = View.VISIBLE
        }
    }
}