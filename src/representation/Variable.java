package representation;
import java.util.Set;

public class Variable {
	String nom;
	Set<Object> domain;
	
	public Variable(String nom, Set <Object> domain){
		this.nom = nom;
		this.domain = domain;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Variable)
			if (((Variable) obj).getName().equals(nom))
				return true;
		return false; 
	}
	
	public String getName() {
		return nom;
	}
	
	public Set<Object> getDomain() {
		return domain;
	}
	
	public int hashCode() {
		return nom.hashCode();
	}
	
	



}
