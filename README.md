# JavaVPN

A simple Java-based VPN server + client application, built to demonstrate basic networking, tunnelling and multi-client support using Java.

---

## Table of Contents

- [Overview](#overview)  
- [Features](#features)  
- [Architecture](#architecture)  
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Installation](#installation)  
  - [Configuration](#configuration)  
  - [Running the Application](#running-the-application)  
- [Usage](#usage)  
- [Logging & Configuration](#logging-–-configuration)  
- [Threading & Multi-Client Support](#threading-–-multi-client-support)  
- [Development](#development)  
- [Roadmap & Future Work](#roadmap-–-future-work)  
- [Contributing](#contributing)  
- [License](#license)  

---

## Overview

JavaVPN is a teaching/prototyping project that implements a basic VPN system in Java.  
The system consists of a server component and one or more clients; traffic from the clients is funneled through the server (tunnelled) so that clients can appear as if they are on the same network or reroute their traffic.  

This project was created as a personal project and learning exercise, focusing on:
- Extracting configuration into `.properties` files  
- Logging infrastructure (via `LoggerUtil`)  
- Tunnel management (`TunnelManager`)  
- Packet handling (`PacketHandler`)  
- Threading / concurrency for multiple client support  
- Clean separation of concerns via JavaFX UI or headless mode (if present)  

---

## Features

- Basic VPN server (accepts multiple clients)  
- Client application to connect to server and tunnel traffic  
- Configuration via `.properties` file  
- Logging via the `LoggerUtil` class  
- Modular architecture (server → `TunnelManager`, `PacketHandler`)  
- Multi-threading support so multiple clients can connect simultaneously  
- (Optional) JavaFX UI for client/management interface (if implemented) 
