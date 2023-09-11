package network.particle.demo.utils

import com.particle.base.*
import network.particle.chains.ChainInfo

object DemoChainUtils {
    fun getAllChainInfo(): List<ChainInfo> {
        return ChainInfo.getAllChains()
    }

}