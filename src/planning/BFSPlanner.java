package planning;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import representation.Variable;

public class BFSPlanner implements Planner {
	private Map<Variable,Object> initialState;
	private Set<Action> actions;
	private Goal goal;

	public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
		this.initialState = initialState;
		this.actions = actions;
		this.goal = goal;
	}
	
	@Override
	public List<Action> plan() {
		int sonde = 0;
        Map<Map<Variable,Object>,Map<Variable,Object>> father = new HashMap<>();
        Map<Map<Variable,Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();
        Set<Map<Variable,Object>> closed = new HashSet<>();
        Queue<Map<Variable, Object>> open  = new ArrayDeque<Map<Variable, Object>>();
        
        father.put(this.getInitialState(),null);
        plan.put(this.getInitialState(),null);
        closed.add(this.getInitialState());
        open.add(getInitialState());
        
        if (getGoal().isSatisfiedBy(initialState)) 	return new ArrayList<>();
        
        while(! open.isEmpty()) {
        	Map<Variable, Object> instantiation = open.poll();
        	closed.add(instantiation);
        	sonde++ ;
        	for(Action action : this.getActions()) {
        		if(action.isApplicable(instantiation)) {
        			Map<Variable ,Object> next = action.successor(instantiation);
        			sonde++;
        			if((! closed.contains(next)) && (!open.contains(next)) ) {
        				father.put(next,instantiation);
        				plan.put(next, action);
        				//sonde++;
        				
        				if(this.getGoal().isSatisfiedBy(next)) {
        					return getBfsPlan(father, plan, next);
        				}else {
        					open.add(next);
        					
        				}
        			}
        		}
        	}
        }
		
        return null;
	}
	
	public List<Action> getBfsPlan(Map<Map<Variable,Object>,Map<Variable,Object>> father, Map<Map<Variable,Object>, Action> plan,  Map<Variable, Object> goal){
		List<Action> bfsPlan = new ArrayList<>();
		while(goal != null) {
			if(plan.get(goal) != null) {
				bfsPlan.add(plan.get(goal));
				
			}
			goal = father.get(goal);
		}
		Collections.reverse(bfsPlan);
		return bfsPlan;
	}
	
	
	
	
	@Override
	public Map<Variable, Object> getInitialState() {
		return initialState;
	}

	@Override
	public Set<Action> getActions() {
		return actions;
	}

	@Override
	public Goal getGoal() {
		return goal;
	}
	

}
