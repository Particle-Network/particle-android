# Particle Android

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/network.particle/auth-service/badge.svg?style=flat)](https://search.maven.org/search?q=g:network.particle)

👉 [Download built version from Google Play](https://play.google.com/store/apps/details?id=network.particle.auth)

This repository contains [Auth Service](https://docs.particle.network/auth-service/introduction) and [Wallet Service](https://docs.particle.network/wallet-service/introduction) sample source. It supports Solana and all EVM-compatiable chains now, more chains and more features coming soon! Learn more visit [Particle Network](https://docs.particle.network/).

## Getting Started

* Clone the repo.
* Create `local.properties` in project root dir if not exist.
* Add below particle sdk config to `gradle.properties`.   

```
particle.network.project_client_key=xxx
particle.network.project_id=xxx
particle.network.app_id=xxx
```

  Replace `xxx` with the new values created in the [Dashboard](https://dashboard.particle.network/#/login).

## Build

```
./gradlew assembleDebug
```

## Features

1. Auth login with email, phone, facebook, google, apple etc.
2. Logout.
3. Open Wallet.
4. Change Chain Id.
5. Check our official dev docs: https://docs.particle.network/

## Docs

1. https://docs.particle.network/auth-service/sdks/android
2. https://docs.particle.network/wallet-service/sdks/android

## Give Feedback

Please report bugs or issues to [particle-android/issues](https://github.com/Particle-Network/particle-android/issues)

You can also join our [Discord](https://discord.gg/2y44qr6CR2).
