package functions.connectors

/**
  * A FunctionConnector connects with an external server to execute a specific function that returns a JSON string.
  */
trait FunctionConnector {

  /**
    * Makes a connection with the configured external server and executes a function. This returns a JSON string.
    * @return
    */
  def execute() : Seq[String]

}
