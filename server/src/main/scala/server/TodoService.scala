package server

import shared.Todo

class TodoService {
  private var todos = List.empty[Todo]
  private var nextId = 0

  def allTodos: List[Todo] = todos

  def todo(id: Int): Option[Todo] = todos.find(_.id == id)

  def add(task: String): List[Todo] = {
    val todo = Todo(nextId, task, completed = false)
    todos :+= todo
    nextId += 1
    todos
  }

  def switchComplete(id: Int): List[Todo] = {
    todos = todos.collect {
      case t if t.id == id =>
        t.copy(completed = !t.completed)
      case other => other
    }
    todos
  }

  def update(id: Int, task: String): List[Todo] = {
    todos = todos.collect {
      case t if t.id == id =>
        t.copy(task = task)
      case other => other
    }
    todos
  }

  def delete(id: Int): List[Todo] = {
    todos = todos.filterNot(_.id == id)
    todos
  }
}
