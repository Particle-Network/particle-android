package com.particle.demo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityMainBinding
import com.particle.gui.router.PNRouter
import com.particle.gui.router.RouterPath
import com.particle.network.ChainId
import com.particle.network.ChainName
import com.particle.network.ParticleNetwork
import com.particle.network.service.LoginType
import com.particle.network.service.WebServiceCallback
import com.particle.network.service.model.LoginOutput
import com.particle.network.service.model.WebOutput
import com.particle.network.service.model.WebServiceError


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val chainInfos = mutableListOf<ChainInfo>()

    private var selectChain = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setData()
        refreshUIState()
        setListeners()
    }

    private fun setData() {
        selectChain = getSharedPreferences("main", Context.MODE_PRIVATE).getInt(
            "select_chain",
            selectChain
        )
        chainInfos.add(ChainInfo(ChainName.Solana, ChainId.SolanaMainnet))
        chainInfos.add(ChainInfo(ChainName.Solana, ChainId.SolanaTestnet))
        chainInfos.add(ChainInfo(ChainName.Solana, ChainId.SolanaDevnet))

        binding.chain.text =
            chainInfos[selectChain].name.toString() + "\n" + chainInfos[selectChain].id.value
        ParticleNetwork.setChainId(chainInfos[selectChain].id)
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
            chainInfos.map { it.name.toString() + "-" + it.id.value }.toTypedArray()

        alertDialog.setSingleChoiceItems(listItems, selectChain) { dialog, which ->
            selectChain = which
            getSharedPreferences("main", Context.MODE_PRIVATE).edit()
                .putInt("select_chain", selectChain).commit()
            chooseChain(chainInfos[which])
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("Cancel", null)
        alertDialog.create().show()
    }

    private fun chooseChain(chain: ChainInfo) {
        binding.chain.text = chain.name.toString() + "\n" + chain.id.value
        ParticleNetwork.setChainId(chain.id)
    }
}