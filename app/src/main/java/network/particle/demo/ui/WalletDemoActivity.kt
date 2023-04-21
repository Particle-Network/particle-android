package network.particle.demo.ui

import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityWalletDemoBinding
import com.particle.gui.router.PNRouter
import com.particle.gui.router.RouterPath
import com.particle.gui.ui.swap.SwapConfig
import network.particle.demo.ui.base.DemoBaseActivity

class WalletDemoActivity : DemoBaseActivity<ActivityWalletDemoBinding>(R.layout.activity_wallet_demo) {

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).statusBarColor(android.R.color.transparent).titleBar(
            binding.toolbar
        ).init()
    }

    override fun setListeners() {
        super.setListeners()
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.openWallet.setOnClickListener {
            PNRouter.build(RouterPath.Wallet).navigation()
        }

        binding.openSendToken.setOnClickListener {
            //open send token
            //val params = WalletSendParams(tokenAddress, toAddress?, toAmount?)
            //PNRouter.build(RouterPath.TokenSend, params).navigation()

            //open send default token by chain name
            PNRouter.build(RouterPath.TokenSend).navigation()
        }
        binding.openReceiveToken.setOnClickListener {
            PNRouter.build(RouterPath.TokenReceive).navigation()
        }
        binding.openTransactionRecords.setOnClickListener {
            //open  token transaction records
            // val params = TokenTransactionRecordsParams(tokenAddress)
            // PNRouter.build(RouterPath.TokenTransactionRecords, params).navigation()

            //open default token transaction records by chain name
            PNRouter.build(RouterPath.TokenTransactionRecords).navigation()
        }
        binding.openNftDetails.setOnClickListener {
//            val params = NftDetailParams("5iNNGxfmvE98vDFUrpUiSSU2NXYFx3jLqFSeLh7J8xL4")
//            PNRouter.build(RouterPath.NftDetails, params).navigation()
            ToastUtils.showLong(getString(R.string.pn_api_tips))
        }
        binding.openNftSend.setOnClickListener {
//            val params = NftDetailParams("5iNNGxfmvE98vDFUrpUiSSU2NXYFx3jLqFSeLh7J8xL4", "5iNNGxfmvE98vDFUrpUiSSU2NXYFx3jLqFSeLh7J8xL4")
//            PNRouter.build(RouterPath.NftDetails,params).navigation()
            ToastUtils.showLong(getString(R.string.pn_api_tips))
        }
        binding.openSwap.setOnClickListener {
            PNRouter.navigatorSwap(SwapConfig(toTokenAddress = "0x66c3E9e7ecBCFaeB4A132cFCAdF23821a00b34e7"))
        }
    }
}