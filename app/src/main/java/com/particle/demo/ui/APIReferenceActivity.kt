package com.particle.demo.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityApiRefListBinding
import com.particle.gui.router.PNRouter
import com.particle.gui.router.RouterPath
import com.particle.network.ParticleNetwork
import com.particle.network.infrastructure.net.data.SerializeSOLTransReq
import com.particle.network.service.WebServiceCallback
import com.particle.network.service.model.SignOutput
import com.particle.network.service.model.WebServiceError
import kotlinx.coroutines.launch
import kotlin.math.pow


class APIReferenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiRefListBinding

    private val chainInfos = mutableListOf<ChainInfo>()

    private var selectChain = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_api_ref_list)
        setListeners()
    }

    private fun setListeners() {

        //Auth Service
        binding.signSendTransaction.setOnClickListener {

            lifecycleScope.launch {
                val req = SerializeSOLTransReq(
                    ParticleNetwork.getPublicKey(),
                    "BBBsMq9cEgRf9jeuXqd6QFueyRDhNwykYz63s1vwSCBZ",
                    2 * 10.0.pow(9.0).toLong()
                )
                val result =
                    ParticleNetwork.getSolanaService().enhancedSerializeTransaction(req).result
                val message = result.encodedTransactionSerializeMessage
                ParticleNetwork.signAndSendTransaction(
                    this@APIReferenceActivity,
                    message,
                    object : WebServiceCallback<SignOutput> {

                        override fun success(output: SignOutput) {
                            showToast(getString(R.string.success))
                        }

                        override fun failure(errMsg: WebServiceError) {
                            showToast(getString(R.string.failed))
                        }
                    })
            }
        }
        binding.signTransaction.setOnClickListener {

            lifecycleScope.launch {

                val req = SerializeSOLTransReq(
                    ParticleNetwork.getPublicKey(),
                    "BBBsMq9cEgRf9jeuXqd6QFueyRDhNwykYz63s1vwSCBZ",
                    2 * 10.0.pow(9.0).toLong()
                )
                val result =
                    ParticleNetwork.getSolanaService().enhancedSerializeTransaction(req).result
                val message = result.encodedTransactionSerializeMessage
                //transaction: base58 string
                ParticleNetwork.signTransaction(
                    this@APIReferenceActivity,
                    message,
                    object : WebServiceCallback<SignOutput> {
                        override fun success(output: SignOutput) {
                            //sign transaction success
                        }

                        override fun failure(errMsg: WebServiceError) {
                            // handle error
                        }
                    })
            }

        }

        binding.signMessage.setOnClickListener {
            //sign any string
            ParticleNetwork.signMessage(
                this@APIReferenceActivity,
                "your message",
                object : WebServiceCallback<SignOutput> {
                    override fun success(output: SignOutput) {
                        //sign success
                    }

                    override fun failure(errMsg: WebServiceError) {
                        // handle error
                    }
                })

        }
        //Wallet Service
        binding.openWallet.setOnClickListener {
            PNRouter.build(RouterPath.Wallet).navigation()
        }

        binding.openSendToken.setOnClickListener {
            //open send spl token
            //val params = WalletSendParams(tokenAddress, toAddress?, toAmount?)
            //PNRouter.build(RouterPath.TokenSend, params).navigation()

            //open send default token by chain name
            PNRouter.build(RouterPath.TokenSend).navigation()

        }
        binding.openReceiveToken.setOnClickListener {
            PNRouter.build(RouterPath.TokenReceive).navigation()
        }
        binding.openTransactionRecords.setOnClickListener {

            //open spl token transaction records
            // val params = TokenTransactionRecordsParams(tokenAddress)
            // PNRouter.build(RouterPath.TokenTransactionRecords, params).navigation()

            //open default token transaction records by chain name
            PNRouter.build(RouterPath.TokenTransactionRecords).navigation()
        }
        binding.openNftDetails.setOnClickListener {
//            val params = NftDetailParams()
//            PNRouter.build(RouterPath.NftDetails, params).navigation()
            showToast(getString(R.string.api_tips))
        }
        binding.openNftSend.setOnClickListener {
//            val params = NftDetailParams(mint， receiver)
//            PNRouter.build(RouterPath.NftSend).navigation()
            showToast(getString(R.string.api_tips))
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@APIReferenceActivity, msg, Toast.LENGTH_LONG).show()
    }
}