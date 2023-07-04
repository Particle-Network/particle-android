package network.particle.demo.utils

import com.particle.api.evm
import com.particle.api.infrastructure.net.data.EvmReqBodyMethod
import com.particle.api.infrastructure.net.data.SerializeSOLTransReq
import com.particle.api.service.EvmService
import com.particle.api.solana
import com.particle.base.ParticleNetwork
import com.particle.base.model.FeeMarketEIP1559TxData
import com.particle.base.model.ITxData
import com.particle.base.model.TxAction
import com.particle.base.model.TxData
import com.particle.base.utils.gweiToHexStr
import com.particle.base.utils.toHexStr
import com.particle.gui.ParticleWallet.getUserPublicKey
import com.particle.network.ParticleNetworkAuth.getAddress
import okhttp3.internal.toHexString
import org.json.JSONObject

object TransactionMock {

    suspend fun mockSolanaTransaction(): String {
        val req = SerializeSOLTransReq(
            ParticleNetwork.getAddress(),
            TestAccount.solana().receiverAddress,
            TestAccount.solana().amount
        )
        val result =
            ParticleNetwork.solana.serializeTransaction(req).result
        val message = result.transaction.serialized
        return message
    }

    suspend fun mockEvmSendNativeTransactionFast(): String {
        val from: String = ParticleNetwork.getAddress()
        val to = TestAccount.evm().receiverAddress
        val amount = TestAccount.evm().amount.toHexString()
        return ParticleNetwork.evm.createTransaction(from, to, "0x$amount")!!.serialize()
    }

    suspend fun mockEvmSendNativeTransactionCustom(): String {
        val from: String = ParticleNetwork.getAddress()
        val to = TestAccount.evm().receiverAddress
        val amount = TestAccount.evm().amount.toHexString()
        val gasLimit = getEvmTransGasLimit(from, to)
        val suggestedGasFees = ParticleNetwork.evm.suggestedGasFees()
        val high = suggestedGasFees.result.high
        val transaction: ITxData
        if (ParticleNetwork.chainInfo.chainName.evm1559support) {
            transaction = FeeMarketEIP1559TxData(
                high.maxPriorityFeePerGas.gweiToHexStr(),
                high.maxFeePerGas.gweiToHexStr(),
                chainId = ParticleNetwork.chainId.toString().toHexStr(),
                from = from,
                to = to,
                value = "0x$amount",
                gasLimit = gasLimit,
                data = "0x",
                nonce = "0x0",
            )
        } else {
            transaction = TxData(
                chainId = ParticleNetwork.chainId.toString().toHexStr(),
                from = ParticleNetwork.getUserPublicKey(),
                to = to,
                value = "0x$amount",
                data = "0x",
                nonce = "0x0",
                gasPrice = high.maxFeePerGas.gweiToHexStr(),
                gasLimit = gasLimit,
                action = TxAction.normal.toString(),
            )
        }
        return transaction.serialize()
    }

    private suspend fun getEvmTransGasLimit(from: String, to: String, value: String = "0x0", data: String = "0x"): String {
        val map = HashMap<String, String>()
        map["from"] = from
        map["to"] = to
        map["value"] = value
        map["data"] = data
        val params = arrayListOf(map)
        val resp = ParticleNetwork.evm.rpc(EvmReqBodyMethod.ethEstimateGas.value, params)
        val jobj = JSONObject(resp.string())
        val gasLimit = jobj.getString("result")
        return gasLimit
    }

}