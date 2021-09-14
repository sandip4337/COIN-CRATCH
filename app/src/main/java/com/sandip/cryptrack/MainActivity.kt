package com.sandip.cryptrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.sandip.cryptrack.utils.inflate
import java.io.IOException
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var recyclerhome: RecyclerView
    lateinit var layoutManager: GridLayoutManager
    lateinit var menuadaptar: homeadaptar
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout

//    private var apiUrl = String.format("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest")
//
//    private val client by lazy {
//        OkHttpClient()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar1)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "COIN'$'CRATCH"

        recyclerhome = findViewById(R.id.recyclerview)

        layoutManager = GridLayoutManager(this , 2)


        progressLayout = findViewById(R.id.progressLayout)

        progressBar = findViewById(R.id.ProgressBar)

        progressLayout.visibility = View.VISIBLE




        val queue = Volley.newRequestQueue(this)

        val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"

        if (ConnectionManager().cheakConnectivity(this)){

            val JsonObjectRequest = object : JsonObjectRequest(
                Method.GET,url,null,
                Response.Listener {

                    progressLayout.visibility = View.GONE

                    val data = it.getJSONArray("data")

                    for (i in 0 until data.length()){
                        val coinJsonObject = data.getJSONObject(i)
                        val json = coinJsonObject.getJSONObject("quote").getJSONObject("USD")
                        val coinObject = coins(
                            coinJsonObject.getString("id"),
                            coinJsonObject.getString("name"),
                            coinJsonObject.getString("symbol"),
                            json.getString("price"),
                            json.getString("percent_change_1h"),
                            json.getString("percent_change_24h"),
                            json.getString("percent_change_7d")
                        )
                        coinslist.add(coinObject)

                        menuadaptar = homeadaptar(this,coinslist)

                        recyclerhome.adapter = menuadaptar

                        recyclerhome.layoutManager = layoutManager

                        runOnUiThread {
                            menuadaptar.updateData(coinslist)
                        }

                    }


                                  },
                Response.ErrorListener {

                    /*when volley error occured*/
                    Toast.makeText(this,"VOLLEY ERROR OCCURED!!!", Toast.LENGTH_SHORT).show()
                })
            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()

                    headers["Accept"] = "application/json"

                    headers["X-CMC_PRO_API_KEY"] = "1ab51f5d-b8e6-4695-b2b8-e0d6acbdce06"

                    return headers
                }
            }
            queue.add(JsonObjectRequest)

        }
        else
        {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                this.finish()
            }
            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity(this as Activity)
            }
            dialog.create()
            dialog.show()
        }
    }

//    private fun getCoins() {
//
//        val request = Request.Builder().url(apiUrl).build()
//
//        client.newCall(request).enqueue(object : Callback, okhttp3.Callback {
//            /**
//             *  Triggered if an error occurred
//             */
//            override fun onFailure(call: Call, e: IOException) {
//                println("Failed $e")
//            }
//
//            /**
//             *  Has the results from the call made to the REST endpoint
//             */
//            override fun onResponse(call: Call, response: Response) {
//                val body = response.body()?.string()
//                println("Body: $body")
//                val gson = Gson()
//                val cryptoCoins: List<coins> = gson.fromJson(body, object : TypeToken<List<coins>>() {}.type)
//
//                /**
//                 * Because the enqueue function was used,
//                 * we must make this call to have the results returned
//                 * to the main thread and then sent
//                 * to the RecyclerView adapter
//                 */
//                runOnUiThread {
//                    menuadaptar.updateData(cryptoCoins)
//                }
//            }
//
//        })
//    }
}

