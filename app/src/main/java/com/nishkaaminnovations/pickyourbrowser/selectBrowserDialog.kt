package com.nishkaaminnovations.pickyourbrowser

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.nishkaaminnovations.pickyourbrowser.models.pickerBrowser
import com.nishkaaminnovations.pickyourbrowser.ui.theme.PickYourBrowserTheme

class selectBrowserDialog : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make the window fully transparent, including status and navigation bars
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        enableEdgeToEdge()
        val browserList=getInstalledBrowsers(this)
        val intentData = intent?.data
        var url=""
        intentData?.let {
            url = it.toString()
            Log.d("MainActivity", "User clicked URL: $url")
        }
        setContent {
            PickYourBrowserTheme {
                Surface(Modifier.fillMaxSize(),
                    color = Color.Transparent) {
                    BottomBrowserPickerDialog(browserList,url,openLink = {
                            url,packageName->
                        openUrlInBrowser(this@selectBrowserDialog,url,packageName)
                    }, paddingValues = PaddingValues(0.dp),this@selectBrowserDialog) {
                        finish()
                    }
                }
            }
        }
    }


    @Composable
    fun BottomBrowserPickerDialog(
        browserList: List<pickerBrowser>,
        url: String,
        openLink: (String, String) -> Unit,
        paddingValues: PaddingValues,
        context: Context,
        onDismiss: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(paddingValues)
                .clickable{onDismiss()},
            contentAlignment = Alignment.BottomCenter
        ) {

            Column( modifier = Modifier
                .fillMaxWidth()
                .height(290.dp)
                .background(
                    color = Color(0xFF2C2C2C),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp) // only top corners
                )
                .padding(bottom = 5.dp))
            {

                Text(
                    text = "Open With...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )

                // Horizontal LazyRow of browsers
//                LazyRow(
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    contentPadding = PaddingValues(horizontal = 8.dp)
//                ) {
//                    items(browserList) { browser ->
//                        BrowserUI(
//                            appName = browser.appName,
//                            appIconId = browser.appIcon,
//                            url = url,
//                            appPackage = browser.appPackageName,
//                            openLink = { clickedUrl, packageName ->
//                                openLink(clickedUrl, packageName)
//                                onDismiss()
//                            }
//                        )
//                    }
//                }


                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    val fullBrowserList = browserList + listOf(
                        pickerBrowser(
                            appName = "Incognito",
                            appPackageName = "incognito",
                            appIcon = drawableToBitmap(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.incognito_svgrepo_com_1_
                                )!!
                            ).asImageBitmap()
                        )
                    )

                    items(fullBrowserList) { browser ->
                        BrowserUI(
                            appName = browser.appName,
                            appIconId = browser.appIcon,
                            url = url,
                            appPackage = browser.appPackageName,
                            openLink = { clickedUrl, packageName ->
                                if (packageName == "incognito") {
                                    // Launch app's own PrivateWebView activity
                                    val intent = Intent(context, incognito::class.java)
                                    intent.putExtra("url", clickedUrl)
                                    context.startActivity(intent)
                                } else {
                                    openLink(clickedUrl, packageName)
                                }
                                onDismiss()
                            }
                        )
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                // Cancel button
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(start = 25.dp, end = 25.dp),
                    shape = RoundedCornerShape(16.dp) ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }


    @Composable
    private fun BrowserUI(
        appName: String,
        appIconId: ImageBitmap,
        url: String,
        appPackage: String,
        openLink: (String, String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .clickable { openLink(url, appPackage) }
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon in rounded square background
            Box(
                modifier = Modifier
                    .size(55.dp) // size of the icon container
                    .background(
                        color = Color.LightGray, // background color
                        shape = RoundedCornerShape(12.dp) // rounded square
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = appIconId,
                    contentDescription = "$appName icon",
                    modifier = Modifier.size(35.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Browser name
            Text(
                text = appName,
                fontSize = 14.sp,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }


    fun getInstalledBrowsers(context: Context): List<pickerBrowser> {
        val browserList = mutableListOf<pickerBrowser>()
        val intent = Intent(Intent.ACTION_VIEW, "http://www.google.com".toUri())
        val pm: PackageManager = context.packageManager
        val flags = PackageManager.MATCH_ALL

        val resolvedList: List<ResolveInfo> = pm.queryIntentActivities(intent, flags)

        for (info in resolvedList) {
            val packageName = info.activityInfo.packageName
            // Ignore this app itself
            if (packageName == "com.nishkaaminnovations.pickyourbrowser") continue

            val appName = info.activityInfo.loadLabel(pm).toString()
            val iconDrawable = try {
                pm.getApplicationIcon(packageName)
            } catch (e: Exception) {
                ContextCompat.getDrawable(context, R.drawable.browser_general_svgrepo_com)!!
            }
            val iconBitmap = drawableToBitmap(iconDrawable).asImageBitmap()
            browserList.add(pickerBrowser(appName, packageName, iconBitmap))
            Log.d("MainActivity", "displayBrowsersDetail: $packageName, $appName")
        }
        return browserList
    }

    fun openUrlInBrowser(context: Context, url: String, packageName: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setPackage(packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("BrowserError", "Cannot open URL in $packageName: ${e.message}")

            try {
                val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                fallbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(fallbackIntent)
            } catch (ex: Exception) {
                Log.e("BrowserError", "Cannot open URL in default browser: ${ex.message}")
            }
        } catch (e: Exception) {
            Log.e("BrowserError", "Unexpected error opening URL: ${e.message}")
        }
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth.takeIf { it > 0 } ?: 1,
                drawable.intrinsicHeight.takeIf { it > 0 } ?: 1,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

}


