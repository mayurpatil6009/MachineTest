package com.example.machinetest

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.GridLayoutManager

import com.example.machinetest.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val list = arrayListOf<RecylerviewData>(
            RecylerviewData(R.drawable.baseline_add_to_queue_24, "Cash Withdrawal"),
            RecylerviewData(R.drawable.baseline_add_to_queue_24, "Mini Balance"),
            RecylerviewData(R.drawable.baseline_add_to_queue_24, "Balance check"),
            RecylerviewData(R.drawable.baseline_add_to_queue_24, "Aadhar pay"),
            RecylerviewData(R.drawable.baseline_add_to_queue_24, "DMT payment"),
            RecylerviewData(R.drawable.baseline_add_to_queue_24, "Loans"),
            RecylerviewData(R.drawable.baseline_add_to_queue_24, "Axi bank a/c open"),
            RecylerviewData(R.drawable.baseline_add_to_queue_24, "MATM")
        )
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerview.adapter = Adapter(list)
        binding.BtnApi.setOnClickListener {
            Retrofit()
        }
        return binding.root
    }


    private fun Retrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://supay.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(apiInterface::class.java)
        val call = apiInterface.getResponce("9876543210", "1234", "1")
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val rechargeResponse = response.body()
                    Toast.makeText(
                        requireContext(),
                        "${rechargeResponse?.MESSAGE}",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    Toast.makeText(
                        requireContext(),
                        "problem with fetching response",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
