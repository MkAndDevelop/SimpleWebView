package com.mk.develop.simplewebview

import android.app.Activity
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.mk.develop.simplewebview.webutils.WebConfig
import com.mk.develop.simplewebview.webutils.offerWebChromeClient
import com.mk.develop.simplewebview.webutils.offerWebViewClient

@Composable
fun SimpleWebView(
    activity: Activity,
    url: String,
    navigateToGameCallback: (() -> Unit)? = null,
    onPageFinishedLoggerEvent: ((userAgentString: String) -> Unit)? = null
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    WebConfig.fullScreenForWeb(activity)
    WebView(
        state = rememberWebViewState(url = url),
        client = offerWebViewClient(navigateToGameCallback, onPageFinishedLoggerEvent),
        captureBackPresses = false,
        chromeClient = offerWebChromeClient(),
        modifier = Modifier.fillMaxSize(),
        onCreated = { web ->
            webView = web
            WebConfig.webViewSettings(web)
        })
    BackHandler(enabled = true) { webView?.let { WebConfig.onBackPressedEvent(it) } }
}