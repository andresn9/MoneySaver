package com.appify.android.moneysaver

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.adapters.WalletAdapter
import com.appify.android.moneysaver.data.Wallet
import com.appify.android.moneysaver.databinding.FragmentWalletBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WalletFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WalletFragment : Fragment() {

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!


    private lateinit var recyclerView: RecyclerView
    private lateinit var walletArrayList: ArrayList<Wallet>
    private lateinit var myAdapter : WalletAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        _binding = FragmentWalletBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.createWalletButton.setOnClickListener{

                Navigation.findNavController(view).navigate(R.id.action_walletFragment_to_addWalletFragment);

        }



        return view

        // Inflate the layout for this fragment

    }



    private fun EventChangeListener(){


        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("userData").document(currentuser)
            .collection("wallet").addSnapshotListener(object: EventListener<QuerySnapshot> {

                override fun onEvent(
                    value : QuerySnapshot?,
                    error : FirebaseFirestoreException?
                ) {

                    if(error != null){
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }



                    for(dc: DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){

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
        recyclerView.layoutManager= LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)


        walletArrayList = arrayListOf()

        myAdapter = WalletAdapter(walletArrayList, this)

        recyclerView.adapter= myAdapter

        EventChangeListener()



    }


}