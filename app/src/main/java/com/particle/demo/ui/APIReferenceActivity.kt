package com.particle.demo.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityApiRefListBinding
import com.particle.gui.router.PNRouter
import com.particle.gui.router.RouterPath
import com.particle.network.ChainId
import com.particle.network.ChainName
import com.particle.network.ParticleNetwork
import com.particle.network.infrastructure.net.data.SerializeTokenTransReq
import com.particle.network.service.LoginType
import com.particle.network.service.WebServiceCallback
import com.particle.network.service.model.LoginOutput
import com.particle.network.service.model.SignOutput
import com.particle.network.service.model.WebOutput
import com.particle.network.service.model.WebServiceError


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
            /**
            //step 1
            val req = SerializeTokenTransReq("sender","receiver","mint",amount)
            ParticleNetwork.getSolanaService().enhancedSerializeTransaction(req)
            //step 2
            //transaction: base58 string from step 1 response
            ParticleNetwork.signAndSendTransaction(this@APIReferenceActivity, "", object : WebServiceCallback<SignOutput>{
            override fun success(output: SignOutput) {
            //sign and send transaction success
            }

            override fun failure(errMsg: WebServiceError) {
            // handle error
            }
            })
             **/
            showToast(getString(R.string.api_tips))
        }
        binding.signTransaction.setOnClickListener {
            /**
            //transaction: base58 string
            ParticleNetwork.signTransaction(activity, transaction, object : WebServiceCallback<SignOutput>{
            override fun success(output: SignOutput) {
            //sign transaction success
            }

            override fun failure(errMsg: WebServiceError) {
            // handle error
            }
            })
             */
            showToast(getString(R.string.api_tips))
        }

        binding.signMessage.setOnClickListener {
            /**
            //sign any string
            ParticleNetwork.signMessage(activity, message, object : WebServiceCallback<SignOutput>{
            override fun success(output: SignOutput) {
            //sign success
            }

            override fun failure(errMsg: WebServiceError) {
            // handle error
            }
            })
             */
            showToast(getString(R.string.api_tips))
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
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@APIReferenceActivity, msg, Toast.LENGTH_LONG).show()
    }
}