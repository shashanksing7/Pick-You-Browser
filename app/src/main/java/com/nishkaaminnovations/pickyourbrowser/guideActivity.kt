package com.nishkaaminnovations.pickyourbrowser

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nishkaaminnovations.pickyourbrowser.ui.theme.PickYourBrowserTheme
import androidx.compose.foundation.layout.*

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.FrameLayout
import androidx.compose.foundation.clickable
import pl.droidsonroids.gif.GifImageView


class guideActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        finishActivity()
        enableEdgeToEdge()
        setContent {
            PickYourBrowserTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .clickable{finish()}
                    ,color = Color.Transparent
                )
                {
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center) {
                        GifColumnLocal()
                    }
                }
            }
        }
    }
    @Composable
    fun GifColumnLocal() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(370.dp)
                .background(
                    color = Color(0xFF2C2C2C),
                    shape = RoundedCornerShape(30.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            Text(text = "Follow these Steps:",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, bottom = 10.dp, top = 10.dp), textAlign = TextAlign.Left)
            AndroidView(
                factory = { context ->
                    GifImageView(context).apply {
                        setImageResource(R.drawable.guide)
                        layoutParams = FrameLayout.LayoutParams(290, 290)
                    }
                },
                modifier = Modifier
                    .height(290.dp)
                    .width(290.dp)
            )
        }
    }

    private fun finishActivity(){
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 5000)
    }
}







