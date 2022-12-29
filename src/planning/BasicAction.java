package planning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import representation.Variable;

public class BasicAction implements Action {
	
	Map<Variable, Object> precondition; 
	Map<Variable, Object>  effect;
	int cost;
	
	public BasicAction(Map<Variable, Object> precondition,Map<Variable, Object> effect, int cost ) {
		this.precondition = precondition;
		this.effect = effect;
		this.cost = cost;
	}

	@Override
	public boolean isApplicable(Map<Variable, Object> state) {
		boolean res = true;
		if(precondition.isEmpty() ) {return true; }
		//for(Entry<Variable, Object> p : precondition.entrySet())
		Iterator<Variable> iterator = precondition.keySet().iterator();
		while(iterator.hasNext()) {
			Variable key = iterator.next();
			if(!state.containsKey(key) || !state.get(key).equals(precondition.get(key))) {
				res = false;	
			}
		}
		return res;
	}

	@Override
	public Map<Variable, Object> successor (Map<Variable, Object> state) {
		Map<Variable, Object> newState = new HashMap<>(state);
        if (this.isApplicable(state)) {	newState.putAll(this.effect);	}
        return newState;
    }


	@Override
	public int getCost() {
		return cost;
	}

}
