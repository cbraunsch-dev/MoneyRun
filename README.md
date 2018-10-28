# MoneyRun
Developed in March 2011

![cover image](https://github.com/cbraunsch-dev/MoneyRun/raw/master/Graphics/Graphics/Deployment/highresicon.png)

## Premise and basic mechanic
The premise of the game is that the player is supposed to steal bags of money and escape. The mechanic can be summed up as follows:
- Touch a cell to move the player (player cannot move in diagonals, only in straight lines)
- Collect a bag of money
- Bring the bag to a bank
- Escape the level

Do this without getting caught by the enemy guards.

## Target platform
The game was developed for Android. It has not been updated since it's release and would probably need to be opened in Eclipse and not Android Studio.

## Technologies used
- The [AndEngine](http://www.andengine.org) game engine was used to develop the game. Their github page can be found [here](https://github.com/nicolasgramlich/AndEngine).
- The A-Star algorithm was used to implement path finding for the enemy AI.
- The game utilizes a tile engine to render levels
