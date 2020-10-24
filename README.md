# Archived
I'm pretty sure this code *does not work!* I've archived it as I don't intend to develop it any further.

# VRWiimote
This is a driver for the [VRidge API](https://github.com/RiftCat/vridge-api) that lets you use Wii Remotes (aka Wiimotes) in VR.

### How it works (when I've finished it)
- Buttons on Wii remotes are mapped to controller buttons for SteamVR
- Orientation is taken from Wii remotes, and mapped to VR controllers
- Yaw is locked to player, as Wii remotes do not have magnetometers
- Position is determined as a set distance from the user according to the orientation of each remote
- Accelerometers in remotes can be used to temporarily move the controllers, so objects can be reached etc.

### Dependencies
- [WiiUseJ v0.13 (awvalenti fork)](https://github.com/awvalenti/wiiusej/tree/245e209d55f5258ec756351a227fb7739cb86701)
- Google GSON 2.8.1
- [JeroMQ 0.4.0](https://github.com/zeromq/jeromq)
