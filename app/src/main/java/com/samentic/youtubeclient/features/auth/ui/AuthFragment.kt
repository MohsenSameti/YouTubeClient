package com.samentic.youtubeclient.features.auth.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.di.findAppComponent
import com.samentic.youtubeclient.databinding.FragmentAuthBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import javax.inject.Inject

class AuthFragment : Fragment(R.layout.fragment_auth) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAuthorize.setOnClickListener {
            Toast.makeText(requireContext(), "${viewModel.isAuthorized()}", Toast.LENGTH_SHORT).show()
        }
    }
}