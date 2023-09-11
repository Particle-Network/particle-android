package network.particle.demo.ui

import android.content.Intent
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityMainBinding
import com.particle.base.ParticleNetwork
import com.particle.connect.ParticleConnect
import com.particle.gui.ParticleWallet
import com.particle.gui.ParticleWallet.navigatorDAppBrowser
import com.particle.gui.ParticleWallet.isWalletLogin
import network.particle.demo.ui.adapter.ChainInfoChoiceListAdapter
import network.particle.demo.ui.base.DemoBaseActivity
import network.particle.demo.utils.DemoChainUtils


class MainActivity : DemoBaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        super.initView()
        ImmersionBar.with(this).transparentStatusBar().hideBar(BarHide.FLAG_HIDE_BAR).init()
    }

    override fun setListeners() {
        super.setListeners()
        binding.btAuthDemo.setOnClickListener {
            startActivity(Intent(this, AuthDemoActivity::class.java))
        }
        binding.btApiDemo.setOnClickListener {
            startActivity(Intent(this, ApiDemoActivity::class.java))
        }
        binding.btWalletDemo.setOnClickListener {
            if (!ParticleNetwork.isWalletLogin()) {
                startActivity(Intent(this, ParticleWalletLoginDemoActivity::class.java))
                return@setOnClickListener
            }
            startActivity(Intent(this, WalletDemoActivity::class.java))

        }
    }
}