package csp;

import java.util.Map;

import representation.Variable;

public interface Solver {
	
	public Map<Variable, Object> solve();
}
