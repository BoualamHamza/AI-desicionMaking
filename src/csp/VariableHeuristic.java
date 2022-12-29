package csp;

import java.util.Map;
import java.util.Set;

import representation.Variable;

public interface VariableHeuristic {
	
	public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains);

}
