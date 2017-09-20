package client

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax

import scalatags.JsDom.all._
import io.circe.parser._
import org.scalajs.dom.{Event, MouseEvent, XMLHttpRequest}
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

  private def decodeTodos(response: XMLHttpRequest) = {
    decode[List[Todo]](response.responseText).foreach { todos =>
      showTodos(todos)
    }
  }

  def loadTodos(): Unit = {
    Ajax.get("/api/todo").map { response =>
      decodeTodos(response)
    }
  }

  def switchTodo(id: Int): Unit = {
    Ajax.post(s"/api/todo?id=$id&switch").map { response =>
      decodeTodos(response)
    }
  }

  def deleteTodo(id: Int): Unit = {
    Ajax.delete(s"/api/todo?id=$id").map { response =>
      decodeTodos(response)
    }
  }

  def showTodos(todos: Seq[Todo]): Unit = {
    // clear existing content
    todoDiv.innerHTML = ""

    // build rows of todos
    val todoList = todos.map { todo =>
      tr(
        td(todo.task),
        td(todo.completed.toString, onclick := { (e: MouseEvent) => switchTodo(todo.id)}),
        td(button("Delete", onclick := { (e: MouseEvent) => deleteTodo(todo.id)}))
      )
    }

    // update DOM
    todoDiv.appendChild(
      table(
        tr(th("Task"), th("Completed")),
        todoList
      ).render
    )

    lazy val addInput = input(placeholder := "Add todo", onchange := addTodoHandler _).render

    def addTodoHandler(e: Event): Unit = {
      Ajax.post(s"/api/todo?task=${addInput.value}").map { response =>
        decodeTodos(response)
      }
    }

    todoDiv.appendChild(addInput)

  }
}
