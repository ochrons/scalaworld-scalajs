package server

import akka.http.scaladsl.server.{Directives, Route}
import io.circe.generic.auto._

class WebService() extends Directives with CirceSupport {

  val todoService = new TodoService
  todoService.add("Buy coffee")
  todoService.add("Drink coffee")
  todoService.add("Write awesome code")

  val route: Route = {
    pathSingleSlash {
      get {
        getFromResource("public/index.html")
      }
    } ~ pathPrefix("assets" / Remaining) { file =>
      // optionally compresses the response with Gzip or Deflate
      // if the client accepts compressed responses
      encodeResponse {
        getFromResource("public/" + file)
      }
    } ~ pathPrefix("api") {
      path("todo") {
        (get & parameter('id.as[Int])) { id =>
          complete(todoService.todo(id))
        } ~ get {
          complete(todoService.allTodos)
        } ~ (post & parameter('task)) { (task) =>
          complete(todoService.add(task))
        } ~ (post & parameters(('id.as[Int], 'task))) { (id, task) =>
          complete(todoService.update(id, task))
        } ~ (post & parameters(('id.as[Int], 'switch))) { (id, _) =>
          complete(todoService.switchComplete(id))
        } ~ (delete & parameter('id.as[Int])) { id =>
          complete(todoService.delete(id))
        }
      }
    }
  }
}
