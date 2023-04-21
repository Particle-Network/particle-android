package network.particle.demo.ui


import android.view.View
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.connect.common.utils.Base58Utils
import com.connect.common.utils.HexUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gyf.immersionbar.ImmersionBar
import com.minijoy.demo.R
import com.minijoy.demo.databinding.ActivityAuthDemoBinding
import com.particle.base.*
import com.particle.network.ParticleNetworkAuth.fastLogout
import com.particle.network.ParticleNetworkAuth.getAddress
import com.particle.network.ParticleNetworkAuth.getUserInfo
import com.particle.network.ParticleNetworkAuth.isLogin
import com.particle.network.ParticleNetworkAuth.isLoginAsync
import com.particle.network.ParticleNetworkAuth.login
import com.particle.network.ParticleNetworkAuth.logout
import com.particle.network.ParticleNetworkAuth.openAccountAndSecurity
import com.particle.network.ParticleNetworkAuth.openWebWallet
import com.particle.network.ParticleNetworkAuth.setChainInfo
import com.particle.network.ParticleNetworkAuth.setSecurityAccountConfig
import com.particle.network.ParticleNetworkAuth.setUserInfo
import com.particle.network.ParticleNetworkAuth.signAllTransactions
import com.particle.network.ParticleNetworkAuth.signAndSendTransaction
import com.particle.network.ParticleNetworkAuth.signMessage
import com.particle.network.ParticleNetworkAuth.signTransaction
import com.particle.network.ParticleNetworkAuth.signTypedData
import com.particle.network.SignTypedDataVersion
import com.particle.network.service.*
import com.particle.network.service.model.*
import kotlinx.coroutines.launch
import network.particle.demo.ui.adapter.ChainInfoChoiceListAdapter
import network.particle.demo.ui.base.DemoBaseActivity
import network.particle.demo.utils.DemoChainUtils
import network.particle.demo.utils.ParticleInitUtils
import network.particle.demo.utils.StreamUtils
import network.particle.demo.utils.TransactionMock


class AuthDemoActivity : DemoBaseActivity<ActivityAuthDemoBinding>(R.layout.activity_auth_demo) {

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).statusBarColor(android.R.color.transparent).titleBar(
            binding.toolbar
        ).init()
    }

    var currChainInfo: ChainInfo? = null
    override fun setListeners() {
        super.setListeners()
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.selectChain.setOnClickListener {
            val chainInfos = DemoChainUtils.getAllChainInfo()
            MaterialAlertDialogBuilder(this@AuthDemoActivity).setTitle(getString(R.string.pn_select_chain))
                .setSingleChoiceItems(ChainInfoChoiceListAdapter(this@AuthDemoActivity, chainInfos), 0) { dialog, which ->
                    val chainInfo = chainInfos[which]
                    currChainInfo = chainInfo
                    ParticleNetwork.setChainInfo(currChainInfo!!)  //set chain info
                    updateChainName()
                    updateAddress()
                    dialog.dismiss()
                }.show()
        }
        binding.init.setOnClickListener {
            if (checkCurrChainInfo()) return@setOnClickListener
            ParticleInitUtils.initAuth(this@AuthDemoActivity, currChainInfo!!)
        }
        binding.login.setOnClickListener {
            val supportAuthTypeAll = SupportAuthType.ALL.value
            //if you want to use google or facebook login, you can set the authType to SupportAuthType.GOOGLE.value or SupportAuthType.FACEBOOK.value
            val supportAuthType2 = SupportAuthType.GOOGLE.value or SupportAuthType.FACEBOOK.value
            ParticleNetwork.login(LoginType.EMAIL,
                supportAuthTypeAll,
                loginFormMode = false,
                loginCallback = object : WebServiceCallback<LoginOutput> {
                    override fun success(output: LoginOutput) {
                        updateAddress()
                    }

                    override fun failure(errMsg: WebServiceError) {
                        ToastUtils.showLong(errMsg.message)
                    }
                })
        }

        binding.openWebWallet.setOnClickListener {
            ParticleNetwork.openWebWallet()
        }

        binding.isLogin.setOnClickListener {
            showMessageDialog(getString(R.string.pn_is_login), ParticleNetwork.isLogin().toString())
        }

        binding.isLoginAsync.setOnClickListener {
            lifecycleScope.launch {
                val userInfo: UserInfo? = ParticleNetwork.isLoginAsync()
                showMessageDialog(getString(R.string.pn_is_login_async), GsonUtils.toJson(userInfo))
            }
        }

        binding.getAddress.setOnClickListener {
            val address = ParticleNetwork.getAddress()
            showMessageDialog(getString(R.string.pn_get_address), address)
        }

        binding.getUserInfo.setOnClickListener {
            val userInfo: UserInfo? = ParticleNetwork.getUserInfo()
            showMessageDialog(getString(R.string.pn_get_userinfo), GsonUtils.toJson(userInfo))
        }

        binding.logout.setOnClickListener {
            ParticleNetwork.logout(object : WebServiceCallback<WebOutput> {
                override fun success(output: WebOutput) {
                    currChainInfo = null
                    ToastUtils.showLong(getString(R.string.pn_logout_success))
                }

                override fun failure(errMsg: WebServiceError) {
                    ToastUtils.showLong(errMsg.message)
                }
            })
        }

        binding.fastLogout.setOnClickListener {
            ParticleNetwork.fastLogout(object : FastLogoutCallBack {
                override fun success() {
                    currChainInfo = null
                    ToastUtils.showLong(getString(R.string.pn_logout_success))
                }

                override fun failure() {
                    ToastUtils.showLong(getString(R.string.pn_fastlogout_failed))
                }
            })
        }

        binding.signMessage.setOnClickListener {
            if (checkCurrChainInfo()) return@setOnClickListener
            if (checkIsLogin()) return@setOnClickListener
            val message = "Hello Particle Network"
            val encodeMessage = if (currChainInfo is EvmChain) {
                HexUtils.encodeWithPrefix(message.toByteArray(Charsets.UTF_8))
            } else {
                Base58Utils.encode(message.toByteArray(Charsets.UTF_8))
            }
            ParticleNetwork.signMessage(encodeMessage, object : WebServiceCallback<SignOutput> {
                override fun success(output: SignOutput) {
                    showMessageDialog(getString(R.string.pn_sign_message), output.signature!!)
                }

                override fun failure(errMsg: WebServiceError) {
                    showMessageDialog(getString(R.string.pn_sign_message), "code:${errMsg.code} \nmessage:${errMsg.message}")
                }
            })
        }

        binding.signTransaction.setOnClickListener {
            if (checkCurrChainInfo()) return@setOnClickListener
            if (checkIsLogin()) return@setOnClickListener
            if (currChainInfo!!.isEvmChain()) {
                ToastUtils.showLong(R.string.pn_only_solana_support)
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val transaction = TransactionMock.mockSolanaTransaction()
                LogUtils.d("signTransaction", transaction)
                ParticleNetwork.signTransaction(transaction, object : WebServiceCallback<SignOutput> {
                    override fun success(output: SignOutput) {
                        showMessageDialog(getString(R.string.pn_sign_transaction), output.signature!!)
                    }

                    override fun failure(errMsg: WebServiceError) {
                        showMessageDialog(getString(R.string.pn_sign_transaction), "code:${errMsg.code} \nmessage:${errMsg.message}")
                    }
                })
            }

        }

        binding.signAllTransactions.setOnClickListener {
            if (checkCurrChainInfo()) return@setOnClickListener
            if (checkIsLogin()) return@setOnClickListener
            if (currChainInfo!!.isEvmChain()) {
                ToastUtils.showLong(R.string.pn_only_solana_support)
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val transactions1 = TransactionMock.mockSolanaTransaction()
                val transactions2 = TransactionMock.mockSolanaTransaction()
                ParticleNetwork.signAllTransactions(listOf(transactions1, transactions2), object : WebServiceCallback<SignOutput> {
                    override fun success(output: SignOutput) {
                        showMessageDialog(getString(R.string.pn_sign_all_transactions), output.signature!!)
                    }

                    override fun failure(errMsg: WebServiceError) {
                        showMessageDialog(getString(R.string.pn_sign_all_transactions), "code:${errMsg.code} \nmessage:${errMsg.message}")
                    }
                })
            }

        }

        binding.signSendTransaction.setOnClickListener {
            if (checkCurrChainInfo()) return@setOnClickListener
            if (checkIsLogin()) return@setOnClickListener
            lifecycleScope.launch {
                val transaction: String = if (currChainInfo!!.isSolanaChain()) {
                    TransactionMock.mockSolanaTransaction()
                } else {
                    TransactionMock.mockEvmSendNativeTransactionFast()
                    //or you can use TransactionMock.mockEvmSendNativeTransactionCustom()
                }
                LogUtils.d("signSendTransaction", transaction)
                ParticleNetwork.signAndSendTransaction(transaction, object : WebServiceCallback<SignOutput> {
                    override fun success(output: SignOutput) {
                        showMessageDialog(getString(R.string.pn_sign_send_transaction), output.signature!!)
                    }

                    override fun failure(errMsg: WebServiceError) {
                        showMessageDialog(getString(R.string.pn_sign_send_transaction), "code:${errMsg.code} \nmessage:${errMsg.message}")
                    }
                })
            }

        }

        binding.signTypedData.setOnClickListener {
            if (checkCurrChainInfo()) return@setOnClickListener
            if (checkIsLogin()) return@setOnClickListener
            if (currChainInfo!!.isSolanaChain()) {
                ToastUtils.showLong(R.string.pn_only_evm_support)
                return@setOnClickListener
            }
            val message = StreamUtils.getRawString(resources, R.raw.typed_data)
            val hexMessage = HexUtils.encodeWithPrefix(message.toByteArray(Charsets.UTF_8))
            ParticleNetwork.signTypedData(hexMessage, SignTypedDataVersion.V4, object : WebServiceCallback<SignOutput> {
                override fun success(output: SignOutput) {
                    showMessageDialog(getString(R.string.pn_sign_typed_data), output.signature!!)
                }

                override fun failure(errMsg: WebServiceError) {
                    showMessageDialog(getString(R.string.pn_sign_typed_data), "code:${errMsg.code} \nmessage:${errMsg.message}")
                }
            })
        }

        // If you login to solana, you do not have an evm wallet address, you need to call this method to switch to the evm chain, this method will create an evm wallet
        binding.setChainInfoSync.setOnClickListener {
            ParticleNetwork.setChainInfo(EthereumChain(EthereumChainId.Goerli), object : ChainChangeCallBack {
                override fun success() {
                    showMessageDialog(getString(R.string.pn_set_chaininfo_sync), "success")
                }

                override fun failure() {
                    showMessageDialog(getString(R.string.pn_set_chaininfo_sync), "failure")
                }
            })
        }

        binding.getChainInfo.setOnClickListener {
            val chainInfo = ParticleNetwork.chainInfo
            val chainInfoStr = "${chainInfo.chainName.displayName} ${chainInfo.chainId}(${chainInfo.chainId.value()})"
            showMessageDialog(getString(R.string.pn_get_chaininfo), chainInfoStr)
        }

        binding.openAccountSecurity.setOnClickListener {
            ParticleNetwork.openAccountAndSecurity(object : WebServiceCallback<WebOutput> {
                override fun success(output: WebOutput) {

                }

                override fun failure(errMsg: WebServiceError) {
                    if (errMsg.code == 10005 || errMsg.code == 8005) {
                        //You've been knocked out.
                    }
                }
            })
        }

        binding.setSecurityAccountConfig.setOnClickListener {
            //0-> Do not prompt
            //1-> prompt once
            //2-> prompt every time
            val config = SecurityAccountConfig(promptSettingWhenSign = 1, promptMasterPasswordSettingWhenLogin = 0)
            ParticleNetwork.setSecurityAccountConfig(config)
        }

        binding.setLanguage.setOnClickListener {
            val isRelaunchApp = true //True to relaunch app, false to recreate all activities.
            ParticleNetwork.setAppliedLanguage(LanguageEnum.EN, isRelaunchApp)
        }

        //If you have logged in to Particle on other platforms, you can log in directly using json without having to jump to Particle again.
        binding.setUserInfo.setOnClickListener {
            val userInfoJson = ""
            ParticleNetwork.setUserInfo(userInfoJson)
        }

    }

    private fun checkCurrChainInfo(): Boolean {
        if (currChainInfo == null) {
            ToastUtils.showLong(R.string.pn_select_chain_please)
            return true
        }
        return false
    }

    private fun checkIsLogin(): Boolean {
        if (!ParticleNetwork.isLogin()) {
            ToastUtils.showLong(R.string.pn_not_login)
            return true
        }
        return false
    }

    fun updateAddress() {
        if (ParticleNetwork.isLogin()) {
            binding.address.text = ParticleNetwork.getAddress()
            binding.address.visibility = View.VISIBLE
        }
    }

    private fun updateChainName() {
        currChainInfo?.apply {
            binding.chainName.text = "${chainName.displayName} ${chainId}(${chainId.value()})"
            binding.chainName.visibility = View.VISIBLE
        }
    }

    private fun showMessageDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this@AuthDemoActivity).setTitle(title).setMessage(message).show()
    }


}