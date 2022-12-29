package csp;

import java.util.Map;
import java.util.Set;

import representation.Variable;

public class DomainSizeVariableHeuristic implements VariableHeuristic {
	boolean greatest;
	
	public DomainSizeVariableHeuristic(boolean greatest) {
		this.greatest = greatest;
	}

	@Override
	public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> ED){
		Variable prefered = null;
		double max_min_value = (this.greatest ? Float.MIN_VALUE : Float.MAX_VALUE);
		for (Variable x : variables) {
			if ((this.greatest ? max_min_value < ED.get(x).size() : max_min_value > ED.get(x).size())) 
			{
				max_min_value = ED.get(x).size();
				prefered = x;
						
			}
		    }
		    return prefered;
		  }
}
