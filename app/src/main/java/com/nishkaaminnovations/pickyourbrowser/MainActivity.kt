package com.nishkaaminnovations.pickyourbrowser

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.CookieManager
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView

import android.webkit.WebResourceRequest
import android.webkit.WebResourceError
import androidx.activity.compose.BackHandler

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen {
                startActivity(Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS))
                val int= Intent(this,guideActivity::class.java)
                startActivity(int)
            }
        }
    }

    @Composable
    fun MainScreen(onClick: () -> Unit) {
        val primaryBlue = Color(0xFF4C8CFF)
        val darkerBlue = Color(0xFF5A9BFF)
        val lighterBlue = Color(0xFF5A9BFF)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF2C2C2C)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- Circular translucent background behind icon ---
            Spacer(modifier = Modifier.height(150.dp))
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(
                        color = lighterBlue.copy(alpha = 0.25f), // translucent effect
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.browser_general_svgrepo_com),
                    contentDescription = "Browser Illustration",
                    modifier = Modifier.size(80.dp),
                    colorFilter = ColorFilter.tint(darkerBlue)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Retain your freedom to choose the browser you want to surf with, at the moment.",
                color = Color(0xFFEEEEEE),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- Attractive button ---
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .height(56.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = "Set Default Browser",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }


}
