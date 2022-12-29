package csp;
import java.util.*;

import representation.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic{

  Set<Constraint> constraints; boolean bool;

  public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean bool){
    this.constraints = constraints; this.bool = bool;
  }
  /**
   * qui prend en arguments, dans cet ordre, un ensemble de variables (de type Set<Variable>) et un ensemble de domaines (de type Map<Variable, Set<Object>>), et qui retourne une variable (la meilleure au sens del’heuristique).
   * @param Set<Variable> variables
   * @param Map<Variable, Set<Object>> ED: un ensemble de domains
   * @return Variable 
   * **/
  public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> ED){

    int cmpt;
    Variable prefered = null;

    double max_min_value = (bool ? Float.MIN_VALUE : Float.MAX_VALUE);
    for (Variable x : variables) {
      cmpt = 0;
      for (Constraint c : this.constraints) {
        if (c.getScope().contains(x)) {
          cmpt += 1;
        }
      }
      if ((bool ? cmpt > max_min_value : cmpt < max_min_value)) {
        max_min_value = cmpt; prefered = x;
      }
    }
    return prefered;
  }




}
