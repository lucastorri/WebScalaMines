package co.torri.scalamines.web

import org.scalatest.matchers.ShouldMatchers
import org.scalatra.test.ScalatraTests
import org.scalatra.test.scalatest.ScalatraFunSuite

class ScalaMinesWebTest extends ScalatraFunSuite with ShouldMatchers {
    addServlet(classOf[ScalaMinesWeb], "/*")

    test("simple get") {
        
        get("/") {
            status should be (200)
            body should include ("play!")
        }
        
    }   
}