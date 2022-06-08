package com.particle.demo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.LogUtils
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityMainBinding
import com.particle.base.*
import com.particle.gui.router.PNRouter
import com.particle.gui.router.RouterPath
import com.particle.network.ParticleNetworkAuth.isLogin
import com.particle.network.ParticleNetworkAuth.login
import com.particle.network.ParticleNetworkAuth.logout
import com.particle.network.ParticleNetworkAuth.setChainInfo
import com.particle.network.service.LoginType
import com.particle.network.service.WebServiceCallback
import com.particle.network.service.model.LoginOutput
import com.particle.network.service.model.WebOutput
import com.particle.network.service.model.WebServiceError


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val chainInfos = mutableListOf<ChainInfo>()

    private var selectChain = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setData()
        refreshUIState()
        setListeners()
    }

    private fun setData() {
        selectChain = getSharedPreferences(
            "main_${!ParticleNetwork.isEvmChain()}",
            Context.MODE_PRIVATE
        ).getInt(
            "select_chain",
            selectChain
        )
        if (!ParticleNetwork.isEvmChain()) {
            chainInfos.add(SolanaChain(SolanaChainId.Mainnet))
            chainInfos.add(SolanaChain(SolanaChainId.Testnet))
            chainInfos.add(SolanaChain(SolanaChainId.Devnet))
            if (selectChain > 2) {
                selectChain = 0
            }
        } else {
            chainInfos.add(EthereumChain(EthereumChainId.Mainnet))
            chainInfos.add(EthereumChain(EthereumChainId.TestnetKovan))
            chainInfos.add(BscChain(BscChainId.Mainnet))
            chainInfos.add(BscChain(BscChainId.Testnet))
            chainInfos.add(PolygonChain(PolygonChainId.Mainnet))
            chainInfos.add(PolygonChain(PolygonChainId.TestnetMumbai))
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

            chainInfos.add(ArbitrumChain(ArbitrumChainId.Mainnet))
            chainInfos.add(ArbitrumChain(ArbitrumChainId.Testnet))

            chainInfos.add(HarmonyChain(HarmonyChainId.Mainnet))
            chainInfos.add(HarmonyChain(HarmonyChainId.Testnet))

            chainInfos.add(AuroraChain(AuroraChainId.Mainnet))
            chainInfos.add(AuroraChain(AuroraChainId.Testnet))
        }

        binding.chain.text =
            chainInfos[selectChain].chainName.toString() + "\n" + chainInfos[selectChain].chainId.toString()
        ParticleNetwork.setChainInfo(chainInfos[selectChain])
    }

    private fun refreshUIState() {
        if (ParticleNetwork.isLogin()) {
            binding.loginSuccess.visibility = View.VISIBLE
            binding.loginLayout.visibility = View.GONE
            binding.welcome.visibility = View.VISIBLE
            binding.icon.visibility = View.GONE
            if (ParticleNetwork.isEvmChain()) {
                binding.apiReference.visibility = View.GONE
            }
        } else {
            binding.loginSuccess.visibility = View.GONE
            binding.loginLayout.visibility = View.VISIBLE
            binding.welcome.visibility = View.GONE
            binding.icon.visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        binding.emailLogin.setOnClickListener {
            login(LoginType.EMAIL)
        }
        binding.phoneLogin.setOnClickListener {
            login(LoginType.PHONE)
        }

        binding.openWallet.setOnClickListener {
            openWallet()
        }

        binding.logout.setOnClickListener {
            logout()

        }

        binding.chain.setOnClickListener {
            openSelectChain()
        }

        binding.apiReference.setOnClickListener {
            startActivity(Intent(this@MainActivity, APIReferenceActivity::class.java))
        }
    }

    private fun login(loginType: LoginType) {
        ParticleNetwork.login(this, loginType, object : WebServiceCallback<LoginOutput> {
            override fun success(output: LoginOutput) {
                refreshUIState()
            }

            override fun failure(errMsg: WebServiceError) {
                Toast.makeText(this@MainActivity, errMsg.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun logout() {
        ParticleNetwork.logout(this, object : WebServiceCallback<WebOutput> {
            override fun success(output: WebOutput) {
                refreshUIState()
            }

            override fun failure(errMsg: WebServiceError) {
                Toast.makeText(this@MainActivity, errMsg.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun openWallet() {
        PNRouter.build(RouterPath.Wallet).navigation()
    }

    private fun openSelectChain() {
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setIcon(R.drawable.icon)
        alertDialog.setTitle("Choose Chain")

        val listItems =
            chainInfos.map {
                it.chainName.toString()
                    .replaceFirstChar { it.uppercase() } + "-" + it.chainId.toString() + "-" + it.chainId.value()
            }.toTypedArray()

        alertDialog.setSingleChoiceItems(listItems, selectChain) { dialog, which ->
            selectChain = which
            getSharedPreferences(
                "main_${!ParticleNetwork.isEvmChain()}",
                Context.MODE_PRIVATE
            ).edit()
                .putInt("select_chain", selectChain).commit()
            chooseChain(chainInfos[which])
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("Cancel", null)
        alertDialog.create().show()
    }

    private fun chooseChain(chain: ChainInfo) {
        binding.chain.text = chain.chainName.toString() + "\n" + chain.chainId.toString()
        ParticleNetwork.setChainInfo(chain)
        val isLogin = ParticleNetwork.isLogin()
        LogUtils.d("isLogin:$isLogin")
        refreshUIState()
    }
}