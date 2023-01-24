package com.samentic.youtubeclient.features.player

import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.samentic.youtubeclient.BuildConfig
import com.samentic.youtubeclient.databinding.ActivityPlayerBinding

class PlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var videoId: String

    override fun onCreate(savedInstanceBundle: Bundle?) {
        super.onCreate(savedInstanceBundle)

        // region initialize values
        val id = if (savedInstanceBundle != null) {
            savedInstanceBundle.getString(EXTRA_VIDEO_ID, null)
        } else {
            intent.getStringExtra(EXTRA_VIDEO_ID)
        }

        if (id == null) {
            Toast.makeText(this, "Invalid video", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        videoId = id
        // endregion initialize values

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.player.initialize(BuildConfig.API_KEY, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {

        if (!wasRestored) {
            player.loadVideo(videoId)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        error: YouTubeInitializationResult?
    ) {
        val requestCode = 123
        if (error?.isUserRecoverableError == true) {
            error.getErrorDialog(this, requestCode).show()
        } else {
            Toast.makeText(this, "There was an error initializing player", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        const val EXTRA_VIDEO_ID = "videoId"
    }
}