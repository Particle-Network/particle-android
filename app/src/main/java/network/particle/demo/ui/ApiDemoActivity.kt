package network.particle.demo.ui

import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.connect.common.TransactionCallback
import com.connect.common.model.ConnectError
import com.gyf.immersionbar.ImmersionBar
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityApiDemoBinding
import com.particle.api.evm
import com.particle.api.service.data.ContractParams
import com.particle.auth.AuthCore
import com.particle.base.ParticleNetwork
import com.particle.base.model.MobileWCWallet
import com.particle.base.model.MobileWCWalletName
import com.particle.connect.ParticleConnect
import com.particle.gui.utils.WalletUtils
import kotlinx.coroutines.launch
import network.particle.demo.ui.base.DemoBaseActivity
import java.math.BigDecimal
import kotlin.math.pow

class ApiDemoActivity : DemoBaseActivity<ActivityApiDemoBinding>(R.layout.activity_api_demo) {
    override fun initView() {
        super.initView()
        ImmersionBar.with(this).statusBarColor(android.R.color.transparent).titleBar(
            binding.toolbar
        ).init()
    }

    override fun setListeners() {
        super.setListeners()
        binding.toolbar.setNavigationOnClickListener { finish() }
        val contractAddress = "0x84b9B910527Ad5C03A9Ca831909E21e236EA7b06"
        val from = "0x4F96Fe3b7A6Cf9725f59d353F723c1bDb64CA6Aa"
        val to = "0x4F96Fe3b7A6Cf9725f59d353F723c1bDb64CA6Aa"
        val amount = BigDecimal.valueOf(0.00001 * 10.0.pow(18.0)).toPlainString()
        val address = if (ParticleNetwork.chainInfo.isEvmChain()) {
            AuthCore.evm.getAddress()
        } else {
            AuthCore.solana.getAddress()
        }
        binding.erc20Transfer.setOnClickListener {

            lifecycleScope.launch {
                try {
                    val contractParams = ContractParams.erc20Transfer(contractAddress, to, amount)
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        from, contractParams = contractParams
                    )

                    val adapter = ParticleConnect.getAdapters()
                        .first { it.name == MobileWCWalletName.AuthCore.name }
                    val data = iTxData!!.serialize()
                    adapter.signAndSendTransaction(
                        address!!,
                        data,
                        object : TransactionCallback {
                            override fun onError(error: ConnectError) {
                                LogUtils.d("sign onError")
                            }

                            override fun onTransaction(transactionId: String?) {
                                LogUtils.d("sign onTransaction")
                            }
                        })

//                    LogUtils.d(iTxData, "hexData:${iTxData?.serialize()}")
//                    ToastUtils.showLong("hexData:${iTxData?.serialize()}")
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
                        address!!,
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
                        from, to, amount, type = "0x0", contractParams = contractParams
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
                        from, to, amount, type = "0x0", contractParams = contractParams
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
                    val contractParams = ContractParams.erc1155SafeTransferFrom(
                        contractAddress, from, to, id, amount, data
                    )
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        address!!,
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
            ToastUtils.showLong(getString(R.string.pn_api_tips))
//            ParticleNetwork.evm.erc1155SafeTransferFrom()
            lifecycleScope.launch {
                try {
                    val contractParams = ContractParams.customAbiEncodeFunctionCall(
                        contractAddress, "custom_revealMysteryBox", listOf(926), ""
                    )
                    val iTxData = ParticleNetwork.evm.createTransaction(
                        from, to, amount, type = "0x0", contractParams = contractParams
                    )
                    LogUtils.d(iTxData, "hexData:${iTxData?.serialize()}")
                    ToastUtils.showLong("hexData:${iTxData?.serialize()}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtils.showLong(e.message)
                }

            }
        }
        binding.rpc.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val from = address!!
                    val to = "0xAC6d81182998EA5c196a4424EA6AB250C7eb175b"
                    val data = "0x"
                    // Integer block number, or the string 'latest', 'earliest' or 'pending'
                    val quantity = "latest"
                    val result = ParticleNetwork.evm.rpc("eth_call")
                    ToastUtils.showLong(result.string())
                } catch (e: Exception) {
                    e.printStackTrace()
                    ToastUtils.showLong(e.message)
                }

            }


        }
    }
}