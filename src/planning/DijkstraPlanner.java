package planning;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Variable;

public class DijkstraPlanner implements Planner {
	private Map<Variable,Object> initialState;
	private Set<Action> actions;
	private Goal goal;

	public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
		this.initialState = initialState;
		this.actions = actions;
		this.goal = goal;
	}
	@Override
	public List<Action> plan() {
		int sonde = 0;
        Map<Map<Variable,Object>,Map<Variable,Object>> father = new HashMap<>();
        Map<Map<Variable,Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();
        List<Map<Variable, Object>> open  = new LinkedList<Map<Variable, Object>>();
        List<Map<Variable, Object>> goals  = new LinkedList<Map<Variable, Object>>();
        Map<Map<Variable, Object>, Double> distance = new HashMap<>();
        Map<Variable, Object> instantiation = new HashMap<>();
        
        father.put(getInitialState(), null);
		open.add(getInitialState());
		distance.put(initialState, 0.0);
		
		while(!open.isEmpty()) {
			instantiation = this.argmin(distance, open);
			open.remove(instantiation);
			if(this.getGoal().isSatisfiedBy(instantiation)) { goals.add(instantiation); }
			for(Action action : this.getActions()) 
			{
				if(action.isApplicable(instantiation)) 
				{
					Map<Variable, Object> next = action.successor(instantiation);
					if(!distance.containsKey(next)) { distance.put(next, Double.POSITIVE_INFINITY); }
					if(distance.get(next)>distance.get(instantiation)+ action.getCost()) 
					{
                        distance.put(next,distance.get(instantiation)+action.getCost());
                        father.put(next, instantiation);
                        plan.put(next, action);
                        open.add(next);
					}
				}
			}
		}if(goals.isEmpty()) {	return null;	}
		return get_dijkstra_plan(father, plan, goals, distance);
	}


	

    public Map<Variable,Object> argmin(Map<Map<Variable, Object>, Double> dist, List<Map<Variable,Object>> node) {
        Map<Variable,Object> mp = null;
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i<node.size(); i++)
        {
        	if(dist.get(node.get(i))< min) {
        		min = dist.get(node.get(i));
        		mp = node.get(i);
        	}

        }
        return mp;
    }
    
    public List<Action> get_dijkstra_plan(Map<Map<Variable,Object>,Map<Variable,Object>> father, Map<Map<Variable,Object>, Action> plan, List<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Double> distance){
    	List<Action> DIJ_plan  = new LinkedList<>();
    	Map<Variable, Object> goal = new HashMap<>();
    	goal = argmin(distance, goals);
    	
    	while(goal != null) 
    	{
    		if(plan.get(goal) != null) 
    		{
        		DIJ_plan.add(plan.get(goal));   			
    		}
    		goal = father.get(goal);
    	}
    	Collections.reverse(DIJ_plan);
    	return DIJ_plan;
    }
	
	
    
   
	public Map<Variable, Object> getInitialState() {
		return initialState;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public Goal getGoal() {
		return goal;
	}

}
