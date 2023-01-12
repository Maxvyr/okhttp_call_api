package xyz.pandamy.okhttp_kotlon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import okhttp3.*
import xyz.pandamy.okhttp_kotlon.ui.theme.OkhttpkotlonTheme
import java.io.IOException


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OkhttpkotlonTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    SimpleCallApi()
                }
            }
        }
    }
}

@Composable
fun SimpleCallApi() {
    var body by remember {  mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Res $body!")
        OutlinedButton(onClick = {
            val urlTest = "https://pokeapi.co/api/v2/pokemon?limit=10&offset=0"
                val client = OkHttpClient()
                val request = Request.Builder().url(urlTest).build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.d("Response MainActivity", "onResponse: receive response from server")
                        response.use {
                            if(response.isSuccessful) {
//                                Log.d("Response server", response.code.toString())
                                Log.d("Response server", response.message)
                                //Be careful body can be consume only once do do print and write on value
                                body = response.body?.string()?:"receive a NULL value"
                            } else {
                                Log.e("Response MainActivity", "onResponse: Error call API" )
                            }
                        }

                    }
                })
        }) {
            Text(text = "call api")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OkhttpkotlonTheme {
        SimpleCallApi()
    }
}