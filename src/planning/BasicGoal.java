package planning;

import java.util.Map;

import representation.Variable;

public class BasicGoal implements Goal {
	public Map<Variable, Object> partialInstantiation;
	
	public BasicGoal(Map<Variable, Object> partialInstantiation) {
		this.partialInstantiation = partialInstantiation;
	}

	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> state) {
		return state.entrySet().containsAll(this.partialInstantiation.entrySet());
	}

}
