package network.particle.demo.utils

import android.content.Context
import com.connect.common.model.DAppMetadata
import com.evm.adapter.EVMConnectAdapter
import com.particle.base.*
import com.particle.connect.ParticleConnect
import com.particle.connect.ParticleConnectAdapter
import com.particle.erc4337.biconomy.BiconomyService
import com.particle.gui.ParticleWallet
import com.phantom.adapter.PhantomConnectAdapter
import com.solana.adapter.SolanaConnectAdapter
import com.wallet.connect.adapter.*

object ParticleInitUtils {

    // it is recommended to initialize it in Application. This is only used as an example.
    fun initAuth(context: Context, chainInfo: ChainInfo) {
        ParticleNetwork.init(context, Env.PRODUCTION, chainInfo)
    }

    fun initConnect(context: Context, chainInfo: ChainInfo) {
        ParticleConnect.init(
            context, Env.PRODUCTION, chainInfo, DAppMetadata("",
                "Particle Connect",
                "https://connect.particle.network/icons/512.png",
                "https://connect.particle.network",
                "","",""
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
                EVMConnectAdapter("https://api-debug.particle.network/evm-chain/rpc/"),//"https://api-debug.particle.network/evm-chain/rpc/"
                SolanaConnectAdapter("https://api-debug.particle.network/solana/rpc/"),//"https://api-debug.particle.network/solana/rpc/"
            )
        }
    }

    fun initWallet(context: Context, chainInfo: ChainInfo) {
        initConnect(context, chainInfo)
        /**
         *  supportChains is optional,, if not provided, all chains will be supported
         *  if provided, only the chains in the list will be supported,Only the main chain is required,
         *  if you want to support devnet, you can call showTestNetworks() to show the devnet networks
         */
        val supportChains = mutableListOf(
            SolanaChain(SolanaChainId.Mainnet),
            EthereumChain(EthereumChainId.Mainnet),
            BscChain(BscChainId.Mainnet)
        )
        ParticleWallet.init(
            context
        ).apply {
            showTestNetworks(true)
            showSettingManageWallet(true)
            hideMainBackIcon()
        }
        ParticleNetwork.setAppliedLanguage(LanguageEnum.EN)
        //enable AA-4337 mode
        ParticleNetwork.setBiconomyService(BiconomyService)
    }
}