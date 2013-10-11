import java.util.Iterator;
import java.util.ConcurrentModificationException;
import data_structures.*;

public class MyTester3 {
  public static void main(String [] args) {
    DictionaryADT<Integer,Integer> dictionary = new
		BalancedTree<Integer,Integer>();
		
		System.out.println("\nDeleting from epmty list");
		System.out.println(dictionary.delete(8));
		
		System.out.println("\nAdding elements to dictionary");
		dictionary.add(5, 5);
		dictionary.add(3,3);
		dictionary.add(2,2);
		System.out.println("Size: "+dictionary.size());
		System.out.println(dictionary.add(4,4));
		
		//System.out.println(dictionary.getValue(5));
		
		System.out.println("Size: "+dictionary.size());
	  Iterator<Integer> kIter= dictionary.keys();
		System.out.println("keys()");
		while (kIter.hasNext()) {
		  System.out.println(kIter.next());
		}
		
		Iterator<Integer> vIter = dictionary.values();
		//System.out.println(vIter.hasNext());
		System.out.println("Size: "+dictionary.size());
		System.out.println("\nvalues()");
		while (vIter.hasNext()) {
		Integer tmp = vIter.next();
		  System.out.println(tmp);
		}
  }
}
