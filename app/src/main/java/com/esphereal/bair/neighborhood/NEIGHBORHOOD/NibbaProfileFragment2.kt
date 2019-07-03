package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esphereal.bair.funloot.R

class NibbaProfileFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.nibba_fragment_example, container, false)
        return view
    }
}