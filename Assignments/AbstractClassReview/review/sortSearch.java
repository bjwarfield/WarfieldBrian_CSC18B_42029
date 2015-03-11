

public class sortSearch extends baseSort {

    public sortSearch(int size) {
        super(size);
    }
    protected void binarySearch(int key){
    	int position;
    	int lowerbound = 0;
    	int upperbound = this.array.length;
    	//sort array
    	sort();

    	//find the index of middle
    	position = ((lowerbound+upperbound)/2);

    	while((this.array[position]!=key)&&(lowerbound<=upperbound)){
    		if(this.array[position]>key){
    			upperbound = position-1;
    		}else{
    			lowerbound = position +1;
    		}
    		position = ((lowerbound+upperbound)/2);
    	}
    	if (lowerbound <= upperbound){
           System.out.println("The number " + key + " was found in array index " + position);
	    }else{
        	System.out.println("Sorry, the number " + key + " is not in this array.");
	    }
    }
}
