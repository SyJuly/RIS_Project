# RIS_Project

Represents a realtime interactive system, a simple 2D-Platformer game playable as Single-Player (PVE) or Multiplayer (PVE/PVP) with an infinite, generated world. The following components are included:

![alt class diagram](https://github.com/SyJuly/RIS_Projekt/blob/refactor_world_updating/architecture.jpg)

#### GameServer
_3 (+2 per client) threads_
- Procedural world generation
- Handling static and dynamic objects
- Gravity-based player moment
- Minimalistic KI
- AABB Collision detection
- Event handling
- Network communication for every client


#### GameClient
_3 threads_
- Input handling
- Camera handling
- Procedural world generation
- Object updating
- Minimalistic movement prediction
- Rendering dynamic and static objects
