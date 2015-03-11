
import java.util.Arrays;
import java.util.Random;

//Inherit the abstract class from 1) -> baseSort
public class baseSort extends absSort {
    //private int[] array;
    private static final Random random = new Random();

    //constructor with size of the array as input
    public baseSort (int size){
    	fillArray(size);
    }

    //fillArray(n) -> filling the array with random 2 digit numbers from 10 to 99
    @Override
    protected void fillArray(int n){
        this.array = new int[n];
    	for(int counter = 0; counter < n; counter++){
    		this.array[counter] = random.nextInt(90)+10;
    	}
    }

    //sort() -> sorts the array
    public void sort(){
		Arrays.sort(array);
	}

	//print(boolean) -> prints the array sorted or unsorted
    public void print(boolean sorted){
    	if(sorted){
    		sort();
    		System.out.println(Arrays.toString(array));
            System.out.println(array.length);
    	}else{
    		System.out.println(Arrays.toString(array));
            System.out.println(array.length);
    	}
    	
    }
}
