package csp;
import java.util.*;

import representation.*;

public class HeuristicMACSolver extends AbstractSolver{

  public VariableHeuristic hvar;
  public ValueHeuristic hval;


  public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic hvar, ValueHeuristic hval){
    super(variables, constraints);
    this.hvar = hvar; this.hval = hval;
  }

  public Map<Variable, Object> mac(Map<Variable, Object> I, Set<Variable> variables_non_instancier, Map<Variable, Set<Object>> ED){
    if (variables_non_instancier.isEmpty()) {
      return I;
    }
    else{
      ArcConsistency arc = new ArcConsistency(this.getConstraintes());
      if (!arc.ac1(ED)) return null;
      Variable xi = this.hvar.best(variables_non_instancier, ED);
      variables_non_instancier.remove(xi);
      Map<Variable, Object> N = new HashMap<Variable, Object>(I);
      for (Object v : this.hval.ordering(xi, ED.get(xi))) {
        N.put(xi, v);
        if (this.isConsistent(N)) {
          Map<Variable, Object> R = this.mac(N, variables_non_instancier, ED);
          if (R != null) return R;
        }
      }
      variables_non_instancier.add(xi);
    return null;
    }
  }

  @Override
  public Map<Variable, Object> solve(){
    Map<Variable, Set<Object>> ED = new HashMap<Variable, Set<Object>>();
    for(Variable x : this.getVariables()) {
      ED.put(x, x.getDomain());
    }
    return mac(new HashMap<Variable, Object>() , new HashSet<Variable>(this.getVariables()), ED);
  }






}
