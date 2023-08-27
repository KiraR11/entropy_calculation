package com.example.entropy_calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentCharList : Fragment() {

private  lateinit var charAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val db : MainDb = MainDb.getDb(requireContext())
        val rootView = inflater.inflate(R.layout.fragment_char_list, container, false)
        val rv = rootView.findViewById<RecyclerView>(R.id.Recycler_Char)
        rv.layoutManager = LinearLayoutManager(context)
        rv.setHasFixedSize(true)
        var charList:List<Probability> = mutableListOf()
        charList = db.getDao().getCharList()

        charAdapter = RecyclerAdapter(charList)
        rv.adapter = charAdapter

        return rootView
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentCharList().apply {
                arguments = Bundle().apply {
                }
            }
    }
}