package com.samentic.youtubeclient.features.auth.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.databinding.FragmentAuthBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val binding by viewBinding(FragmentAuthBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnAuthenticate.setOnClickListener {
            Toast.makeText(requireContext(), "Clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}