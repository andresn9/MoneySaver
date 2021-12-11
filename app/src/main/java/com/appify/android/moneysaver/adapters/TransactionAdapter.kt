package com.appify.android.moneysaver.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.R

class TransactionAdapter: RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {


    val titles = arrayOf("bombona", "agua", "luz")
    val details = arrayOf("bombona", "agua", "luz")
    val images = arrayOf(R.drawable.ic_baseline_arrow_back,R.drawable.ic_baseline_arrow_back,R.drawable.ic_baseline_arrow_back)

    override fun onCreateViewHolder(viewGroup: ViewGroup,  i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.transaction_layout, viewGroup,false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemName.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemImage.setImageResource(images[i])


    }


    override fun getItemCount(): Int {
        return titles.size
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        var itemImage: ImageView
        var itemName : TextView
        var itemDetail : TextView


        init {
            itemImage = itemView.findViewById(R.id.category_image)
            itemName = itemView.findViewById(R.id.transaction_name)
            itemDetail = itemView.findViewById(R.id.wallet_name)
        }
    }
}