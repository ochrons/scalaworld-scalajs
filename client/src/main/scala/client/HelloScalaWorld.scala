package client

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax

import scalatags.JsDom.all._
import io.circe.parser._
import shared.Todo

import scala.concurrent.ExecutionContext.Implicits.global

object HelloScalaWorld {
  // list of todos, to be filled from the server
  private val todoDiv = div().render

  // The main method is automatically identified by Scala.js as the entry point
  // of your application
  def main(args: Array[String]): Unit = {
    // find the DOM element under which we'll start building HTML
    val root = dom.document.getElementById("root")

    // build some simple HTML using Scalatags
    val html = div(
      h1("Stuff to do"),
      todoDiv
    ).render

    // Append our HTML to the root element to make it visible in the DOM
    root.appendChild(html)

    loadTodos()
  }

  def loadTodos(): Unit = {
    Ajax.get("/api/todo").map { request =>
      decode[List[Todo]](request.responseText).foreach { todos =>
        showTodos(todos)
      }
    }
  }

  def showTodos(todos: Seq[Todo]): Unit = {
    // clear existing content
    todoDiv.innerHTML = ""

    // build rows of todos
    val todoList = todos.map { todo =>
      tr(td(todo.task), td(todo.completed.toString))
    }

    // update DOM
    todoDiv.appendChild(
      table(
        tr(th("Task"), th("Completed")),
        todoList
      ).render
    )
  }
}
