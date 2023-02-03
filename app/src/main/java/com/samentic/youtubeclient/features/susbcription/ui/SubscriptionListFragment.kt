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
import com.samentic.youtubeclient.core.ui.pagination.PaginationScrollListener
import com.samentic.youtubeclient.core.ui.safeNavigate
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
            safeNavigate(
                SubscriptionListFragmentDirections.actionSubscriptionListFragmentToChannelDetailFragment(
                    channelId = subscription.resourceChannelId,
                    uploadsPlaylist = subscription.uploadPlayList.orEmpty()
                )
            )
        }

        binding.rvSubscriptions.let { rvSubscriptions ->
            rvSubscriptions.setHasFixedSize(true)
            rvSubscriptions.adapter = adapter
            rvSubscriptions.addOnScrollListener(
                object : PaginationScrollListener(10) {
                    override fun isLoading(): Boolean {
                        return viewModel.isLoadingItems() ||
                                // TODO: can we somehow move this to scrollListener?
                                adapter.currentList.lastOrNull() is SubscriptionMoreItemView
                    }

                    override fun loadMore() {
                        viewModel.fetchNextPage()
                    }
                }
            )
        }
        // endregion initRecyclerView

        // region initObservation
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            Log.d("SubscriptionTAG", "loading: $isLoading")
            binding.srlSubscriptionList.isRefreshing = isLoading
        }
        viewModel.moreLoading.observe(viewLifecycleOwner) { moreLoading ->
            binding.srlSubscriptionList.isEnabled = !moreLoading
            if (moreLoading) {
                if (adapter.currentList.isNotEmpty() &&
                    adapter.currentList.last() !is SubscriptionMoreItemView
                ) {
                    adapter.submitList(
                        buildList {
                            addAll(adapter.currentList)
                            add(SubscriptionMoreItemView())
                        }
                    )
                }
            }

        }
        viewModel.subscriptions.observe(viewLifecycleOwner) { subscriptions ->
            adapter.submitList(subscriptions)
        }
        // endregion initObservation
    }

}