package com.appify.android.moneysaver.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.R
import com.appify.android.moneysaver.WalletFragment
import com.appify.android.moneysaver.interfaces.OnRecyclerItemClick
import com.appify.android.moneysaver.data.Category
import com.appify.android.moneysaver.data.Transaction
import com.appify.android.moneysaver.data.Wallet
import com.appify.android.moneysaver.databinding.FragmentWalletItemBinding
import com.google.firebase.firestore.FirebaseFirestore

class WalletAdapter(
    private val walletList: ArrayList<Wallet>,
    private val ClickHandler: OnRecyclerItemClick,

    ) : RecyclerView.Adapter<WalletAdapter.ViewHolder>(), OnRecyclerItemClick {

    private lateinit var context: Context
    private val db = FirebaseFirestore.getInstance()







    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {


        var viewHolder : ViewHolder = ViewHolder(FragmentWalletItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

        context = viewHolder.itemView.context


        return viewHolder



    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {



        val wallet : Wallet = walletList[position]
        viewHolder.name.text = wallet.name
        viewHolder.amount.text = wallet.amount
        viewHolder.image.setImageResource(R.drawable.wallet)






    }



    override fun getItemCount(): Int {
        return walletList.size
    }



    inner class ViewHolder(binding:  FragmentWalletItemBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{



        var name : TextView = binding.walletName
        var amount : TextView = binding.walletAmount
        var image : ImageView = binding.walletImage


        init{
            binding.root.setOnClickListener(this)
        }


        override fun onClick(p0: View?) {

            val currentWallet = walletList[bindingAdapterPosition]
            ClickHandler.clickedWallet(currentWallet)

        }




    }

    override fun clickedCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override fun clickedWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }

    override fun clickedTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }


}
