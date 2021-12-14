package com.appify.android.moneysaver

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.adapters.TransactionAdapter
import com.appify.android.moneysaver.data.Category
import com.appify.android.moneysaver.data.Transaction
import com.appify.android.moneysaver.data.Wallet
import com.appify.android.moneysaver.databinding.FragmentChronologyBinding
import com.appify.android.moneysaver.interfaces.OnRecyclerItemClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChronologyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChronologyFragment : Fragment(), OnRecyclerItemClick {
    // TODO: Rename and change types of parameters

    private var _binding:FragmentChronologyBinding? = null
    private val binding get() = _binding!!


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<TransactionAdapter.ViewHolder>? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionArrayList: ArrayList<Transaction>
    private lateinit var myAdapter : TransactionAdapter
    private lateinit var db : FirebaseFirestore
    private val db2 = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChronologyBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.addTransactionButton.setOnClickListener{

            Navigation.findNavController(view).navigate(R.id.action_chronologyFragment_to_addTransactionFragment)

        }





        return view
        // Inflate the layout for this fragment



    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)



        recyclerView = binding.recyclerTransaction
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)


        transactionArrayList = arrayListOf()

        myAdapter = TransactionAdapter(transactionArrayList,this)

        recyclerView.adapter = myAdapter


        setupInitialCategories()

        EventChangeListener()

    }


    private fun EventChangeListener(){


        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("userData").document(currentuser)
            .collection("transactions").addSnapshotListener(object: EventListener<QuerySnapshot> {

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

                            transactionArrayList.add(dc.document.toObject(Transaction::class.java))
                        }
                    }

                    myAdapter.notifyDataSetChanged()



                }

            })

    }

    fun setupInitialCategories() {

        var categories : ArrayList<HashMap<String,String>> = ArrayList()

        categories.add(hashMapOf("image" to "baseline_home", "name" to "Casa", ))
        categories.add(hashMapOf("image" to "baseline_groceries", "name" to "Compra Casa", ))
        categories.add(hashMapOf("image" to "baseline_restaurant", "name" to "Restaurante", ))
        categories.add(hashMapOf("image" to "baseline_health", "name" to "Salud", ))
        categories.add(hashMapOf("image" to "baseline_transport", "name" to "Transporte", ))
        categories.add(hashMapOf("image" to "baseline_car", "name" to "Coche", ))

        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid




        for(category in categories){
            category.get("name")?.let {
                db2.collection("userData").document(currentuser).collection("categories").document(
                    it
                )
                    .set(category)
                    .addOnSuccessListener {
                        Log.d(
                            ContentValues.TAG,
                            "DocumentSnapshot successfully written!"
                        )
                    }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
            }
        }



    }



    fun deleteDialog(transaction : Transaction) {
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database
                db.collection("userData").document(currentuser).collection("transactions").whereEqualTo("note", transaction.note).get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        db.collection("userData").document(currentuser).collection("transactions").document(document.id).delete()
                        fun recreate() {
                            view?.let { Navigation.findNavController(it).navigate(R.id.chronologyFragment) }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChronologyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChronologyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun clickedCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override fun clickedWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }

    override fun clickedTransaction(transaction: Transaction) {
        deleteDialog(transaction)
    }
}