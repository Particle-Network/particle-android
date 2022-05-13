# Particle Android

This repository contains [Auth Service](https://docs.particle.network/auth-service/introduction) and [Wallet Service](https://docs.particle.network/wallet-service/introduction) sample source. It supports Solana now, more chains and more features coming soon! Learn more visit [Particle Network](https://docs.particle.network/).

## Getting Started

* Clone the repo.
* Create `local.properties` in project root dir if not exist.
* Add below particle sdk config.   

```
particle.network.project_id=xxx  
particle.network.project_client_key=xxx      
particle.network.app_id=xxx
```
Replace `xxx` with the new values created in the [Dashboard](https://particle.network/#/login).

## Build
```
./gradlew assembleDebug
```

## Features

1. Auth login with email or phone.
2. Logout.
3. Open Wallet.
4. Change Chain Id.

## Give Feedback
Please report bugs or issues to [particle-android/issues](https://github.com/Particle-Network/particle-android/issues)

You can also join our [Discord](https://discord.com/invite/qwysge6cgF).
