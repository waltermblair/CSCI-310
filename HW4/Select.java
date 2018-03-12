import java.util.*;

/**
 * 
 * Select has a List and numberComparisons counter and methods for Quickselect, the 
 * Select2 method described in the assignment, and Quicksort. All of these methods
 * use Hoare partitioning.
 * 
 * Consulted source for usage of List (https://docs.oracle.com/javase/7/docs/api/java/util/List.html)
 * 
 * @author waltermblair
 *
 */

class Select {
	private List<Object> array;
	private int numberComparisons;
	
	/**
	 * Constructor used by FiveElementSet in Select2 method
	 */
	public Select() {
		super();
	}
	
	/**
	 * Parametized constructor invoked by driver's Main
	 * @param array of type List<Object> which is the list created in driver
	 */
	public Select(List<Object> array) {
		super();
		this.array = array;
	}

	/**
	 * Quickselect method from text p160. Modified with conditional if(l < r)
	 * in case of partition with only 1 element, Hoare rather than Lomuto partition,
	 * and no recursive modification of k because I don't ever reset parameter l to 
	 * zero, so we're keeping same reading frame of whole array throughout recursive calls
	 * @param array of type List<Object>
	 * @param l of type int, start of subarray
	 * @param r of type int, end of subarray
	 * @param k of type int, the kth smallest element
	 * @return int
	 */
	public int Quickselect(List<Object> array, int l, int r, int k) {
		if(l < r) {
			int s = HoarePartition(array, l, r);
			if(s == k-1)
				return (int)array.get(s);
			else if(s > (k - 1))
				return Quickselect(array, l, s-1, k);
			else
				return Quickselect(array, s+1, r, k);
		}
		else
			return (int)array.get(l);
	}
	
	/**
	 * Select method from assignment. Quicksorts arrays smaller than 50 elements.
	 * Otherwise, Splits array up into 5-element lists and builds a list M containing
	 * the median of each 5-element list. The median of M is calculated and stored as m.
	 * The full array is sorted into lists S1, S2, and S3, and the sizes of these lists
	 * are compared to k with recursive calls until finally finding the kth smallest element.
	 * @param array of type List<Object>
	 * @param k of type int, the kth smallest element
	 * @return int
	 */
	public int Select2(List<Object> array, int k) {
		if(array.size() < 50) {
			Quicksort(array, 0, array.size()-1);
			return (int)array.get(k-1);
		}
		else {
			// Instantiate list of medians M
			List<Object> M = new ArrayList<Object>();
			
			// Create each five-element set, sort it, and store its median in M
			for(int i = 0; i < array.size()-1; i+=5) {
				Select FiveElementSet = new Select();
				for(int j = 0; j < 5; j++)
					FiveElementSet.getArray().add(array.get(i+j));
				FiveElementSet.Quicksort(this.array, 0, 4);
				M.add((Integer)FiveElementSet.getArray().get(2));
			}
			
			// Declare m and select the median m of the list of medians M.
			int m;
			if(M.size()%2 == 0) // if length is even
				m = (M.size()/2 + (M.size()/2+1))/2;
			else
				m = M.size()/2+1; // if length is odd
			m = Select2(M, m);
			
			// Create three lists and add elements to each depending on <, ==, or > than m.
			ArrayList<Object> S1 = new ArrayList<Object>();
			ArrayList<Object> S2 = new ArrayList<Object>();
			ArrayList<Object> S3 = new ArrayList<Object>();
			for(int i = 0; i < array.size()-1; i++) {
				if((int)array.get(i) < m)
					S1.add(array.get(i));
				else if((int)array.get(i) == m)
					S2.add(array.get(i));
				else
					S3.add(array.get(i));
			}
			
			// Compare size of lists to k and drill through recursive calls to return m when
			// it is kth smallest element.
			if(S1.size() >= k)
				return Select2(S1, k);
			else if(S1.size() + S2.size() >= k)
				return m;
			else
				return Select2(S3, k-S1.size()-S2.size());
		}
	}
	
	/**
	 * Quicksort from text p176 using Hoare partition.
	 * @param array of type List<Object>
	 * @param l of type int, start of subarray
	 * @param r of type int, end of subarray
	 */
	public void Quicksort(List<Object> array, int l, int r) {
		if(l < r) {
			int s = HoarePartition(array, l, r);
			Quicksort(array, l, s-1);
			Quicksort(array, s+1, r);
		}
	}
	
	/**
	 * Hoare partition from text p178.
	 * @param array of type List<Object>
	 * @param l of type int, start of subarray
	 * @param r of type int, end of subarray
	 * @return int
	 */
	public int HoarePartition(List<Object> array, int l, int r) {
		int p = ((int)array.get(l));
		int i = l;
		int j = r+1;
		do {
			this.setNumberComparisons(this.getNumberComparisons()+1);
			do {
				i++;
				this.setNumberComparisons(this.getNumberComparisons()+1);
			} while(i < r && (int)array.get(i) < p);
			do {
				j--;
				this.setNumberComparisons(this.getNumberComparisons()+1);
			} while(j >= 0 && (int)array.get(j) > p);
			int temp = (int)array.get(i);
			array.set(i, array.get(j));
			array.set(j, temp);
		} while(i < j);
		int temp = (int)array.get(i);
		array.set(i, array.get(j));
		array.set(j, temp);
		temp = (int)array.get(l);
		array.set(l, array.get(j));
		array.set(j, temp);
		return j;
	}

	public List<Object> getArray() {
		return array;
	}

	public void setArray(List<Object> array) {
		this.array = array;
	}

	public int getNumberComparisons() {
		return numberComparisons;
	}

	public void setNumberComparisons(int numberComparisons) {
		this.numberComparisons = numberComparisons;
	}
	
	
}