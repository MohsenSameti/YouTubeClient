package com.samentic.youtubeclient.features.susbcription.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.di.findAppComponent
import com.samentic.youtubeclient.databinding.FragmentSubscriptionListBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import javax.inject.Inject

class SubscriptionListFragment : Fragment(R.layout.fragment_subscription_list) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binding by viewBinding(FragmentSubscriptionListBinding::bind)
    private val viewModel by viewModels<SubscriptionListViewModel> { viewModelFactory }
    private lateinit var adapter: SubscriptionListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // region initView
        binding.srlSubscriptionList.setOnRefreshListener {
            viewModel.refresh()
        }
        // endregion initView

        // region initRecyclerView
        adapter = SubscriptionListAdapter { subscription ->
            Toast.makeText(requireContext(), subscription.title, Toast.LENGTH_SHORT).show()
        }

        binding.rvSubscriptions.let { rvSubscriptions ->
            rvSubscriptions.setHasFixedSize(true)
            rvSubscriptions.adapter = adapter
        }
        // endregion initRecyclerView

        // region initObservation
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            Log.d("SubscriptionTAG", "loading: $isLoading")
            binding.srlSubscriptionList.isRefreshing = isLoading
        }
        viewModel.subscriptions.observe(viewLifecycleOwner) { subscriptions ->
            adapter.submitList(subscriptions)
        }
        // endregion initObservation
    }

}