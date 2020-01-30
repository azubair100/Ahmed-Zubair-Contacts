package com.example.ahmedzubaircontacts.view.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.ahmedzubaircontacts.R
import com.example.ahmedzubaircontacts.databinding.FragmentHelpBinding


class HelpFragment : Fragment() {

    private lateinit var dataBinding: FragmentHelpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false)
        return dataBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding.unbind()
    }

}
