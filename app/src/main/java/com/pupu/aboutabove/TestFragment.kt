package com.pupu.aboutabove

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pupu.aboutabove.databinding.FragmentTestBinding

class TestFragment(private val data: Triple<String, String, String>) : Fragment() {
    private var mBinding: FragmentTestBinding? = null
    private val binding get() = mBinding!!
    public var checkedIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentTestBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textViewTestQuestion.text = data.first
        binding.buttonTestA.text = data.second
        binding.buttonTestB.text = data.third

        binding.toggleGroupTest.addOnButtonCheckedListener {group, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            checkedIndex = when (checkedId) {
                binding.buttonTestA.id -> 0
                binding.buttonTestB.id -> 1
                else -> return@addOnButtonCheckedListener
            }

            Log.d("test", checkedIndex.toString())
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}