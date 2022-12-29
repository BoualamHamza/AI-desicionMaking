package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import representation.BooleanVariable;

public class Apriori extends AbstractItemsetMiner{

	public Apriori(BooleanDatabase booleanDatabase) {
		super(booleanDatabase);
	}

	public BooleanDatabase getDatabase() {
		return this.booleanDatabase;
	}
	/**
	 * la fonction extract retourne l’ensemble des itemsets (non vides) ayant au moins cette fréquence dans la base.
	 * @param minFrequency float
	 * @return result Set<ItemSet>
	 * **/
	public Set<Itemset> extract(float minFrequency) {
		Set<Itemset> result = new HashSet<>();
        Set<Itemset> sing = new HashSet<>();

        sing = frequentSingletons(minFrequency); 
        List<SortedSet<BooleanVariable>> listFre = new ArrayList<>();

        for (Itemset itemset : sing) {
            SortedSet<BooleanVariable> items = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            items.addAll(itemset.getItems ());
            listFre.add(items); 
            result.add( itemset); 
            }

        for (int k=2; k <= super.getBooleanDatabase().getItems().size(); k++) {
            List<SortedSet<BooleanVariable>> listItemsFrequence2 = new ArrayList<>();

            for (int i = 0; i < listFre.size(); i++) {
                SortedSet<BooleanVariable> itemsI = listFre.get(i);
                 for (int j = i + 1; j < listFre.size(); j++) {
                     SortedSet<BooleanVariable> itemsJ = listFre.get(j); 
                    SortedSet<BooleanVariable> itemsCombine = combine(itemsI, itemsJ);

                    if (itemsCombine != null && allSubsetsFrequent(itemsCombine, listFre)){
                        float freqCombine = frequency(itemsCombine);
                        if (freqCombine >= minFrequency) {
                            result.add(new Itemset(itemsCombine, freqCombine)); 
                            listItemsFrequence2.add(itemsCombine);
                        }

                    }
                }
            }
            listFre = listItemsFrequence2 ;
        }
        return result;

	}
	/**
	 * elle prend en argument une fréquence, et retournant l’ensemble de tous les itemsets
	 * dont la fréquence dans la base est au moins égale à celle donnée.
	 * @param minFrequency float
	 * @return singletons Set<ItemSet>
	 * **/
	public Set<Itemset> frequentSingletons(float minFrequency){
		Set <Itemset> singletons = new HashSet<>();
		for(BooleanVariable booleanVariable : this.getDatabase().getItems()) {
			SortedSet<BooleanVariable> candidates = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            candidates.add(booleanVariable);
            float Variablefrequency = this.frequency(candidates);

            if (Variablefrequency >= minFrequency)
            {
                singletons.add(new Itemset(candidates, Variablefrequency));
            }
		}
		return singletons;
	}
	/**
	 * Cette fonction permet de combiner deux ensembles trieés est retourne un un ensemble (trié) d’items en combinant les deux ensembles,si: 
	 * les deux ensembles aient la même taille k,
	 * les deux ensembles aient les mêmes k − 1 premiers items,
	 * les deux ensembles aient des k items différents.
	 * Dans tous les autres cas, la méthode devra renvoyer null.
	 * @param set1 SortedSet<BooleanVariable> ensemble d'items triée
	 * @param set2 SortedSet<BooleanVariable> ensemble d'items triée
	 * @return merged SortedSet<BooleanVriable> 
	 * **/
	public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> set1, SortedSet<BooleanVariable>set2){
		
		  SortedSet<BooleanVariable> merged = new TreeSet<>(COMPARATOR);
	      if (set1.size()>0 && set1.size() == set2.size()) {
	                if (set1.subSet(set1.first(), set1.last()).equals(set2.subSet(set2.first(), set2.last()))) {
	                    if(!set1.last().equals(set2.last())) {
	                        merged.addAll(set1);
	                        merged.addAll(set2);
	                        return merged;
	                   }
	                }
	      }
	            return null;
	   }
	
	public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> itColl) {
		
		Boolean b = true;
		Set<BooleanVariable> copyItems = new HashSet<BooleanVariable>(items);
		
		for(BooleanVariable itemCurrent : items) {
			
			copyItems.remove(itemCurrent);
			b = b && itColl.contains(copyItems);
			copyItems.add(itemCurrent);
			
		}
		return b;
	}
	

}
