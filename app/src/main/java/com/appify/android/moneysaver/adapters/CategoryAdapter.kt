package com.appify.android.moneysaver.adapters

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.R
import com.appify.android.moneysaver.data.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class CategoryAdapter(private val categoryList : ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

        private lateinit var context: Context






        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {



            val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.category_layout, viewGroup,false)

           return ViewHolder(itemView)

         //   Toast.makeText(context,categories[0].name,Toast.LENGTH_LONG).show()

        }


        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


            val category : Category = categoryList[position]
            viewHolder.name.text = category.name
            /*viewHolder.itemImage.setImageResource(categories[i].image.toInt())

            viewHolder.itemImage.setOnClickListener{

            }*/



        }



        override fun getItemCount(): Int {
            return categoryList.size
        }
/*
     interface OnNoteListener{
        fun onNoteClick(position: Int)
    }
*/
        public class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){





          //  var itemImage: ImageView
            var name : TextView = itemView.findViewById(R.id.category_name)




        }




    }
