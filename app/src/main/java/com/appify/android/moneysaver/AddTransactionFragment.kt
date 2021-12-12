package com.appify.android.moneysaver

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.appify.android.moneysaver.databinding.FragmentAddTransactionBinding
import com.appify.android.moneysaver.databinding.FragmentAddWalletBinding
import com.appify.android.moneysaver.databinding.FragmentSettingsCategoryBinding
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_IMAGEID = "imageId"
private const val ARG_PARAM2 = "param2"


private const val TAG = "TransactionFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [AddTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTransactionFragment : Fragment() {
    // TODO: Rename and change types of parameters

private var param1: Int? = null




    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_IMAGEID)
        }
        Log.d(TAG, "param: " + param1)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddTransactionBinding.inflate(inflater,container,false)
        val view = binding.root



        binding.categoryButton.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_addTransactionFragment_to_settingsCategoryFragment)
        }


            param1?.let { binding.imageTrans.setImageResource(it) }
        Log.d(TAG, "onCreateView: "+param1)


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTransactionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddTransactionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGEID, param1)
                    Log.d(TAG, "onCreate: $param1 ")
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}