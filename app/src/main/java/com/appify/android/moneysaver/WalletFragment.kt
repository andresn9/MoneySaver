package com.appify.android.moneysaver

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.adapters.WalletAdapter
import com.appify.android.moneysaver.data.Category
import com.appify.android.moneysaver.data.Transaction
import com.appify.android.moneysaver.data.Wallet
import com.appify.android.moneysaver.databinding.FragmentWalletBinding
import com.appify.android.moneysaver.interfaces.OnRecyclerItemClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import android.os.Build




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WalletFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WalletFragment : Fragment(), OnRecyclerItemClick {

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!


    private lateinit var recyclerView: RecyclerView
    private lateinit var walletArrayList: ArrayList<Wallet>
    private lateinit var myAdapter: WalletAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.createWalletButton.setOnClickListener {

            Navigation.findNavController(view)
                .navigate(R.id.action_walletFragment_to_addWalletFragment);

        }




        return view

        // Inflate the layout for this fragment

    }

    override fun clickedCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override fun clickedWallet(wallet: Wallet) {
        deleteDialog(wallet)
    }


    fun deleteDialog(wallet: Wallet) {
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database
                db.collection("userData").document(currentuser).collection("wallet").whereEqualTo("name", wallet.name).get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        db.collection("userData").document(currentuser).collection("wallet").document(document.id).delete()
                        fun recreate() {
                            view?.let { Navigation.findNavController(it).navigate(R.id.walletFragment) }
                        }
                        recreate()
                        myAdapter.notifyDataSetChanged()
                    }

                }

            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }


    override fun clickedTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }




    private fun EventChangeListener() {


        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("userData").document(currentuser)
            .collection("wallet").addSnapshotListener(object : EventListener<QuerySnapshot> {

                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {

                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }



                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {

                            walletArrayList.add(dc.document.toObject(Wallet::class.java))
                        }
                    }

                    myAdapter.notifyDataSetChanged()


                }

            })

    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)






        recyclerView = binding.walletRecycler
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)


        walletArrayList = arrayListOf()

        myAdapter = WalletAdapter(walletArrayList, this)

        recyclerView.adapter = myAdapter
        myAdapter.notifyDataSetChanged()

        EventChangeListener()


    }


}