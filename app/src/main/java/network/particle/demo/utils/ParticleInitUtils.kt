package network.particle.demo.utils

import android.app.Application
import android.content.Context
import com.evm.adapter.EVMConnectAdapter
import com.particle.base.*
import com.particle.base.model.DAppMetadata
import com.particle.connect.ParticleConnect
import com.particle.erc4337.ParticleNetworkBiconomy.initBiconomyMode
import com.particle.erc4337.biconomy.BiconomyService
import com.particle.gui.ParticleWallet
import com.phantom.adapter.PhantomConnectAdapter
import com.solana.adapter.SolanaConnectAdapter
import com.wallet.connect.adapter.*
import network.particle.chains.ChainInfo
import particle.auth.adapter.ParticleConnectAdapter

object ParticleInitUtils {

    // it is recommended to initialize it in Application. This is only used as an example.
    fun initAuth(context: Context, chainInfo: ChainInfo) {
        ParticleNetwork.init(context, Env.PRODUCTION, chainInfo)
    }

    fun initConnect(app: Application, chainInfo: ChainInfo) {
        val dAppMetadata = DAppMetadata(
            "f431aaea6e4dea6a669c0496f9c009c1",
            "Particle Connect",
            "https://connect.particle.network/icons/512.png",
            "https://particle.network",
            description = "Particle Connect is a decentralized wallet connection protocol that makes it easy for users to connect their wallets to your DApp.",
        )
        ParticleConnect.init(
            app, Env.PRODUCTION, chainInfo, dAppMetadata
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
                EVMConnectAdapter("https://api-debug.particle.network/evm-chain/rpc/"),//"https://api-debug.particle.network/evm-chain/rpc/"
                SolanaConnectAdapter("https://api-debug.particle.network/solana/rpc/"),//"https://api-debug.particle.network/solana/rpc/"
            )
        }
    }

    fun initWallet(app: Application, chainInfo: ChainInfo) {
        initConnect(app, chainInfo)
        /**
         *  supportChains is optional,, if not provided, all chains will be supported
         *  if provided, only the chains in the list will be supported,Only the main chain is required,
         *  if you want to support devnet, you can call showTestNetworks() to show the devnet networks
         */

        ParticleWallet.init(
            app
        ).apply {
            setShowTestNetworkSetting(true)
            setShowManageWalletSetting(true)
            hideMainBackIcon()
        }
        ParticleNetwork.setLanguage(LanguageEnum.EN)
        //enable AA-4337 mode
        ParticleNetwork.initBiconomyMode(
            mapOf(
                EthereumChainId.Mainnet.id to "your key",
            )
        )
        ParticleNetwork.setBiconomyService(BiconomyService)
        ParticleNetwork.getBiconomyService().enableBiconomyMode()
    }
}