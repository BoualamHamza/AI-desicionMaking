package datamining;

import java.util.Comparator;
import java.util.Set;

import representation.BooleanVariable;

public abstract class AbstractItemsetMiner implements ItemsetMiner{
	public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());
    protected final BooleanDatabase booleanDatabase;

    public AbstractItemsetMiner(BooleanDatabase booleanDatabase)
    {
        this.booleanDatabase = booleanDatabase;
    }
    /**
     * La methode frequency permet de calculer la fréquence d’un ensemble donné d’items dans la base
     * @param items Set<BooleanVariable>
     * @return float qui est la frequence 
     **/
    public float frequency(Set<BooleanVariable> items)
    {
        float occurence = 0;
        for (Set<BooleanVariable> itemsIt : this.booleanDatabase.getTransactions())
        {
            if (itemsIt.containsAll(items))	occurence++;
        }
        return occurence / this.booleanDatabase.getTransactions().size();
    }

    public BooleanDatabase getBooleanDatabase() {
        return booleanDatabase;
    }
    
}


