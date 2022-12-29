package planning;

import java.util.Map;
import representation.Variable;

public interface Action {

	public boolean isApplicable (Map <Variable,Object> ApplicableMap);
	public Map <Variable, Object>  successor (Map <Variable, Object> State);
	public int getCost();
}
