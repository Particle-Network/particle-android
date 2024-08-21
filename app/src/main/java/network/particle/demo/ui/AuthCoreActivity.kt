package network.particle.demo.ui


import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gyf.immersionbar.ImmersionBar
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityAuthCoreDemoBinding
import com.particle.api.infrastructure.db.table.WalletInfo
import com.particle.auth.AuthCore
import com.particle.auth.data.AuthCoreServiceCallback
import com.particle.base.*
import com.particle.base.data.ErrorInfo
import com.particle.base.model.ChainType
import com.particle.base.model.MobileWCWalletName
import com.particle.base.model.SocialLoginType
import com.particle.base.model.UserInfo
import com.particle.gui.ParticleWallet
import com.particle.gui.router.PNRouter
import kotlinx.coroutines.launch
import network.particle.chains.ChainInfo
import network.particle.demo.ui.base.DemoBaseActivity

class AuthCoreActivity :
    DemoBaseActivity<ActivityAuthCoreDemoBinding>(R.layout.activity_auth_core_demo) {

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).statusBarColor(android.R.color.transparent).titleBar(
            binding.toolbar
        ).init()
    }

    var currChainInfo: ChainInfo? = ChainInfo.Ethereum
    override fun setListeners() {
        super.setListeners()
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.login.setOnClickListener {

            AuthCore.connect(
                SocialLoginType.GOOGLE,
                connectCallback = object : AuthCoreServiceCallback<UserInfo> {
                    override fun failure(errMsg: ErrorInfo) {
                    }

                    override fun success(output: UserInfo) {
                        lifecycleScope.launch {
                            val publicAddress =
                                output.getWallet(if (ParticleNetwork.chainInfo.isEvmChain()) ChainType.EVM else ChainType.Solana)!!.publicAddress
                            val walletInfo = WalletInfo.createWallet(
                                publicAddress,
                                ParticleNetwork.chainName,
                                ParticleNetwork.chainId,
                                1,
                                "Custom WalletName",
                                MobileWCWalletName.AuthCore.name
                            )
                            ParticleWallet.setWallet(walletInfo)
                            PNRouter.navigatorWallet()
                        }
                    }


                })

        }
    }


    private fun showMessageDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this@AuthCoreActivity).setTitle(title).setMessage(message).show()
    }


}