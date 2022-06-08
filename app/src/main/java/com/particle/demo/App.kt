package com.particle.demo

import android.app.Application
import com.particle.base.Env
import com.particle.base.SolanaChain
import com.particle.base.SolanaChainId
import com.particle.gui.ParticleNetworkGUI

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ParticleNetworkGUI.init(this, Env.DEV, SolanaChain(SolanaChainId.Mainnet))
    }
}
