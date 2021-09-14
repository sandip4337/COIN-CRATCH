package com.sandip.cryptrack

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import okio.Utf8.size
import java.util.*
import kotlin.collections.ArrayList


class homeadaptar (private val context: Context, var itemList: ArrayList<coins> ): RecyclerView.Adapter<homeadaptar.adaptarholder>(){

//    private var itemList: List<coins> = Collections.emptyList()

    fun updateData(itemList: List<coins>) {
        this.itemList = itemList as ArrayList<coins>
        notifyDataSetChanged()
    }

    class adaptarholder(view: View) : RecyclerView.ViewHolder(view)
    {
        val coinicon = view.findViewById<ImageView>(R.id.coinIcon)
        val coinname = view.findViewById<TextView>(R.id.coinName)
//        val price = view.findViewById<TextView>(R.id.priceUsdText)
        val coinsymbol = view.findViewById<TextView>(R.id.coinSymbol)
//        val persent1hr = view.findViewById<TextView>(R.id.percentChange1hText)
//        val persent24hr = view.findViewById<TextView>(R.id.percentChange24hText)
//        val persent7day = view.findViewById<TextView>(R.id.percentChange7dayText)
        val homecard = view.findViewById<CardView>(R.id.homecards)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeadaptar.adaptarholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.coin_layout_main, parent, false)
        return adaptarholder(view)
    }



    override fun onBindViewHolder(holder: adaptarholder, position: Int) {

        val coin = itemList[position]
        val imageUrl = "https://res.cloudinary.com/dxi90ksom/image/upload/"
        holder.coinname?.text = coin.name
//        holder.price?.text = coin.price_usd
        holder.coinsymbol?.text = coin.symbol
//        (coin.percent_change_1h + "%").also { holder.persent1hr.text = it }
//        (coin.percent_change_24h + "%").also { holder.persent24hr.text = it }
//        (coin.percent_change_7d + "%").also { holder.persent7day.text = it }
        Picasso.get().load(imageUrl + coin.symbol.toLowerCase(Locale.ROOT) + ".png").into(holder.coinicon)


//        holder.persent1hr.setTextColor(Color.parseColor(when {
//            coin.percent_change_1h.contains("-") -> "#ff0000"
//            else -> "#32CD32"
//        }))
//
//        holder.persent24hr.setTextColor(Color.parseColor(when {
//            coin.percent_change_24h.contains("-") -> "#ff0000"
//            else -> "#32CD32"
//        }))
//
//        holder.persent7day.setTextColor(Color.parseColor(when {
//            coin.percent_change_7d.contains("-") -> "#ff0000"
//            else -> "#32CD32"
//        }))

        holder.homecard.setOnClickListener {
            val intent = Intent(context, CoinActivity::class.java)
            intent.putExtra("coin_id", coin.id)
            intent.putExtra("coin_name", coin.name)
            intent.putExtra("coin_symbol", coin.symbol)
            intent.putExtra("coin_price",coin.price_usd)
            intent.putExtra("coin_percent_change_1h", coin.percent_change_1h)
            intent.putExtra("coin_percent_change_24h", coin.percent_change_24h)
            intent.putExtra("coin_percent_change_7d", coin.percent_change_7d)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

