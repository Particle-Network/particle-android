package network.particle.demo.utils

import java.math.BigInteger

data class TestAccount(
    val privateKey: String,
    val mnemonic: String,
    val tokenContractAddress: String,
    val amount: Long,
    val nftContractAddress: String,
    val nftTokenId: String,
    val receiverAddress: String
) {
    companion object {
        fun evm(): TestAccount {
            return TestAccount(
                "eacd18277e3cfca6446801b7587c9d787d5ee5d93f6a38752f7d94eddadc469e",
                "hood result social fetch pet code check yard school jealous trick lazy",
                "0x326C977E6efc84E512bB9C30f76E30c160eD06FB",
                1000000000000000,
                "0xD000F000Aa1F8accbd5815056Ea32A54777b2Fc4",
                "1412",
                "0xAC6d81182998EA5c196a4424EA6AB250C7eb175b"
            )
        }

        fun solana(): TestAccount {
            return TestAccount(
                "5fBYPZdP5nqH5DSAjgjMi4aSf113m5PuavakojZ7C9svt1i8vyq26pXpEf1Suivg91TUAp7TX1pqK49rgXQfAAjT",
                "vacant focus country eye wine where lady doll boat sort ticket grab",
                "GobzzzFQsFAHPvmwT42rLockfUCeV3iutEkK218BxT8K",
                10000000,
                "HLyQCnxBo5SGmYBv3aRCH9tPqT9TvexHY2JaGnqvfWuw",
                "",
                "9LR6zGAFB3UJcLg9tWBQJxEJCbZh2UTnSU14RBxsK1ZN"
            );
        }
    }
}