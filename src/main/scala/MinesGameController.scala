package co.torri.scalamines.controller

import co.torri.scalamines.MinesGame

object gameController {
    private var openGames = Map[String,MinesGame]()
    def get(id: String): MinesGame = openGames(id)
    def create(size: Int, mines: Int): MinesGame = {
        var game = new MinesGame(size, mines)
        openGames += (game.hashCode.toString -> game)
        game
    }
}