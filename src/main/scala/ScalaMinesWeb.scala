package co.torri.scalamines.web

import org.scalatra._
import scalate.ScalateSupport
import co.torri.scalamines._
import co.torri.scalamines.controller.gameController

class ScalaMinesWeb extends ScalatraServlet with ScalateSupport {
    
    before { contentType = "application/json" }
    
    get("/") {
        contentType = "text/html"
        templateEngine.layout("/WEB-INF/scalate/templates/index.scaml")
    }
    
    get("/game/new/:difficulty") {
        redirect("/game/" + (params("difficulty") match {
            case "normal" => gameController.create(20,60)
            case "hard" => gameController.create(30,110)
            case "easy" | _ => gameController.create(10,10)
        }).hashCode.toString)
    }
    
    get("/game/:id") {
        contentType = "text/html"
        <html><head>
        <script src="/js/jquery-1.5.min.js"></script>
        <script src="/js/gamescript.js"></script>
        </head>
        <body>{  {for (i <- (0 until game.boardSize)) yield <div class="line" style="height: 31px;float: left;clear: left;">{ for (j <- (0 until game.boardSize)) yield <div class="square" data-row={i.toString} data-column={j.toString} style="width: 30px;height: 30px;border: solid 1px black;display: inline-block;float: left;text-align: center;line-height: 30px;font-weight: bold;"> </div>}</div> }  }</body></html>
    }
    
    get("/game/:id/status") { game.toJSON }
    
    post("/game/:id/:action/:row/:column") {
        
        try {
            params("action") match {
                case "reveal" => square.reveal
                case "reveal-neighbours" => square.revealNeighbours
                case "flag" => square.flag = !square.flag
                case _ => throw new Exception("Invalid action")
            }
        } catch {
            case e => println(e)
        }

        game.toJSON
    }
    
    private def square: Square = game.board.square((params("row").toInt, params("column").toInt))
    private def game = gameController.get(params("id"))
    
    protected def contextPath = request.getContextPath
}