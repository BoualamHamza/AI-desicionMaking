package csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import representation.Variable;

public class RandomValueHeuristic implements ValueHeuristic {
	private Random randomGenerator;
	
	public RandomValueHeuristic(Random randomGenerator) {
		this.randomGenerator = new Random();
	}
	/**
	 * Cette methoe retourne une liste contenant les valeurs du domaine, ordonnées selon l’heuristique(la meilleure en premier)
	 * @param Variable
	 * @param Set<Object> domain 
	 * @return List<Object>
	 * **/
	@Override
	public List<Object> ordering(Variable variable, Set<Object> domain) {
		List<Object> list = new ArrayList<Object>();
		for(Object elem : list) {
			list.add(elem);
		}
		Collections.shuffle(list, randomGenerator);
		return list;
		
	}
}
