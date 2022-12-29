package csp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

public class ArcConsistency {
	private Set <Constraint> constraints;
    public Set<Constraint> unaryConstraints, binaryConstraints;

    public ArcConsistency(Set<Constraint> constraints) throws IllegalArgumentException {
        unaryConstraints = new HashSet<>();
        binaryConstraints = new HashSet<>();
        
        for(Constraint constraint : constraints) {
            Set<Variable> scope = constraint.getScope();
            if(scope.size() == 1) {
                unaryConstraints.add(constraint);
            } else if(scope.size() == 2) {
                binaryConstraints.add(constraint);
            } else {
                throw new IllegalArgumentException("Ni unaire ni binaire");
            }
        }
        this.constraints = constraints;
    }
    /**
     *  La méthode doit supprimer, en place, les valeurs v des domaines pour lesquelles il existe une contrainte unaire non satisfaite par v.
     *  Elle doit par ailleurs retourner false si au moins un domaine a été vidé ( true sinon).
     *  @param ED Map<Variable, Set<Object>> un ensemble de domaine
     *  @return boolean
     *  **/
    
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> ED) {
        for(Variable variable : ED.keySet()) {
            Set<Object> toDelete = new HashSet<>();
            for(Object valeur : ED.get(variable)) {
                for(Constraint c : this.unaryConstraints) {
                    if(c.getScope().contains(variable)) {
                        Map<Variable, Object> map = new HashMap<>();
                        map.put(variable, valeur);
                        if(!c.isSatisfiedBy(map)) {
                            toDelete.add(valeur);
                        }
                    }
                }
            }
            ED.get(variable).removeAll(toDelete);
        }
        for(Variable variable : ED.keySet()) {
            if(ED.get(variable).isEmpty()) {
                return false;
            }
        }
        return true;
    }
	
    /**
     * La méthode doit supprimer, en place, toutes les valeurs v1 de D1 pour lesquels il n’existe aucune
     *valeur v2 de D2 supportant v1 pour toutes les contraintes portant sur v1 et v2. La méthode doit en outre		
     *retourner true si au moins une valeur a été supprimée de D1 ( false sinon).
     **/
    public boolean revise(Variable variable1, Set<Object> object1, Variable variable2, Set <Object> object2) {
    	boolean delete = true;
    	Set<Object> toDelete = new HashSet<>();
    	Map<Variable, Object>  u = new HashMap<>();
    	for(Object obj1 : object1) 
    	{
    		boolean viable = false;
    		for(Object obj2 : object2) 
    		{
    			boolean toutSatisfait = true;
    			for(Constraint constraint : this.binaryConstraints) 
    			{
    				Set<Variable> tmp = new HashSet<>();
    				tmp.add(variable1);
    				tmp.add(variable2);
    				if(tmp.equals(constraint.getScope())){
        				u.put(variable1, obj1);
        				u.put(variable2, obj2);
        				if(! constraint.isSatisfiedBy(u)) 
        				{
        					toutSatisfait = false;
        					break;
        				}
    				}

                    
    			}
    			if(toutSatisfait) 
    			{
    				viable = true;
    				break;
    			}
    		}
    		if(!viable) {
    			toDelete.add(obj1);
    			delete = true;
    		}
    		
    	}
    	object1.removeAll(toDelete);
    	return delete && !toDelete.isEmpty();
    }
    /**
     * cette methode filtre tous les domaines en place en utilisant ac1, jusqu’à stabilité, et retourne false si au moins un domaine a été vidé ( true sinon).
     *@param domains Map<Variable, Set<Object>> ensemble de domains
     **/
    public boolean ac1(Map<Variable, Set<Object>> domains) {
    	if(! this.enforceNodeConsistency(domains)) 
    	{
    		return false;
    	}
    	boolean change;
    	do{
    		change = false;
    		for(Variable xi : domains.keySet()) {
    			for(Variable xj : domains.keySet()) {
    				if(this.revise(xi, xi.getDomain(), xj, xj.getDomain())) {
    					change = true;
    				}
    				
    			}
    		}
    			
    	}while(change);
    	
    	for(Variable x : domains.keySet()) {
    		if(x.getDomain().isEmpty()) {
    			return false;
    		}
    	}
    	
		return true;
    	
    }
    
    
    
    public Set <Constraint> getConstraints() {
		return constraints;
	}
}


