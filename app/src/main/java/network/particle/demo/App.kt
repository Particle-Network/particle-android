package network.particle.demo

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.android.material.color.DynamicColors
import com.particle.base.*
import network.particle.chains.ChainInfo
import network.particle.demo.utils.ParticleInitUtils


class App : Application() {
    var isDebug = false

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        ParticleInitUtils.initWallet(this, ChainInfo.Ethereum)
    }


}
