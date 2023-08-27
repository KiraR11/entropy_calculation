package com.example.entropy_calculation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentBigramList : Fragment() {

    private  lateinit var bigramAdapter: RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_bigram_list, container, false)
        val rv = rootView.findViewById<RecyclerView>(R.id.Recycler_Bigram)
        var db = MainDb.getDb(requireContext())
        rv.layoutManager = LinearLayoutManager(context)
        rv.setHasFixedSize(true)

        var bigramList : List<Probability> = mutableListOf()

        bigramList = db.getDao().getBigramList()
Log.d("Log","кол-во символов ${bigramList.size}")
        bigramAdapter = RecyclerAdapter(bigramList)
        rv.adapter = bigramAdapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentBigramList().apply {
                arguments = Bundle().apply {
                }
            }
    }
}