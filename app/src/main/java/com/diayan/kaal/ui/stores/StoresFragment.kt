package com.diayan.kaal.ui.stores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.diayan.kaal.R

class StoresFragment : Fragment() {

    private lateinit var storesViewModel: StoresViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        storesViewModel =
            ViewModelProviders.of(this).get(StoresViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_stores, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        storesViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}