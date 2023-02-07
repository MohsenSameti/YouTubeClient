package com.samentic.youtubeclient.features.channel.data

import javax.inject.Inject

class ChannelLocalDataSource @Inject constructor(
    private val dao: ChannelDao
) {

    fun getChannel(id: String) = dao.getChannel(id)

    suspend fun insertChannels(channels: List<ChannelEntity>) {
        dao.insertChannels(channels)
    }
}