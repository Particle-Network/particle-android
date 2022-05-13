package com.particle.demo

import android.app.Application
import com.particle.network.ChainId
import com.particle.network.ChainName
import com.particle.network.Env
import com.particle.network.ParticleNetwork

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ParticleNetwork.init(this, Env.DEV, ChainName.Solana, ChainId.SolanaDevnet)
    }
}
