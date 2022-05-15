package com.pupu.aboutabove

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pupu.aboutabove.databinding.FragmentTestBinding

class TestFragment(private val pos: Int) : Fragment() {
    private var mBinding: FragmentTestBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentTestBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textViewTestPos.text = "$pos"

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}