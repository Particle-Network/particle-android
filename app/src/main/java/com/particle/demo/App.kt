package com.particle.demo

import android.app.Application
import com.particle.base.*
import com.particle.gui.ParticleWallet

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ParticleNetwork.init(this, Env.DEV, EthereumChain(EthereumChainId.TestnetKovan))
        ParticleWallet.init(this)
    }
}
