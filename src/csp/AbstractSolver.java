package csp;

import java.util.Map;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

public abstract class AbstractSolver implements Solver {
	
	private Set <Variable> variables; 
	private Set <Constraint> constraintes;
	
	public AbstractSolver(Set <Variable> variables, Set <Constraint> constraintes){
		this.variables = variables;
		this.constraintes = constraintes;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	public Set<Constraint> getConstraintes() {
		return constraintes;
	}

	@Override
	public Map<Variable, Object> solve() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * cette méthode prend en argument une affectation partielle des variables, et retourne un booléen indiquant
	 * si cette affectation satisfait toutes les contraintes qui portent sur des variables instanciées dans l’affectation
	 * @param partialInstantiation Map<Variable ,Object>
	 * @return boolean 
	 * **/
	public boolean isConsistent(Map<Variable , Object> partialInstantiation) {
		for(Constraint c : constraintes ) {
			if(partialInstantiation.keySet().containsAll(c.getScope())) {
				if(! c.isSatisfiedBy(partialInstantiation)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
