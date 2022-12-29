package planning;

import java.util.Map;

import representation.Variable;

public interface Goal {
	public boolean isSatisfiedBy(Map<Variable, Object> state) ;
	
	

}
