package com.particle.demo.utils

import com.connect.common.model.ChainType
import com.connect.common.utils.Base58Utils
import com.connect.common.utils.HexUtils
import com.particle.connect.ParticleConnect


object MockManger {

    fun encode(message: String): String {
        return if (ParticleConnect.chainType == ChainType.Solana) {
            Base58Utils.encode(message.toByteArray(Charsets.UTF_8))
        } else {
            HexUtils.encodeWithPrefix(message.toByteArray(Charsets.UTF_8))
        }
    }

}