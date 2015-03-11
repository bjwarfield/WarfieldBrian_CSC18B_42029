
public class arrayTest {
	public static void main(String[] args){
                //instantiate base class
		baseSort b = new baseSort(10);
		//call print method with and without sort
                System.out.println("baseSort array output:");
                b.print(false);
		System.out.println("baseSort array ouput with sort:");
                b.print(true);
                System.out.println("baseSort array ouput without sort:");
		b.print(false);
		System.out.println();

		sortSearch s = new sortSearch(25);
                System.out.println("sortSearch array output:");
		s.print(false);
                System.out.println("sortSearch array output with sort:");
		s.print(true);
                System.out.println();
                
                //call binary search function
                System.out.println("Binary Search in subclass:");
		s.binarySearch(55);
                //binary search not available to superclass
                //System.out.println("Binary Search in superclass:");
                //b.binarySearch(55);//compile error
                //System.out.println("Binary Search in typecasted superclass:");
                //((sortSearch)b).binarySearch(20);//produces castExeption error
                System.out.println();
                
                //instanciate new base class with inhereted instance
                baseSort p = s;
                System.out.println("new base class with inhereted instance array output:");
                p.print(false);
                
                //binary search in new class
                System.out.println("Binary Search in new object:");
                //p.binarySearch(20);
                //downcast to get access to method
                ((sortSearch)p).binarySearch(50);
                
                
	}
}
