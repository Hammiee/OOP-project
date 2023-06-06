# OOP-project

Implementation of a game based on Monopoly

## What's different?
* Chance and Community cards are merged into one group
* Consist of simplified monopoly board (utilities fields are replaced with draw-card fields)
* Building, mortgaging and selling is done at the end of the player' turn

## Order of actions during player's turn

* If in jail, can use "free from the jail" card
* Throw the dice
 * move the indicated distance
  * collect $200 if land on or pass Go
 * resolve space
  * property fields
   * pay rent if owned and not mortgaged
   * buy it if unowned (optional)
   * auction property if still unowned (not optional)
  * action fields
   * resolve as indicated
    * collect $200 if resolving puts you passing Go
 * property actions
  * buy/sell houses/hotels
  * mortage property (must be unbuilt!)
  * sell/trade your property (must be unbuilt!)
  * unmortage property  
