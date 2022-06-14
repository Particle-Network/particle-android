package com.particle.demo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityApiRefListBinding
import com.particle.api.infrastructure.net.data.SerializeSOLTransReq
import com.particle.api.solana
import com.particle.base.ParticleNetwork
import com.particle.gui.router.PNRouter
import com.particle.gui.router.RouterPath
import com.particle.network.ParticleNetworkAuth.getPublicKey
import com.particle.network.ParticleNetworkAuth.signAndSendTransaction
import com.particle.network.ParticleNetworkAuth.signMessage
import com.particle.network.ParticleNetworkAuth.signTransaction
import com.particle.network.service.WebServiceCallback
import com.particle.network.service.model.SignOutput
import com.particle.network.service.model.WebServiceError
import kotlinx.coroutines.launch
import kotlin.math.pow


class APIReferenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiRefListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_api_ref_list)
        binding.isEvm = ParticleNetwork.isEvmChain()
        setListeners()
    }

    private fun setListeners() {
        //Auth Service
        binding.signSendTransaction.setOnClickListener {
            testSignSendTransaction()
        }
        binding.signTransaction.setOnClickListener {
            testSignTransaction()
        }
        binding.signMessage.setOnClickListener {
            testSignMessage()
        }

        //Api Service
        binding.erc20Transfer.setOnClickListener {
//            ParticleNetwork.evm.erc20Transfer(contractAddress = "",to="", amount = )
            ToastUtils.showLong(getString(R.string.api_tips))
        }
        binding.erc20Approve.setOnClickListener {
//            ParticleNetwork.evm.erc20Approve()
            ToastUtils.showLong(getString(R.string.api_tips))
        }
        binding.erc20TransferFrom.setOnClickListener {
//            ParticleNetwork.evm.erc20TransferFrom()
            ToastUtils.showLong(getString(R.string.api_tips))
        }
        binding.erc721SafeTransferFrom.setOnClickListener {
//            ParticleNetwork.evm.erc721SafeTransferFrom()
            ToastUtils.showLong(getString(R.string.api_tips))
        }
        binding.erc1155SafeTransferFrom.setOnClickListener {
//            ParticleNetwork.evm.erc1155SafeTransferFrom()
            ToastUtils.showLong(getString(R.string.api_tips))
        }

        //Wallet Service
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
            ToastUtils.showLong(getString(R.string.api_tips))
        }
        binding.openNftSend.setOnClickListener {
//            val params = NftDetailParams("5iNNGxfmvE98vDFUrpUiSSU2NXYFx3jLqFSeLh7J8xL4", "5iNNGxfmvE98vDFUrpUiSSU2NXYFx3jLqFSeLh7J8xL4")
//            PNRouter.build(RouterPath.NftDetails,params).navigation()
            ToastUtils.showLong(getString(R.string.api_tips))
        }
    }


    private fun testSignSendTransaction() {
        if (ParticleNetwork.isEvmChain()) {
            lifecycleScope.launch {
                val message =
                    "0x7b22636861696e4964223a2230783261222c2264617461223a223078222c2266726f6d223a22307835303446383344363530323966423630376663416134336562443062373032326162313631423043222c226761734c696d6974223a22307835323038222c226d6178466565506572476173223a2230783539363832663061222c226d61785072696f72697479466565506572476173223a2230783539363832663030222c226e6f6e6365223a22307830222c2272223a6e756c6c2c2273223a6e756c6c2c22746f223a22307831363338306130334632314535613545333339633135424138654245353831643139346530444233222c2274797065223a22307832222c2276223a6e756c6c2c2276616c7565223a22307833386437656134633638303030227d"
                ParticleNetwork.signAndSendTransaction(
                    this@APIReferenceActivity,
                    message,
                    object : WebServiceCallback<SignOutput> {
                        override fun success(output: SignOutput) {
                        }

                        override fun failure(errMsg: WebServiceError) {
                        }
                    })
            }
        } else {
            lifecycleScope.launch {
                binding.signSendTransaction.isClickable = false
                val req = SerializeSOLTransReq(
                    ParticleNetwork.getPublicKey(),
                    "BBBsMq9cEgRf9jeuXqd6QFueyRDhNwykYz63s1vwSCBZ",
                    2 * 10.0.pow(9.0).toLong()
                )
                val result =
                    ParticleNetwork.solana.serializeTransaction(req).result
                val message = result.encodedTransactionSerializeMessage
                ParticleNetwork.signAndSendTransaction(
                    this@APIReferenceActivity,
                    message,
                    object : WebServiceCallback<SignOutput> {

                        override fun success(output: SignOutput) {
                            showToast(getString(R.string.success))
                            binding.signSendTransaction.isClickable = true
                        }

                        override fun failure(errMsg: WebServiceError) {
                            showToast(getString(R.string.failed))
                            binding.signSendTransaction.isClickable = true
                        }
                    })

            }
        }
    }

    private fun testSignTransaction() {
        if (ParticleNetwork.isEvmChain()) {
            lifecycleScope.launch {
                val message =
                    "0x7b22636861696e4964223a2230783261222c2264617461223a223078222c2266726f6d223a22307835303446383344363530323966423630376663416134336562443062373032326162313631423043222c226761734c696d6974223a22307835323038222c226d6178466565506572476173223a2230783539363832663061222c226d61785072696f72697479466565506572476173223a2230783539363832663030222c226e6f6e6365223a22307830222c2272223a6e756c6c2c2273223a6e756c6c2c22746f223a22307831363338306130334632314535613545333339633135424138654245353831643139346530444233222c2274797065223a22307832222c2276223a6e756c6c2c2276616c7565223a22307833386437656134633638303030227d"
                ParticleNetwork.signTransaction(
                    this@APIReferenceActivity,
                    message,
                    object : WebServiceCallback<SignOutput> {
                        override fun success(output: SignOutput) {
                        }

                        override fun failure(errMsg: WebServiceError) {
                        }
                    })
            }
        } else {
            lifecycleScope.launch {
                binding.signTransaction.isClickable = false
                val req = SerializeSOLTransReq(
                    ParticleNetwork.getPublicKey(),
                    "BBBsMq9cEgRf9jeuXqd6QFueyRDhNwykYz63s1vwSCBZ",
                    2 * 10.0.pow(9.0).toLong()
                )
                val result =
                    ParticleNetwork.solana.serializeTransaction(req).result
                val message = result.encodedTransactionSerializeMessage
                //transaction: base58 string
                ParticleNetwork.signTransaction(
                    this@APIReferenceActivity,
                    message,
                    object : WebServiceCallback<SignOutput> {
                        override fun success(output: SignOutput) {
                            //sign transaction success
                            binding.signTransaction.isClickable = true
                        }

                        override fun failure(errMsg: WebServiceError) {
                            // handle error
                            binding.signTransaction.isClickable = true
                        }
                    })
            }
        }

    }

    private fun testSignMessage() {
        if (ParticleNetwork.isEvmChain()) {
            lifecycleScope.launch {
                val message =
                    "0x7b22636861696e4964223a2230783261222c2264617461223a223078222c2266726f6d223a22307835303446383344363530323966423630376663416134336562443062373032326162313631423043222c226761734c696d6974223a22307835323038222c226d6178466565506572476173223a2230783539363832663061222c226d61785072696f72697479466565506572476173223a2230783539363832663030222c226e6f6e6365223a22307830222c2272223a6e756c6c2c2273223a6e756c6c2c22746f223a22307831363338306130334632314535613545333339633135424138654245353831643139346530444233222c2274797065223a22307832222c2276223a6e756c6c2c2276616c7565223a22307833386437656134633638303030227d"
                ParticleNetwork.signMessage(
                    this@APIReferenceActivity,
                    message,
                    object : WebServiceCallback<SignOutput> {
                        override fun success(output: SignOutput) {
                        }

                        override fun failure(errMsg: WebServiceError) {
                        }
                    })
            }
        } else {
            //sign any string must be base58 encode
            ParticleNetwork.signMessage(
                this@APIReferenceActivity,
                "MK5NWRBTfRn",
                object : WebServiceCallback<SignOutput> {
                    override fun success(output: SignOutput) {
                        //sign success
                    }

                    override fun failure(errMsg: WebServiceError) {
                        // handle error
                    }
                })
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@APIReferenceActivity, msg, Toast.LENGTH_LONG).show()
    }
}