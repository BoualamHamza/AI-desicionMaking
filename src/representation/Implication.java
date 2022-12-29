package representation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Implication implements Constraint{
	Set<Object> domain1, domain2;
	Variable vr1;
	Variable vr2 ;
	
	
	public Implication(Variable vr1, Set<Object> domain1, Variable vr2, Set<Object> domain2) {
		this.vr1 = vr1;
		this.vr2 = vr2;
		this.domain1 = domain1;
		this.domain2 = domain2;
	}

	
	
	
	@Override
	public Set<Variable> getScope() {
		// TODO Auto-generated method stub
		return new HashSet<>(Arrays.asList(vr1, vr2));
	}

	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> map) {
		if (map.get(vr1) == null) throw new IllegalArgumentException();
		if (map.get(vr2) == null) throw new IllegalArgumentException();
		if(!(domain1.contains(map.get(vr1))) || ( domain2.contains(map.get(vr2)))) {
			return true;
		}
		return false;
	}
	
}
