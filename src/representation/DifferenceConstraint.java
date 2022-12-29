package representation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DifferenceConstraint implements Constraint{
	Variable vr1, vr2;
	public DifferenceConstraint(Variable vr1, Variable vr2) {
		this.vr1 = vr1;
		this.vr2 = vr2;
	}
	@Override
	public Set<Variable> getScope() {
		// TODO Auto-generated method stub
		return new HashSet<>(Arrays.asList(vr1, vr2));
	}

	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> map) throws IllegalArgumentException  {
		if (map.get(vr1) == null) throw new IllegalArgumentException();
		if (map.get(vr2) == null) throw new IllegalArgumentException();
		
		if ( map.get(vr1) != map.get(vr2)){
			return true;
		}
		return false;
	}

}
