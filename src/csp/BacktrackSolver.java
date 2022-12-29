package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

public class BacktrackSolver extends AbstractSolver{

	public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraintes) {
		super(variables, constraintes);
		// TODO Auto-generated constructor stub
	}
	
	public Map<Variable, Object> solve(){
		Map<Variable, Object> I = new HashMap<>();
		List<Variable> variables = new ArrayList<>(this.getVariables());
		return bt_solve(I, variables);
	}
	
	/*	I	:	Instantiation Partiel  
	 *  V	: 	variables non instantiées
	 */
	public Map<Variable, Object> BT(Map<Variable, Object> I, Set<Variable> v){
		if(v.isEmpty()) {	return I;	}
		 Variable x = (Variable) v.toArray()[0];
		 System.out.println(x);
		 Set<Variable> newN = new HashSet<>();
		 newN.remove(0);
		 for(Object object : x.getDomain()) {
			 Map<Variable, Object> N = new HashMap<>();
			 N.put(x, object);
			 if(this.isConsistent(N)) {
				 Map<Variable, Object> R = this.BT(N, newN);
				 if(R != null) {
					 return R;
				 }
			 }
		 }
		
		return null;
		
	}
    public Map<Variable, Object> bt_solve (Map<Variable,Object> objectMap, List<Variable> variables_not_inst)
    {
        if (variables_not_inst.isEmpty()){
            return objectMap;
        }
        Variable not_var = variables_not_inst.get(0);
        List<Variable> newNotInstanciatedVariables = new ArrayList<>(variables_not_inst);
        newNotInstanciatedVariables.remove(0);
        for (Object obj : not_var.getDomain()) {
            Map<Variable, Object> objMapTmp = new HashMap<>(objectMap);
            objMapTmp.put(not_var, obj);
            if (this.isConsistent(objMapTmp)) {
                objMapTmp = this.bt_solve(objMapTmp, newNotInstanciatedVariables);
                if (objMapTmp != null) {
                    return objMapTmp;
                }
            }
        }
        return null;
    }
}


