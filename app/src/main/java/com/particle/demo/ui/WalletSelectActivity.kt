package com.particle.demo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityWalletBinding
import com.particle.api.evm
import com.particle.base.*


class WalletSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet)
        setListeners()
    }


    private fun setListeners() {
        binding.solana.setOnClickListener {
            ParticleNetwork.setChainInfo(SolanaChain(SolanaChainId.Mainnet))
            startActivity(Intent(this@WalletSelectActivity, MainActivity::class.java))
        }
        binding.ethereum.setOnClickListener {
            ParticleNetwork.setChainInfo(EthereumChain(EthereumChainId.Mainnet))
            startActivity(Intent(this@WalletSelectActivity, MainActivity::class.java))
        }
    }

    fun test() {
        ParticleNetwork.evm

    }


}