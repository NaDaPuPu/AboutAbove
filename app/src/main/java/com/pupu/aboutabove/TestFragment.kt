package com.pupu.aboutabove

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pupu.aboutabove.databinding.FragmentTestBinding

class TestFragment(private val data: Triple<String, String, String>, private val checked: Int?, private val pos: Int) : Fragment() {
    private var mBinding: FragmentTestBinding? = null
    private val binding get() = mBinding!!
    private var checkedIndex: Int = 0
    private var mOnSelectEventListener: OnSelectEventListener? = null

    interface OnSelectEventListener {
        fun onReceivedData(pos: Int, selected: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity != null && activity is OnSelectEventListener) {
            mOnSelectEventListener = activity as OnSelectEventListener
        }
    }

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

        when (checked) {
            null -> {}
            else -> binding.toggleGroupTest.check(checked)
        }

        binding.toggleGroupTest.addOnButtonCheckedListener { _, checkedId, isChecked ->
            checkedIndex = if (!isChecked) -1
            else {
                when (checkedId) {
                    binding.buttonTestA.id -> 0
                    binding.buttonTestB.id -> 1
                    else -> return@addOnButtonCheckedListener
                }
            }
            mOnSelectEventListener?.onReceivedData(pos, checkedIndex)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}