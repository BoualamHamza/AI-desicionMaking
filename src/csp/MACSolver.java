package csp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

public class MACSolver extends AbstractSolver{

	public MACSolver(Set<Variable> variables, Set<Constraint> constraintes) 
	{
		super(variables, constraintes);
	}
	/**
	 * la methode solve retourne une solution (de type Map<Variable, Object>), ou null s’il n’y en a pas
	 * @return Map<Variable, Object> la solution
	 * **/
	public Map<Variable, Object> solve() {
		Map<Variable, Object> I = new HashMap<>();
		LinkedList <Variable> V = new LinkedList<>(getVariables());
		Map<Variable, Set<Object>> ED = new HashMap<>();
		
		for(Variable variable : getVariables()) 
		{
			ED.put(variable, new HashSet<>(variable.getDomain()));
		}
		
		return MAC(I, V, ED);
	}
	
	public Map<Variable, Object> MAC(Map<Variable, Object> I, LinkedList<Variable> V, Map<Variable, Set<Object>> ED)
	{
		if (V.isEmpty())	return I;
		ArcConsistency arcConsistency = new ArcConsistency(getConstraintes());
		
		if(! arcConsistency.ac1(ED))	return null;
		Variable tmp = V.pop();
		
		for(Object object: ED.get(tmp)) 
		{
			Map<Variable, Object> N = new HashMap<>(I);
			N.put(tmp, object);
			
			if(this.isConsistent(N)) 
			{
				Map<Variable, Object> R = MAC(N, V ,ED);
				if(R != null)	return R;
			}
		}V.add(tmp);
		return null;
	}








}
