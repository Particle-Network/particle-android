package network.particle.demo.utils

import com.particle.base.*

object DemoChainUtils {
    fun getAllChainInfo(): MutableList<ChainInfo> {
        val chainInfos = mutableListOf<ChainInfo>()

        chainInfos.add(SolanaChain(SolanaChainId.Mainnet))
        chainInfos.add(SolanaChain(SolanaChainId.Testnet))
        chainInfos.add(SolanaChain(SolanaChainId.Devnet))

        chainInfos.add(EthereumChain(EthereumChainId.Mainnet))
        chainInfos.add(EthereumChain(EthereumChainId.Goerli))
        chainInfos.add(EthereumChain(EthereumChainId.Sepolia))

        chainInfos.add(BscChain(BscChainId.Mainnet))
        chainInfos.add(BscChain(BscChainId.Testnet))

        chainInfos.add(PolygonChain(PolygonChainId.Mainnet))
        chainInfos.add(PolygonChain(PolygonChainId.Mumbai))

        chainInfos.add(AvalancheChain(AvalancheChainId.Mainnet))
        chainInfos.add(AvalancheChain(AvalancheChainId.Testnet))

        chainInfos.add(MoonbeamChain(MoonbeamChainId.Mainnet))
        chainInfos.add(MoonbeamChain(MoonbeamChainId.Testnet))

        chainInfos.add(MoonriverChain(MoonriverChainId.Mainnet))
        chainInfos.add(MoonriverChain(MoonriverChainId.Testnet))

        chainInfos.add(HecoChain(HecoChainId.Mainnet))
        chainInfos.add(HecoChain(HecoChainId.Testnet))

        chainInfos.add(FantomChain(FantomChainId.Mainnet))
        chainInfos.add(FantomChain(FantomChainId.Testnet))

        chainInfos.add(ArbitrumChain(ArbitrumChainId.One))
        chainInfos.add(ArbitrumChain(ArbitrumChainId.Nova))
        chainInfos.add(ArbitrumChain(ArbitrumChainId.Goerli))

        chainInfos.add(HarmonyChain(HarmonyChainId.Mainnet))
        chainInfos.add(HarmonyChain(HarmonyChainId.Testnet))

        chainInfos.add(AuroraChain(AuroraChainId.Mainnet))
        chainInfos.add(AuroraChain(AuroraChainId.Testnet))

        chainInfos.add(OptimismChain(OptimismChainId.Mainnet))
        chainInfos.add(OptimismChain(OptimismChainId.Goerli))

        chainInfos.add(KCCChain(KCCChainId.Mainnet))
        chainInfos.add(KCCChain(KCCChainId.Testnet))

        chainInfos.add(PlatONChain(PlatONChainId.Mainnet))
        chainInfos.add(PlatONChain(PlatONChainId.Testnet))

        chainInfos.add(TronChain(TronChainId.Mainnet))
        chainInfos.add(TronChain(TronChainId.Shasta))
        chainInfos.add(TronChain(TronChainId.Nile))

        chainInfos.add(OKCChain(OKCChainId.Mainnet))
        chainInfos.add(OKCChain(OKCChainId.Testnet))

        chainInfos.add(ThunderCoreChain(ThunderCoreChainId.Mainnet))
        chainInfos.add(ThunderCoreChain(ThunderCoreChainId.Testnet))

        chainInfos.add(CronosChain(CronosChainId.Mainnet))
        chainInfos.add(CronosChain(CronosChainId.Testnet))

        chainInfos.add(OasisEmeraldChain(OasisEmeraldChainId.Mainnet))
        chainInfos.add(OasisEmeraldChain(OasisEmeraldChainId.Testnet))

        chainInfos.add(GnosisChain(GnosisChainId.Mainnet))
        chainInfos.add(GnosisChain(GnosisChainId.Testnet))

        chainInfos.add(CeloChain(CeloChainId.Mainnet))
        chainInfos.add(CeloChain(CeloChainId.Testnet))

        chainInfos.add(KlaytnChain(KlaytnChainId.Mainnet))
        chainInfos.add(KlaytnChain(KlaytnChainId.Testnet))

        chainInfos.add(ScrollChain(ScrollChainId.Testnet))

        chainInfos.add(ZkSyncChain(ZkSyncChainId.Mainnet))
        chainInfos.add(ZkSyncChain(ZkSyncChainId.Testnet))

        chainInfos.add(MetisChain(MetisChainId.Mainnet))
        chainInfos.add(MetisChain(MetisChainId.Testnet))

        chainInfos.add(ConfluxChain(ConfluxChainId.Mainnet))
        chainInfos.add(ConfluxChain(ConfluxChainId.Testnet))

        chainInfos.add(MAPOChain(MAPOChainId.Mainnet))
        chainInfos.add(MAPOChain(MAPOChainId.Testnet))

        chainInfos.add(PolygonZkChain(PolygonZkChainId.Mainnet))
        chainInfos.add(PolygonZkChain(PolygonZkChainId.Testnet))

        chainInfos.add(BaseChain(BaseChainId.Testnet))

        return chainInfos
    }

    fun getAllTestnet(): MutableList<ChainInfo> {
        return getAllChainInfo().filter { !it.isMainnet() }.toMutableList()
    }

    fun getFaucet(chainId: Long): String? {
        return if (faucetMap.containsKey(chainId)) {
            faucetMap[chainId]!!
        } else {
            null
        }
    }

    val faucetMap = mutableMapOf<Long, String>()

    init {
        faucetMap[SolanaChainId.Testnet.id] = "https://solfaucet.com/"
        faucetMap[SolanaChainId.Devnet.id] = "https://solfaucet.com/"
        faucetMap[EthereumChainId.Goerli.id] = "https://faucet.quicknode.com/drip"
        faucetMap[EthereumChainId.Sepolia.id] = "https://sepoliafaucet.com/"
        faucetMap[BscChainId.Testnet.id] = "https://testnet.bnbchain.org/faucet-smart"
        faucetMap[AvalancheChainId.Testnet.id] = "https://faucet.avax.network/"
        faucetMap[PolygonChainId.Mumbai.id] = "https://mumbai.polygonscan.com/"
        faucetMap[ArbitrumChainId.Goerli.id] = "https://faucet.triangleplatform.com/arbitrum/goerli"
        faucetMap[MoonbeamChainId.Testnet.id] = "https://apps.moonbeam.network/moonbase-alpha/faucet/"
        faucetMap[MoonriverChainId.Testnet.id] = "https://apps.moonbeam.network/moonbase-alpha/faucet/"
        faucetMap[HecoChainId.Testnet.id] = "https://scan-testnet.hecochain.com/faucet"
        faucetMap[AuroraChainId.Testnet.id] = "https://aurora.dev/faucet"
        faucetMap[HarmonyChainId.Testnet.id] = "https://faucet.pops.one/"
        faucetMap[OptimismChainId.Goerli.id] = "https://faucet.triangleplatform.com/optimism/goerli"
        faucetMap[KCCChainId.Testnet.id] = "https://faucet-testnet.kcc.network/"
        faucetMap[PlatONChainId.Testnet.id] = "https://devnet2faucet.platon.network/faucet/"
        faucetMap[TronChainId.Nile.id] = "https://nileex.io/join/getJoinPage"
        faucetMap[TronChainId.Shasta.id] = "https://testnet.help/en/tronfaucet/shasta"
        faucetMap[OKCChainId.Testnet.id] = "https://docs.oxdex.com/v/en/help/gitter"
        faucetMap[ThunderCoreChainId.Testnet.id] = "https://faucet-testnet.thundercore.com/"
        faucetMap[CronosChainId.Testnet.id] = "https://cronos.org/faucet"
        faucetMap[OasisEmeraldChainId.Testnet.id] = "https://faucet.testnet.oasis.dev/"
        faucetMap[GnosisChainId.Testnet.id] = "https://gnosisfaucet.com"
        faucetMap[CeloChainId.Testnet.id] = "https://celo.org/developers/faucet"
        faucetMap[KlaytnChainId.Testnet.id] = "https://baobab.wallet.klaytn.foundation/faucet"
        faucetMap[ScrollChainId.Testnet.id] = "https://scroll.io/alpha"
        faucetMap[ZkSyncChainId.Testnet.id] = "https://portal.zksync.io/faucet"
        faucetMap[MetisChainId.Testnet.id] = "https://goerli.faucet.metisdevops.link"
        faucetMap[ConfluxChainId.Testnet.id] = "https://efaucet.confluxnetwork.org/"
        faucetMap[MAPOChainId.Testnet.id] = "https://faucet.mapprotocol.io/"
        faucetMap[PolygonZkChainId.Testnet.id] = "https://public.zkevm-test.net/"
        faucetMap[BaseChainId.Testnet.id] = "https://bridge.base.org/deposit"
    }
}