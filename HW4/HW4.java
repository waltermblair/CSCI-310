import java.util.*;

/**
 * Walter Blair - CSCI 310 - Homework 4
 * Driver tests arrays from n=10, 15, 20, ..., 50 with several k values for each list.
 * First prints original pseudorandom list and then quicksorted list.
 * Compares Quickselect from text to Select2 method from assignment by displaying their selection of
 * kth smallest element and the number of comparisons made to achieve the selection.
 * 
 * Consulted source for usage of List (https://docs.oracle.com/javase/7/docs/api/java/util/List.html)
 * 
 * @author waltermblair
 *
 */
class ExerciseSelects {
	public static void main(String[] args) {
		System.out.println("Walter Blair - CSCI 310");
		System.out.println("Homework #4\n");
		// Range of list sizes
		for(int n = 10; n < 50; n+=5) {
			/// Range of k values
			System.out.println("*********************************** n = " + n + " ***********************************");
			for(int k = 6; k < n; k+=5) {
				int a = 100;
				List<Object> array = new ArrayList<Object>();
				// Random seeded with n
				Random r = new Random(n);
				System.out.print("Original:\t");
				// Populating list
				for(int i = 0; i < n; i++) {
					array.add(r.nextInt(a));
					System.out.print(array.get(i) + " ");
				}
				System.out.println();
				// Sorting list and printing
				Select quick = new Select(array);
				quick.Quicksort(array, 0, array.size()-1);
				System.out.print("Sorted:\t\t");
				for(int i = 0; i < n; i++) {
					System.out.print(quick.getArray().get(i) + " ");
				}
				
				//select1 used for Quickselect, select2 used for Select2 algorithm
				Select select1 = new Select(array);
				Select select2 = new Select(array);
				System.out.println("\nk = " + k);
				System.out.print("Quickselect: \t" + select1.Quickselect(array, 0, array.size()-1, k));
				System.out.print("\t\tComparisons: " + select1.getNumberComparisons() + "\n");
				System.out.print("Select2: \t" + select2.Select2(array, k));
				System.out.print("\t\tComparisons: " + select2.getNumberComparisons() + "\n\n");
			}
		}
	}
}