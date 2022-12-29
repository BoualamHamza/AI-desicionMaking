package datamining;

import java.util.Set;

/**
 * interface AssociationRuleMiner
 * */
public interface AssociationRuleMiner {
	
	/**
	 * méthode getDatabase retournant une instance de BooleanDatabase
	 * 
	 * */
	public BooleanDatabase getDatabase();
	
	/**
	 * méthode getDatabase retournant une instance de BooleanDatabase
	 * @param freqMin float
	 * @param confianceMin float
	 * */
	Set<AssociationRule> extract(float freqMin, float confianceMin);
}
