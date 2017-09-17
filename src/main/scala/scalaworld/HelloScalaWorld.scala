package scalaworld

import org.scalajs.dom
import scalatags.JsDom.all._

object HelloScalaWorld {
  // The main method is automatically identified by Scala.js as the entry point
  // of your application
  def main(args: Array[String]): Unit = {
    // find the DOM element under which we'll start building HTML
    val root = dom.document.getElementById("root")

    // build some simple HTML using Scalatags
    val html = div(
      h1("Welcome!"),
      p("This is a just a very simple web app created with Scala.js")
    ).render

    // Append our HTML to the root element to make it visible in the DOM
    root.appendChild(html)
  }
}
