package datamining;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import representation.BooleanVariable;
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {


	public BruteForceAssociationRuleMiner(BooleanDatabase db) {
		super(db);
	}

	/**
	 * la methode allCandidatePremises prend en argument un ensemble d’items (de type Set<BooleanVariable>),
	 * et retourne l’ensemble de tous ses sous-ensembles ( Set<Set<BooleanVariable>>), à l’exception de l’ensemble vide et de l’ensemble lui-même. 
	 * @param items  Set<BooleanVariable> ensemble d'items
	 * @return liste des sous-ensembles non-vide et non dupliqué
	 * */
	
	/* L'algorithme utilisé pour cette methode est le "PowerSet" pour plus d'explication regardez le fichier txt */	
	
	public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items){
		Set<Set<BooleanVariable>> result = new HashSet<>();
		ArrayList<BooleanVariable> setToArray = new ArrayList<>();
		
		int n = items.size();
		long limit = (long) Math.pow(2, n);
		
		// On ajoute la Set à notre array pout pouvoir recuperer chaque item
		if(n<=1) return result;
		setToArray.addAll(items);
		// on demarre le counteur de 000 à 111...
		for(int counter = 0; counter< limit; counter++) 
		{
			Set<BooleanVariable> subArray = new HashSet<>();
			for(int j = 0; j<n; j++) 
			{	
				/* Verifier si le j éme bit de counter est  set. Si oui ajoutant le j éme element depuis setToArray(ensemble d'items) */
				if((counter & (1 << j)) > 0 && subArray.size() + 1 < items.size()) {
					subArray.add(setToArray.get(j));
					result.add(subArray);
				}
			}
		}
		return result;
		
	}
	
	
	@Override 
    public Set<AssociationRule> extract(float freqMin, float confidenceMin){
        Set<AssociationRule> res = new HashSet<>();

        //recuperer tous les itemset frequent/
        Set<Itemset> itemsetFrequents = (new Apriori(this.getDatabase()).extract(freqMin));

        for (Itemset item : itemsetFrequents)
        {
            Set<Set<BooleanVariable>> allPrem = BruteForceAssociationRuleMiner.allCandidatePremises(item.getItems());

            for(Set<BooleanVariable> prem : allPrem)
            {
                //recuperer la conclusion de la premisse/
                Set<BooleanVariable> conc = new HashSet<>();
                for(Set<BooleanVariable> iSet : allPrem)
                {

                    //Cherchons le complementaire de la premisse/
                    boolean nonPrem = true;
                    for(BooleanVariable b : prem)
                    {
                        nonPrem = nonPrem && !iSet.contains(b);
                    }
                    if (nonPrem)
                        conc = iSet;
                }
                float conf = AbstractAssociationRuleMiner.confidence(prem, conc, itemsetFrequents);
                if(conf >= confidenceMin)
                {
                    AssociationRule as = new AssociationRule(prem,conc, AbstractAssociationRuleMiner.frequency(item.getItems(),itemsetFrequents),conf);
                    res.add(as);
                }
            }
        }
        return res;
    }


}
