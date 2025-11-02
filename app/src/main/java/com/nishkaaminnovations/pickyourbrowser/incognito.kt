package com.nishkaaminnovations.pickyourbrowser

import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.nishkaaminnovations.pickyourbrowser.ui.theme.PickYourBrowserTheme

class incognito : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val url=intent.getStringExtra("url")
        setContent {
            PickYourBrowserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   url?.let {
                       PrivateWebView(it,innerPadding)
                   }
                }
            }
        }
    }

    @Composable
    fun PrivateWebView(
        url: String,
       paddingValues: PaddingValues
    ) {
        var webView: WebView? by remember { mutableStateOf(null) }
        var isLoading by remember { mutableStateOf(true) }
        var loadError by remember { mutableStateOf<String?>(null) }

        // Handle back press
        BackHandler(enabled = true) {
            webView?.let { wv ->
                if (wv.canGoBack()) {
                    wv.goBack()
                } else {
                    // No pages left, exit activity
                    finish()
                }
            } ?: finish() // If webView is null, also exit
        }


        DisposableEffect(Unit) {
            onDispose {
                webView?.apply {
                    clearCache(true)
                    clearHistory()
                    clearFormData()
                    destroy()
                }
                CookieManager.getInstance().apply {
                    removeAllCookies(null)
                    flush()
                }
                webView = null
            }
        }

        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            AndroidView(
                factory = { context ->
                    WebView(context.applicationContext).apply {
                        settings.apply {
                            domStorageEnabled=false
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            databaseEnabled = true
                            cacheMode = WebSettings.LOAD_DEFAULT
                            allowFileAccess = false
                            allowContentAccess = false
                            setGeolocationEnabled(false)
                            mediaPlaybackRequiresUserGesture = true
                            userAgentString =
                                "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 Chrome/124.0.0.0 Mobile Safari/537.36"
                        }

                        val cookieManager = CookieManager.getInstance()
                        cookieManager.setAcceptCookie(true)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            cookieManager.setAcceptThirdPartyCookies(this@apply, false)
                        }
                        cookieManager.flush()

                        webViewClient = object : WebViewClient() {

                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                val url = request?.url.toString()
                                if (request?.method == "POST") {
                                    view?.loadUrl(url)
                                    return true
                                }
                                return false
                            }

                            @Suppress("OverridingDeprecatedMember")
                            override fun onReceivedError(
                                view: WebView?, errorCode: Int, description: String?, failingUrl: String?
                            ) {
                                loadError = description
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    loadError = error?.description?.toString()
                                }
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                            }
                        }

                        loadUrl(url)
                        webView = this
                    }
                },
                update = { view ->
                    if (view.url != url) view.loadUrl(url)
                },
                modifier = Modifier.fillMaxSize()
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = androidx.compose.ui.graphics.Color(0xFF4C8CFF)
                )
            }

            loadError?.let { error ->
                Text(
                    text = "Failed to load page: $error",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
