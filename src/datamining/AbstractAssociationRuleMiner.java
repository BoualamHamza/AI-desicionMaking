package datamining;

import java.util.HashSet;
import java.util.Set;

import representation.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner{
	private BooleanDatabase db;
	
	public AbstractAssociationRuleMiner(BooleanDatabase db)
	{
		this.db = db;
	}
	
	
	@Override
	public BooleanDatabase getDatabase() {
		return this.db;
	}
	
	
	@Override
	public Set<AssociationRule> extract(float freqMin, float confianceMin) {
		return null;
	}
	
	
	/**
	 * methode frequency retiournant la fréquence de l’ensemble d’items telle qu’elle est stockée dans l’ensemble d’itemsets
	 * @param ensB : ensemble d'items
	 * @param ensI : ensemble d'itemset
	 * @return frequence
	 * */
	public static float frequency(Set<BooleanVariable> ensB, Set<Itemset> ensI) {
		
		boolean exist = false;
		float freq = 0;
		for(Itemset item : ensI)
		{
			if(item.getItems().equals(ensB))
			{
				exist = true;
				freq = item.getFrequency();	
			}
				
		}
		if(!exist)
			throw new IllegalArgumentException("ensemble d'items n'existe pas dans l'ensemble des intemset");
		
		return freq;
		
	}
	
	/**
	 * Methode confidence la confiance de la règle d’association de prémisse et conclusion données, les fréquences d’itemsets nécessaires aux calculs étant
	 * données dans l’ensemble en argument.
	 * @param premise Set<BooleanVariable>
	 * @param conclusion Set<BooleanVariable>
	 * @param ensI Set<ItemSet>
	 * @return frequence de ensB s'il existe
	 * */
	public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> ensI) {
		float probPremise= frequency(premise, ensI);
		Set<BooleanVariable> merge  = new HashSet<BooleanVariable>();
		//ajoutant lA prémisse et la conclusion au merge
		merge.addAll(premise);
		merge.addAll(conclusion);
		float res = frequency(merge, ensI) / probPremise; 
		return res ; 
		
	}

}
