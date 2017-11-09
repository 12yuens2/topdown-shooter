# CS4303 Practical 2 Shooter

## Run instructions
- To build the game, run `ant` in the submission directory.
- To run the server, run `ant server`
- To run the client, run `ant -Darg0="name" client` where name can be any unique name.

For network play, start the game on the server before connecting with a client. Clients must each have a unique name.

## Game instructions
The goal of the game is to try and survive as long as possible. Avoid and shoot the enemies while collecting pickups. The pickups are important to be able to survive longer in the game. 
- Movement keys: WASD
- Attack: Left mouse button
- Reload: R
- Switch to pistol: 1
- Switch to rocket: 2
- Switch to machine gun: 3

While a weapon is reloading, it cannot be fired. Only the current active weapon will be reloading, so switching weapons will halt the reloading.

Press Enter to start the game.
