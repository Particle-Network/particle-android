package com.particle.demo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityApiRefListBinding
import com.particle.api.evm
import com.particle.api.infrastructure.net.data.SerializeSOLTransReq
import com.particle.api.service.data.ContractParams
import com.particle.api.solana
import com.particle.base.ParticleNetwork
import com.particle.gui.router.PNRouter
import com.particle.gui.router.RouterPath
import com.particle.network.ParticleNetworkAuth.getAddress
import com.particle.network.ParticleNetworkAuth.signAndSendTransaction
import com.particle.network.ParticleNetworkAuth.signMessage
import com.particle.network.ParticleNetworkAuth.signTransaction
import com.particle.network.service.WebServiceCallback
import com.particle.network.service.model.SignOutput
import com.particle.network.service.model.WebServiceError
import kotlinx.coroutines.launch
import java.math.BigDecimal
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
        val contractAddress = "0x84b9B910527Ad5C03A9Ca831909E21e236EA7b06"
        val from = ParticleNetwork.getAddress()
        val to = "0x4F96Fe3b7A6Cf9725f59d353F723c1bDb64CA6Aa"
        val amount = BigDecimal.valueOf(0.00001 * 10.0.pow(18.0)).toPlainString()
        binding.erc20Transfer.setOnClickListener {
//            ParticleNetwork.evm.erc20Transfer(contractAddress = "",to="", amount = )
            lifecycleScope.launch {
                try {
                    val contractParams = ContractParams.erc20Transform(contractAddress, to, amount)
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        from,
                        to,
                        amount,
                        type = "0x0",
                        contractParams = contractParams
                    )
                    LogUtils.d(iTxData, "hexData:${iTxData?.serialize()}")
                    ToastUtils.showLong("hexData:${iTxData?.serialize()}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtils.showLong(e.message)
                }

            }

        }
        binding.erc20Approve.setOnClickListener {
//            ParticleNetwork.evm.erc20Approve()
            lifecycleScope.launch {
                try {
                    val contractParams = ContractParams.erc20Approve(contractAddress, to, amount)
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        ParticleNetwork.getAddress(),
                        to,
                        amount,
                        type = "0x0",
                        contractParams = contractParams
                    )
                    LogUtils.d(iTxData, "hexData:${iTxData?.serialize()}")
                    ToastUtils.showLong("hexData:${iTxData?.serialize()}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtils.showLong(e.message)
                }

            }
        }
        binding.erc20TransferFrom.setOnClickListener {
//            ParticleNetwork.evm.erc20TransferFrom()
            lifecycleScope.launch {
                try {
                    val contractParams =
                        ContractParams.erc20TransferFrom(contractAddress, from, to, amount)
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        from,
                        to,
                        amount,
                        type = "0x0",
                        contractParams = contractParams
                    )
                    LogUtils.d(iTxData, "hexData:${iTxData?.serialize()}")
                    ToastUtils.showLong("hexData:${iTxData?.serialize()}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtils.showLong(e.message)
                }

            }
        }
        binding.erc721SafeTransferFrom.setOnClickListener {
//            ParticleNetwork.evm.erc721SafeTransferFrom()
//            ToastUtils.showLong(getString(R.string.api_tips))
            lifecycleScope.launch {
                try {
                    val contractParams =
                        ContractParams.erc721SafeTransferFrom(contractAddress, from, to, amount)
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        from,
                        to,
                        amount,
                        type = "0x0",
                        contractParams = contractParams
                    )
                    LogUtils.d(iTxData, "hexData:${iTxData?.serialize()}")
                    ToastUtils.showLong("hexData:${iTxData?.serialize()}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtils.showLong(e.message)
                }
            }
        }
        binding.erc1155SafeTransferFrom.setOnClickListener {
//            ParticleNetwork.evm.erc1155SafeTransferFrom()

            lifecycleScope.launch {
                try {
                    val id = ""
                    val data = "0x0"
                    val contractParams =
                        ContractParams.erc1155SafeTransferFrom(
                            contractAddress,
                            from,
                            to,
                            id,
                            amount,
                            data
                        )
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        ParticleNetwork.getAddress(),
                        to,
                        amount,
                        type = "0x0",
                        contractParams = contractParams
                    )
                    LogUtils.d(iTxData, "hexData:${iTxData?.serialize()}")
                    ToastUtils.showLong("hexData:${iTxData?.serialize()}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtils.showLong(e.message)
                }
            }

        }
        binding.customAbi.setOnClickListener {
            ToastUtils.showLong(getString(R.string.api_tips))
//            ParticleNetwork.evm.erc1155SafeTransferFrom()
            lifecycleScope.launch {
                try {
                    val contractParams =
                        ContractParams.customAbiEncodeFunctionCall(
                            contractAddress, "custom_revealMysteryBox",
                            listOf(926), ""
                        )
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        from,
                        to,
                        amount,
                        type = "0x0",
                        contractParams = contractParams
                    )
                    LogUtils.d(iTxData, "hexData:${iTxData?.serialize()}")
                    ToastUtils.showLong("hexData:${iTxData?.serialize()}")
                } catch (e: Exception) {
                    ToastUtils.showLong(e.message)
                }

            }
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
                    ParticleNetwork.getAddress(),
                    "BBBsMq9cEgRf9jeuXqd6QFueyRDhNwykYz63s1vwSCBZ",
                    2 * 10.0.pow(9.0).toLong()
                )
                val result =
                    ParticleNetwork.solana.serializeTransaction(req).result
                val message = result.transaction.serialized
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
                    ParticleNetwork.getAddress(),
                    "BBBsMq9cEgRf9jeuXqd6QFueyRDhNwykYz63s1vwSCBZ",
                    2 * 10.0.pow(9.0).toLong()
                )
                val result =
                    ParticleNetwork.solana.serializeTransaction(req).result
                val message = result.transaction.serialized
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