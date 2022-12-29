package planning;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Variable;

public class AStarPlanner implements Planner {
    
	private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;
    
	
    public AStarPlanner(Map<Variable,Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
    	this.initialState = initialState;
    	this.actions = actions;
    	this.goal = goal;
    	this.heuristic = heuristic;
    }
	
	@Override
	public List<Action> plan() {
		Map<Map<Variable, Object>, Action> plan = new HashMap<>();
		Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
		Map<Map<Variable, Object>, Double> distance = new HashMap<>();
		Map<Map<Variable, Object>, Double> value = new HashMap<>();
        List<Map<Variable, Object>> open = new LinkedList<>();

        open.add(this.getInitialState());
        distance.put(this.initialState, 0.0);
        father.put(this.getInitialState(), null);
        value.put(this.initialState, (double) this.heuristic.estimate(this.initialState));        
        
        
        while(! open.isEmpty()) {
        	Map<Variable, Object> instantiation = argmin(value, open);
        	if(this.getGoal().isSatisfiedBy(instantiation)) {
        		
        		return (new BFSPlanner(this.getInitialState(), this.getActions(), this.getGoal())).getBfsPlan(father,plan,instantiation);
        	}else {
        		open.remove(instantiation);
        	} for(Action action : this.getActions()) {
        		if(action.isApplicable(instantiation)) {
        			Map<Variable, Object> next = action.successor(instantiation);
        			if(! distance.containsKey(next)){
        				distance.put(next, Double.POSITIVE_INFINITY);
        			}
        			if(distance.get(next) > distance.get(instantiation) + action.getCost()) {
                        distance.put(next,distance.get( instantiation)+action.getCost());
                        value.put(next,distance.get(next) + (double) this.heuristic.estimate(next));
                        father.put(next, instantiation);
                        plan.put(next,action);
                        open.add(next);
        			}
        		}
        	}
        }
		return null;
	}
    public Map<Variable,Object> argmin(Map<Map<Variable, Object>, Double> distance,List<Map<Variable,Object>> nodes ){
        
        Double max = Double.POSITIVE_INFINITY;
        Map<Variable,Object> instanciation =new HashMap<>();
        
        for (int i=0; i<nodes.size(); i++){
      	  
            if(distance.get(nodes.get(i)) < max){
               max = distance.get(nodes.get(i));
               instanciation = nodes.get(i);
            }
        }
        return instanciation;
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
