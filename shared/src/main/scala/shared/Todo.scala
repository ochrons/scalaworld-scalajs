package shared

import io.circe.generic.JsonCodec

@JsonCodec case class Todo(id: Int, task: String, completed: Boolean)
