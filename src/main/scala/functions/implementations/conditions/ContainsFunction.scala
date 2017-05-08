package functions.implementations.conditions

import functions.Function

/**
  * Checks if an Infobox property contains a given value.
  */
class ContainsFunction(property : String, value : String) extends Function {

  def execute(): Boolean = {
    if(property != null) property.contains(value)
    else false
  }

}
