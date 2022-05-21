package com.particle.demo

import android.app.Application
import com.particle.gui.ParticleNetworkGUI
import com.particle.gui.utils.CrashHandler
import com.particle.network.ChainId
import com.particle.network.ChainName
import com.particle.network.Env
import com.particle.network.ParticleNetwork

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CrashHandler().init(this)
//        auth service
//        ParticleNetwork.init(this, Env.DEV, ChainName.Solana, ChainId.SolanaDevnet)
        // auth + wallet service
        ParticleNetworkGUI.init(this, Env.DEV, ChainName.Solana, ChainId.SolanaDevnet)
    }
}
