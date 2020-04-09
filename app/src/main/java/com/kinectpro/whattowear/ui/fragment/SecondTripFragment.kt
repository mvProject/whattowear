package com.kinectpro.whattowear.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kinectpro.whattowear.databinding.SecondTripFragmentBinding

import com.kinectpro.whattowear.ui.viewmodel.SecondTripViewModel

class SecondTripFragment : Fragment() {

    private lateinit var secondViewModel: SecondTripViewModel
    private lateinit var secondTripFragmentBinding: SecondTripFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        secondTripFragmentBinding = SecondTripFragmentBinding.inflate(inflater, container, false)
        return secondTripFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        secondViewModel = ViewModelProvider(this).get(SecondTripViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
