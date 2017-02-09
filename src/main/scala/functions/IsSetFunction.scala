package functions

/**
  * Function that tests if a given Infobox property is set or not.
  */
class IsSetFunction(property : String) extends Function {

  def execute() : Boolean = {
    isNull(property)
  }

  /**
    * Checks the string if it is null or not
    * @param s
    * @return
    */
  private def isNull(s : String) : Boolean = {
    // for now only "null" is supported
    s match {
      case "null" => true
      case other => false
    }
  }

}