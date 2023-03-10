package datamining;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import representation.BooleanVariable;

public class BooleanDatabase {
	
	private Set<BooleanVariable> items;
	private List<Set<BooleanVariable>> transactions;
	
	public BooleanDatabase(Set<BooleanVariable> items) {
		this.items = items;
		this.transactions = new ArrayList<>();
	}
	
	public void add(Set<BooleanVariable> transaction) {
		this.getTransactions().add(transaction);
		
	}

	public List<Set<BooleanVariable>> getTransactions() {
		return transactions;
	}

	public Set<BooleanVariable> getItems() {
		return items;
	}
	

}
