package representation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnaryConstraint implements Constraint{
	Set<Object> domain;
	Variable vr;
	
	public UnaryConstraint(Variable vr, Set<Object> domain) {
		this.vr = vr;
		this.domain = domain;
	}
	@Override
	public Set<Variable> getScope() {
		// TODO Auto-generated method stub
		return new HashSet<>(Arrays.asList(vr));
	}

	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> map) throws IllegalArgumentException {
		if (map.get(vr) == null) throw new IllegalArgumentException();
		if (domain.contains(map.get(vr))) {
			return true;
		}
		return false;
	}

}
