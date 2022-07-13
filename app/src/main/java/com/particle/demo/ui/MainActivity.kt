package com.particle.demo.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ToastUtils
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityMainBinding
import com.particle.base.*
import com.particle.gui.router.PNRouter
import com.particle.gui.router.RouterPath
import com.particle.network.ParticleNetworkAuth.isLogin
import com.particle.network.ParticleNetworkAuth.login
import com.particle.network.ParticleNetworkAuth.logout
import com.particle.network.ParticleNetworkAuth.setChainInfo
import com.particle.network.service.ChainChangeCallBack
import com.particle.network.service.LoginType
import com.particle.network.service.SupportAuthType
import com.particle.network.service.WebServiceCallback
import com.particle.network.service.model.LoginOutput
import com.particle.network.service.model.WebOutput
import com.particle.network.service.model.WebServiceError


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val chainInfos = mutableListOf<ChainInfo>()

    private var selectChain = 0
    private lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getSharedPreferences("main", Context.MODE_PRIVATE)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setData()
        refreshUIState()
        setListeners()
    }

    private fun setData() {
        selectChain = sp.getInt("select_chain", selectChain)
        chainInfos.add(SolanaChain(SolanaChainId.Mainnet))
        chainInfos.add(SolanaChain(SolanaChainId.Testnet))
        chainInfos.add(SolanaChain(SolanaChainId.Devnet))

        chainInfos.add(EthereumChain(EthereumChainId.Mainnet))
        chainInfos.add(EthereumChain(EthereumChainId.Kovan))
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

        chainInfos.add(ArbitrumChain(ArbitrumChainId.Mainnet))
        chainInfos.add(ArbitrumChain(ArbitrumChainId.Testnet))

        chainInfos.add(HarmonyChain(HarmonyChainId.Mainnet))
        chainInfos.add(HarmonyChain(HarmonyChainId.Testnet))

        chainInfos.add(AuroraChain(AuroraChainId.Mainnet))
        chainInfos.add(AuroraChain(AuroraChainId.Testnet))

        binding.chain.text =
            chainInfos[selectChain].chainName.toString() + "\n" + chainInfos[selectChain].chainId.toString()
        if (ParticleNetwork.isLogin()) {
            ParticleNetwork.setChainInfo(
                chainInfos[selectChain],
                object : ChainChangeCallBack {
                    override fun success() {
//                        ToastUtils.showLong("Chain:${ParticleNetwork.chainInfo.chainName}")
                    }

                    override fun failure() {
                        ToastUtils.showLong("Failure,current Chain:${ParticleNetwork.chainInfo.chainName}")
                    }
                })
        } else {
            ParticleNetwork.setChainInfo(chainInfos[selectChain])
        }
    }

    private fun refreshUIState() {
        if (ParticleNetwork.isLogin()) {
            binding.loginSuccess.visibility = View.VISIBLE
            binding.loginLayout.visibility = View.GONE
            binding.welcome.visibility = View.VISIBLE
            binding.icon.visibility = View.GONE

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
            login(
                LoginType.PHONE,
                SupportAuthType.GOOGLE.value or SupportAuthType.FACEBOOK.value or SupportAuthType.APPLE.value
            )
        }
        binding.googleLogin.setOnClickListener {
            login(LoginType.GOOGLE, SupportAuthType.GOOGLE.value)
        }
        binding.facebookLogin.setOnClickListener {
            login(LoginType.FACEBOOK, SupportAuthType.FACEBOOK.value)
        }
        binding.appleLogin.setOnClickListener {
            login(LoginType.APPLE, SupportAuthType.APPLE.value)
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

    private fun login(
        loginType: LoginType,
        supportAuthTypeValue: Int = SupportAuthType.NONE.value,
    ) {
        ParticleNetwork.login(
            loginType,
            "",
            supportAuthTypeValue,
            object : WebServiceCallback<LoginOutput> {
                override fun success(output: LoginOutput) {
                    refreshUIState()
                }

                override fun failure(errMsg: WebServiceError) {
                    Toast.makeText(this@MainActivity, errMsg.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun logout() {
        ParticleNetwork.logout(object : WebServiceCallback<WebOutput> {
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

            chooseChain(chainInfos[which])
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("Cancel", null)
        alertDialog.create().show()
    }

    private fun chooseChain(chain: ChainInfo) {
        binding.chain.text = chain.chainName.toString() + "\n" + chain.chainId.toString()
        if (ParticleNetwork.isLogin()) {
            ParticleNetwork.setChainInfo(
                chainInfos[selectChain],
                object : ChainChangeCallBack {
                    override fun success() {
                        refreshUIState()
                        ToastUtils.showLong("currChain:${ParticleNetwork.chainInfo.chainName}")
                        sp.edit().putInt("select_chain", selectChain).commit()
                    }

                    override fun failure() {
                        ToastUtils.showLong("success failure:${ParticleNetwork.chainInfo.chainName}")
                    }
                })
        } else {
            ParticleNetwork.setChainInfo(chainInfos[selectChain])
        }
    }

    override fun onResume() {
        super.onResume()
    }
}