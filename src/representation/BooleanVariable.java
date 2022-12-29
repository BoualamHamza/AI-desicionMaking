package representation;
import java.util.Arrays;
import java.util.HashSet;

public class BooleanVariable  extends Variable{
	
	public BooleanVariable(String nom) {
		super (nom, new HashSet<>(Arrays.asList(true, false)));
		
	}


}
