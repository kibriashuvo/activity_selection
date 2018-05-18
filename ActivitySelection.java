import java.util.*;

//--------------Class for desired Input Structure---------------------------------

class Activity {
	int id;							//for maintaining the sequence while input 
	int start;
	int end;
	int profit;

	public Activity() {
		
	}

	Activity(int s, int f,int id) {
		start = s;
		end = f;
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

}


//-------------------This comparator is designed to sort the Array of "Activities" by maintaining the finishing time in ascending order------------

class compareActivity implements Comparator<Activity> {

	public int compare(Activity j1, Activity j2) {
		// TODO Auto-generated method stub
		if (j1.getEnd() >= j2.getEnd())
			return 1;
		else
			return -1;

	}

}

//---------------- This Comparator is designed to maintain the final result according to their insertID----------

class compareID implements Comparator<Activity> {

	public int compare(Activity j1, Activity j2) {
		// TODO Auto-generated method stub
		if (j1.getId() >= j2.getId())
			return 1;
		else
			return -1;

	}

}

class ActivitySelection {

	static void maxProfit(Activity[] arr, int n) {

		//-----------Using above class "compareActivity" to sort the Array of activities using template function------------------
		
		Arrays.sort(arr, new compareActivity());

		int[] maxProfit = new int[n + 1];					//Final Result in this Array
		int[] p = new int[n + 1];							//Most recent non-conflicting activity for each particular activity
		int[] trace = new int[n + 1];						//For keeping track of best combination of activities 

		//----------------Here we took these Array's 1 element bigger that input "n" just to maintain the Activities aligned with the 
		//----------------Index no. of Arrays.
		
		maxProfit[0] = 0;			//------------For maxProfit[] this is the base case			
		p[0] = 0;
		trace[0] = 0;
		
		

		//-----------------Finding the most recent activity that ended before each "i" which was started-------------
		
		for (int i = 1; i < n + 1; i++) {
			int index = 0;
			for (int j = i - 1; j > 0; j--) {
				if (arr[i].getStart() >= arr[j].getEnd()) {
					index = j;
					break;
				}
			}
			p[i] = index;

		}
		
		//------------------Main Working Function--------------------------------
		//----------------The activity is either included if , Profit(most recent ended activity(non-conflicting)) + 
		//----------------Profit(current activity) is Greater than previously calculated maxProfit.----------------
		//----------------Base case maxProfit[0]=0 (i.e- No activity "0" Profit)------------------------------------
		
		
		for (int i = 1; i < n + 1; i++) {
			int incProfit = arr[i].profit + maxProfit[p[i]];
			int excProfit = maxProfit[i - 1];

			if (incProfit >= excProfit) {
				maxProfit[i] = incProfit;
				trace[i] = p[i];						//Building the trace array
			} else {
				maxProfit[i] = excProfit;
				trace[i] = i - 1;

			}

		}
		
		//-------------This PriorityQueue is used only to Print the Activities in the order they were inserted---------------
		//-------------We made a comparator to compare the insertID of each activity and inserted in ascending order---------
		
		PriorityQueue<Activity> q = new PriorityQueue<Activity>(new compareID());
		
		int i = n;
		while (i > 0) {
			if(trace[i]==p[i]){
				q.add(arr[i]);
								
			}
			i = trace[i];
		
		}

		while (!q.isEmpty()) {
			System.out.print(q.poll().id);
			System.out.print(" ");
		}
		System.out.print("= "+maxProfit[n]);

	}

	
	//-----------Main Function---------------------
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		//---------Filtering Inputs for negative n-----------------
		if(n<0){
			System.out.println("'n' can't be negative");
			in.close();
			return;
		}
			
		
		//---------------Taking an Extra element just to maintain "index to Activity" relation-------------------
		
		Activity[] list = new Activity[n + 1];

		
		list[0] = new Activity(0, 0,0);			// Dummy Activity for 0 index

		for (int i = 1; i < n + 1; i++) {
			Activity a = new Activity();
			a.id =i;
			a.start = in.nextInt();
			a.end = in.nextInt();
			a.profit = in.nextInt();
			
			//---------Filtering invalid Activity inputs---------------------
			if(a.start<0 || a.end<0 || a.end<a.start || a.end==a.start || a.profit<0){
				System.out.println("Invalid Activity Description.");
				in.close();
				return;
			}
				
			list[i] = a;
		}
		in.close();

		maxProfit(list, n);

	}

}
