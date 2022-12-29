/**
 * 
 */
package planning;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Variable;



public class DFSPlanner implements Planner {
	private Map<Variable,Object> initialState;
	private Set<Action> actions;
	private Goal goal;
	//private static int sonde;

	public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
		this.initialState = initialState;
		this.actions = actions;
		this.goal = goal;
	}
	
	
	@Override
	public List<Action> plan() {
		Set<Map<Variable, Object>> closed = new HashSet<Map<Variable, Object>>(); 
		closed.add(this.getInitialState());
		return dfs(getInitialState(), new ArrayList<>(), closed);
		
	}

	@Override
	public Map<Variable, Object> getInitialState() { return initialState; }

	@Override
	public Set<Action> getActions() { return actions; }

	@Override
	public Goal getGoal() { return goal; }
	
	
	public List<Action> dfs(Map<Variable,Object> initialState, List <Action> plan, Set<Map<Variable, Object>> closed){
		int sonde = 0;
		if(getGoal().isSatisfiedBy(initialState)) {
			return plan;
		}
		for(Action action : getActions()) {
			if (action.isApplicable(initialState)) {
				Map<Variable, Object> next = action.successor(initialState);
				//sonde++;
				if(! closed.contains(next)) {
					sonde++;
					plan.add(action);
					closed.add(next);
					List <Action> subplan = dfs(next,plan,closed); 
					if(subplan != null) {
						return subplan;
					}else {
						plan.remove(plan.size() - 1);
					}
				}
			}
		}
		return null;
		
	}

	

}
