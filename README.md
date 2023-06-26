# OOP-project

Implementation of a game based on Monopoly

## What's different?
* Chance and Community cards are merged into one group
* Consist of simplified monopoly board (utilities fields are replaced with draw-card fields)
* Building, mortgaging and selling is done at the end of the player' turn

## Order of actions during player's turn

* Throw the dice
    * move the indicated distance
        * collect $200 if land on or pass Go
* Move space
    * property fields
        * pay rent if owned and not mortgaged
        * buy it if unowned (optional)
        * auction property if still unowned (not optional)
    * action fields
        * resolve as indicated
        * collect $200 if resolving puts you passing Go
* Property actions
    * buy/sell houses/hotels
    * mortage property (must be unbuilt!)
    * sell/trade your property (must be unbuilt!)
    * unmortage property

## My Monopoly game consists of the following classes
- Game
- Player
- Card
- Board
- Field
- ActionField
- PropertyField
- Street
- Railroad

Game
--
The main class with attributes:
  - players: a list of all the players participating in the game.
  - board: the structure of the game board.
  - cards: a list of all the Monopoly cards.
  - turn: an integer indicating whose turn it is.
  - round: a counter for the rounds.
  - int and string scanners for input.
  - playerRemoved: a helper variable indicating the number of players who went bankrupt or surrendered during the round.

and methods:
  - Setters and getters for the attributes.
  - drawCard(): returns a random card from the card stack.
  - randomNumber(range): returns a random number within the specified range.
  - shuffle(): shuffles the cards and adds them to the cards attribute.
  - initializeGame(): starts the game with the chosen number of players.
  - startRound(): manages the changing of turns for each round.
  - startTurn(): implements the turn by moving the player, calling the afterMove() function, allowing the player to buy/sell properties and buildings, mortgage/unmortgage properties, and surrendering. It also removes a player if they go bankrupt.
  - afterMove(): calls the appropriate action if the player lands on an ActionField, pays rent if they land on an owned property, allows the player to buy the property or initiates a bidding process.
  - surrender(): returns the properties of a surrendering player to the game.
  - performAction(): called when a player lands on an ActionField.
  - message(): prints a message to the user.
  - rollDice(): implements dice rolling.

Player
--
A class with attributes:
  - name: the name of the player.
  - money: all players start with $1500.
  - position: the current position of the player.
  - ownedCards: a list of cards that can free the player from jail.
  - ownedProperties: a list of properties owned by the player.
  - mortgagedProperties: a list of properties mortgaged by the player.

and methods:
  - Setters and getters for the attributes.
  - message(): prints a message to the user.
  - bid(): allows all players to participate in the bidding process for a property, with the winner buying it.
  - sell(): implements the selling of a property for a specified amount plus the property's cost.
  - addBuildings(): allows the player to build a hotel or house if they have the necessary resources.
  - putOffMortgage(): allows the player to unmortgage a property.
  - putUnderMortgage(): allows the player to mortgage a property if they don't have buildings on it or if it's a property of a different color.
  - sellProperty(): allows the player to choose and sell a property.
  - sellBuildings(): allows the player to choose and sell a building.
  - move(): moves the player a specified number of steps and adds $200 if they pass the "GO!" field.
  - buy(): implements the purchase of a field and calls the corresponding field function.

Card
--
A class with attributes:
  - info: the message from the card to the player.
  - action: a number that helps implement the action from the card.
  - amount: the amount of money given or taken from the player.

and methods:
  - Setters and getters for the attributes.
  - findRailroad(): finds the nearest railroad from a given position.
  - payForBuildings(): a helper function for checkCard() that allows the player to pay a specified sum for all their buildings.
  - checkCard(): goes through all the cards and implements the specified actions

.

Board
--
A class with an attribute:
  - fields: a list of the 40 fields on the Monopoly board.

and methods:
  - Setters and getters for the attribute.

Field
--
An abstract class with an attribute:
  - type.

and methods:
  - Setters and getters for the attribute.

ActionField
--
An abstract class that extends Field and has an attribute:
  - info.

and methods:
  - Setters and getters for the attribute.

PropertyField
--
An abstract class that extends Field and has attributes:
  - name: the name of the field on the Monopoly board.
  - owner: the owner of the property (null if unowned).
  - price: the price at which the property can be bought.
  - mortgaged: a flag indicating whether the property is mortgaged.

and methods:
  - Setters and getters for the attributes.
  - payRent(): allows the specified player to pay rent to the property owner.
  - sell(): changes the ownedProperties and monopolies of Railroads after the player sells a property.
  - setMonopol(): a helper function for sell().
  - changeRailroads(): a helper function for sell().
  - buyField(): allows the player to buy the field and adjusts the rent and monopolies of Streets and Railroads if necessary.

Street
--
A class that extends PropertyField and has attributes:
  - color: the color of the street.
  - buildings: a list of buildings the player has on this property.
  - monopol: a flag indicating whether the player has a monopoly for this color.
  - rentCostHoused: a list of rent prices depending on the number of buildings.
  - housePrice: the cost of a house.
  - hotelPrice: the cost of a hotel (without the 4 houses!).

and methods:
  - Setters and getters for the attributes.
  - build(): allows the player to build 1 house or hotel.

Railroad
--
A class that extends PropertyField and has attributes:
  - numRailroads: the number of railroads owned by the owner (0 if unowned).
  - rent: the rent price depending on the number of railroads (25 if unowned).

and methods:
  - Setters and getters for the attributes.
