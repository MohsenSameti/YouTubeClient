package com.samentic.youtubeclient.features.auth.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.samentic.youtubeclient.BuildConfig
import com.samentic.youtubeclient.R
import com.samentic.youtubeclient.core.di.findAppComponent
import com.samentic.youtubeclient.core.ui.safeNavigate
import com.samentic.youtubeclient.databinding.FragmentAuthBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import net.openid.appauth.*
import javax.inject.Inject
import kotlin.properties.Delegates

class AuthFragment : Fragment(R.layout.fragment_auth) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var serviceConfig: AuthorizationServiceConfiguration

    @Inject
    lateinit var clientAuthentication: ClientAuthentication

    @Inject
    lateinit var authorizationService: AuthorizationService

    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    private var isLoading by Delegates.observable(false) { _, _, newIsLoading ->
        binding.btnAuthorize.isInvisible = newIsLoading
        binding.progress.isVisible = newIsLoading
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val resp = AuthorizationResponse.fromIntent(it.data!!)
            val ex = AuthorizationException.fromIntent(it.data)
            viewModel.updateAuthState(resp, ex)

            if (resp != null) {
                isLoading = true
                authorizationService
                    .performTokenRequest(
                        resp.createTokenExchangeRequest(),
                        clientAuthentication
                    ) { response, exp ->
                        viewModel.updateAuthState(response, exp)
                        Log.d("OAuth2", "$response")
                        Toast.makeText(requireContext(), "Yaaaaay", Toast.LENGTH_SHORT).show()
                        gotoSubscriptionListFragment()
                        isLoading = false
                    }
            } else {
                isLoading = false
            }
        }

        binding.btnAuthorize.setOnClickListener {
            isLoading = true
            val request = AuthorizationRequest.Builder(
                serviceConfig,
                BuildConfig.CLIENT_ID,
                ResponseTypeValues.CODE,
                Uri.parse("${BuildConfig.REDIRECT_URL_SCHEME}:/oauth2redirect")
            ).setScopes("https://www.googleapis.com/auth/youtube.readonly")
                .build()

            launcher.launch(authorizationService.getAuthorizationRequestIntent(request))
        }

        if (viewModel.isAuthorized()) {
            gotoSubscriptionListFragment()
        }
    }

    private fun gotoSubscriptionListFragment() {
        safeNavigate(AuthFragmentDirections.actionAuthFragmentToSubscriptionListFragment())
    }
}