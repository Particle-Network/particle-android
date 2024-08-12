package network.particle.demo.ui

import android.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.LogUtils
import com.connect.common.ConnectKitCallback
import com.connect.common.model.Account
import com.connect.common.model.ConnectError
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityParticleWalletDemoBinding
import com.particle.connectkit.AdditionalLayoutOptions
import com.particle.connectkit.ConnectKitConfig
import com.particle.connectkit.ConnectOption
import com.particle.connectkit.EnableSocialProvider
import com.particle.connectkit.EnableWallet
import com.particle.connectkit.EnableWalletLabel
import com.particle.connectkit.EnableWalletProvider
import com.particle.connectkit.ParticleConnectKit
import com.particle.gui.ParticleWallet
import network.particle.demo.ui.adapter.BannerAdapter
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.coroutines.launch
import network.particle.demo.ui.base.DemoBaseActivity


class ParticleWalletLoginDemoActivity :
    DemoBaseActivity<ActivityParticleWalletDemoBinding>(R.layout.activity_particle_wallet_demo) {
    val pageTipsStr = arrayOf(
        R.string.pn_page1_tips,
        R.string.pn_page2_tips,
        R.string.pn_page3_tips,
        R.string.pn_page4_tips
    )

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).transparentStatusBar().hideBar(BarHide.FLAG_HIDE_BAR).init()
        initHorizontalBanner()
        setObserver()
    }

    override fun setListeners() {
        super.setListeners()
        binding.rlLoginWithConnectKit.setOnClickListener {
            val config = ConnectKitConfig(
                logo = "",
                connectOptions = listOf(
                    ConnectOption.EMAIL,
                    ConnectOption.PHONE,
                    ConnectOption.SOCIAL,
                    ConnectOption.WALLET),
                socialProviders = listOf(
                    EnableSocialProvider.GOOGLE,
                    EnableSocialProvider.APPLE,
                    EnableSocialProvider.DISCORD,
                    EnableSocialProvider.TWITTER,
                    EnableSocialProvider.FACEBOOK,
                    EnableSocialProvider.GITHUB,
                    EnableSocialProvider.MICROSOFT,
                    EnableSocialProvider.TWITCH,
                    EnableSocialProvider.LINKEDIN),
                walletProviders = listOf(
                    EnableWalletProvider(EnableWallet.MetaMask, EnableWalletLabel.RECOMMENDED),
                    EnableWalletProvider(EnableWallet.OKX),
                    EnableWalletProvider(EnableWallet.Phantom),
                    EnableWalletProvider(EnableWallet.Trust),
                    EnableWalletProvider(EnableWallet.Bitget),
                    EnableWalletProvider(EnableWallet.WalletConnect),
                ),
                additionalLayoutOptions = AdditionalLayoutOptions(
                    isCollapseWalletList = false,
                    isSplitEmailAndSocial = false,
                    isSplitEmailAndPhone = false,
                    isHideContinueButton = false
                )
            )
            ParticleConnectKit.connect(config,connectCallback = object : ConnectKitCallback {
                override fun onConnected(walletName: String, account: Account) {
                    LogUtils.d("onConnected: $walletName, $account")
                    lifecycleScope.launch {
                        ParticleWallet.setWallet(account.publicAddress,walletName)
                        openWallet()
                    }
                }

                override fun onError(error: ConnectError) {
                }

            });
        }


    }


    private fun initHorizontalBanner() {
        binding.bannerView.setScrollDuration(600).setOffScreenPageLimit(2)
            .setLifecycleRegistry(lifecycle).setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setIndicatorSlideMode(IndicatorSlideMode.NORMAL).setInterval(3000)
            .setIndicatorGravity(IndicatorGravity.CENTER).setIndicatorSliderRadius(10)
            .disallowParentInterceptDownEvent(true).setIndicatorSliderColor(
                Color.parseColor("#4E4E50"), Color.WHITE
            ).setAdapter(BannerAdapter())
            .registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.tvSubTitle.text = getString(pageTipsStr[position])
                }
            }).create()
        binding.bannerView.refreshData(
            mutableListOf(
                R.drawable.page1, R.drawable.page2, R.drawable.page3, R.drawable.page4
            )
        )

    }

    private fun openWallet() {
        finish()
    }

}