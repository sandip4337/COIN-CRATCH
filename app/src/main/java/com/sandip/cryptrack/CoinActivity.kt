package com.sandip.cryptrack

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.File
import java.io.FileOutputStream


open class CoinActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    var coinId:String? = "100"
    private lateinit var coinsymbol: TextView
    private lateinit var coinname: TextView
    lateinit var priceUsdText: TextView
    lateinit var percentChange1hText: TextView
    lateinit var percentChange24hText: TextView
    lateinit var percentChange7day: TextView
    lateinit var sharebutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin)

        toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "COIN'$'CRATCH"



        coinsymbol = findViewById(R.id.coinSymbol)
        coinname = findViewById(R.id.coinName)
        priceUsdText = findViewById(R.id.priceUsdText)
        percentChange1hText = findViewById(R.id.percentChange1hText)
        percentChange24hText = findViewById(R.id.percentChange24hText)
        percentChange7day = findViewById(R.id.percentChange7dayText)
        sharebutton = findViewById(R.id.share)


        coinId = intent.getStringExtra("coin_id")

        coinname.text = intent.getStringExtra("coin_name")
        coinsymbol.text = intent.getStringExtra("coin_symbol")
        priceUsdText.text = intent.getStringExtra("coin_price")
        percentChange7day.text = intent.getStringExtra("coin_percent_change_7d")
        percentChange1hText.text = intent.getStringExtra("coin_percent_change_1h")
        percentChange24hText.text = intent.getStringExtra("coin_percent_change_24h")


    }

    fun share(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, " *Name*: ${coinname.text} \n *Symbol*: ${coinsymbol.text} \n *Price*: ${priceUsdText.text} \n *1hr*: ${percentChange1hText.text} \n *24hr*: ${percentChange24hText.text} \n *7d*: ${percentChange7day.text}")
        val chooser = Intent.createChooser(intent,"price is")
        startActivity(chooser)

    }

}


