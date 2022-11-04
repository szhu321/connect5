# Connect 5

## About

Connect 5 is a two-player party game where players fight to get the most points. Similar to connect 4 the players play on a game board where each player takes turns dropping their chips into a specific column. 

This project was created using Java8 and JavaFX for my Final Project in my Java I Course. It was created to challenge myself on using technology such as JavaFX for the GUI and Java sockets for online multiplayer.

One feature that I can improve on is the singleplayer AI. To do so, I would need to create a model that can obtain points while preventing the opponent from obtaining points.

For more details check out the following document: 
[Software Requirements Specification](https://github.com/szhu321/connect5/blob/master/Connect%205%20SRS.pdf)

### Features
- Local Two player.
- Online Two player.
- Online Chat.
- Multiple online game sessions at once.
- Chip falling animations.
- Chip connect four & connect five animations. 
- Scoring system.
- Singleplayer (with questionable AI).

## Installation

### Dependencies

- Java8
- JavaFX

### Setup

Clone/download the repository. Then using your favorite IDE open up the project. Make sure that both Java8 and JavaFX(should come with Java8) are included as dependencies(how this is done depends on the IDE).

- Run Game: Run src/main/Connect5.java.

- Run Server: Run src/server/Server.java.
    - Check out the [Software Requirements Specification](https://github.com/szhu321/connect5/blob/master/Connect%205%20SRS.pdf) for more information on how to setup the server.

## How to play

1. First, select a game mode. There is a singleplayer, local two-player, and online multiplayer.
2. Once inside a game, select a token by clicking on one of the three tokens at the bottom of the screen.
3. Then select a column. That token will then fall, either to the very bottom or the top of an existing token.
4. Getting points: 
    - Placing tokens will give you points equal to the number on the token.
    - Connecting 4 tokens of the same color will award you additional points based on the connected tokens.
5. Win condition: 
    - The game ends when someone connects 5 token of the same color or if the board is full. 
    - The player with the most points wins(if the points are the same it’s a tie).

## Credits

- Check out this [video](https://youtu.be/AubJaosfI-0) on how to setup JavaFX in VSCode.
- I got JDK8 w/JavaFX [here](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html).
