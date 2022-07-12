package com.particle.demo

import android.app.Application
import com.particle.base.*
import com.particle.gui.ParticleWallet
import com.particle.gui.ParticleWallet.enablePay

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ParticleNetwork.init(this, Env.DEV, EthereumChain(EthereumChainId.Kovan))
        ParticleWallet.init(this)
        ParticleNetwork.enablePay(true) //control whether to enable pay feature, default is true
    }
}
