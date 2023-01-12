package com.particle.demo

import android.app.Application
import android.webkit.WebView
import com.connect.common.model.DAppMetadata
import com.evm.adapter.EVMConnectAdapter
import com.minijoy.demo.BuildConfig
import com.particle.base.*
import com.particle.browser.ParticleNetworkAuthTiramisu.setBrowserHeightPercent
import com.particle.connect.ParticleConnect
import com.particle.connect.ParticleConnectAdapter
import com.particle.gui.ParticleWallet
import com.particle.network.ParticleNetworkAuth.setSecurityAccountConfig
import com.particle.network.service.model.SecurityAccountConfig
import com.phantom.adapter.PhantomConnectAdapter
import com.solana.adapter.SolanaConnectAdapter
import com.wallet.connect.adapter.MetaMaskConnectAdapter
import com.wallet.connect.adapter.RainbowConnectAdapter
import com.wallet.connect.adapter.TrustConnectAdapter
import com.wallet.connect.adapter.WalletConnectAdapter
import com.wallet.connect.adapter.*


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedUtils.init(this)
        ParticleConnect.init(
            this,
            Env.DEV,
            SolanaChain(SolanaChainId.Devnet),
            DAppMetadata(
                "Particle Connect",
                "https://static.particle.network/wallet-icons/Particle.png",
                "https://particle.network"
            )
        ) {
            listOf(
                ParticleConnectAdapter(),
                MetaMaskConnectAdapter(),
                RainbowConnectAdapter(),
                TrustConnectAdapter(),
                PhantomConnectAdapter(),
                WalletConnectAdapter(),
                ImTokenConnectAdapter(),
                BitKeepConnectAdapter(),
                EVMConnectAdapter(),
                SolanaConnectAdapter(),
            )
        }
        //if you use auth-service-tiramisu, you can set the height of the browser
        ParticleNetwork.setBrowserHeightPercent(0.6f)

        //Controls whether the set password dialog appears in auth service, default is true
        ParticleNetwork.setSecurityAccountConfig(SecurityAccountConfig(false))

        ParticleWallet.init(this)
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        ParticleWallet.showTestNetworks(true)
    }

}
