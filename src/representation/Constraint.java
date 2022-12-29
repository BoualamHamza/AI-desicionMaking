package representation;

import java.util.Map;
import java.util.Set;

public interface Constraint {
	public Set<Variable> getScope() ;
	public boolean isSatisfiedBy(Map<Variable, Object> map);
}
