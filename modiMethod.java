import java.util.*;  
public class modiMethod{ 
	// global variable
	static boolean found = false , lastScanRow = false;
	static int row=0,col=0,pos = 0,start=-1,countloop=0;
	static int[][] c;
	static int[][] totalSupply;
	static int[][] p;
	static int[] u;
	static int[] v;
	static ArrayList<Integer> loopArray = new ArrayList<>();
	static ArrayList<Integer> loopRejected = new ArrayList<>();
	// main function	
 public static void main(String args[]){  
	 Scanner sc = new Scanner(System.in);
	 row = sc.nextInt();
	 col = sc.nextInt();
	 c = new int[row][col];
	 u = new int[row];
	 v = new int[col];
	 totalSupply = new int[row][col];
	 for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			c[i][j] = sc.nextInt();
		}
	}
	 int[] supply = new int[row];
	 int[] demand = new int[col];
	 for(int i=0;i<row;i++){
		 supply[i] = sc.nextInt();
	 }
	 for(int i=0;i<col;i++){
		 demand[i] = sc.nextInt();
	 }
	 for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(supply[i] == 0 || demand[j]==0){
					totalSupply[i][j] = 0;
				}else if(supply[i]>demand[j]){
					
					supply[i] = supply[i] - demand[j];
					totalSupply[i][j] = demand[j];
					demand[j]=0;
				}else{
					demand[j] = demand[j] - supply[i];
					totalSupply[i][j] = supply[i];
					supply[i]=0;
				}
			}
		}
	 calculate();
	 boolean status  = pij();
	 int num = 0;
	 while(status){
		 loopArray.clear();
		 loopRejected.clear();
		 pos = 0;
		 int location = location();
		 start = location;
		 loopArray.add(location);
		 loopFind(location,lastScanRow);
		 changeSupply();
		 calculate();
		 status  = pij();
		 num++;
	 }
	 int value = 0;
	 for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
//				System.out.print("	"+totalSupply[i][j]);
				value += totalSupply[i][j]*c[i][j]; 
			}
		}
	 System.out.println("\n\n value : "+value);
 }  
 
 	// calculate ui and vj
 	static void calculate(){
 		ArrayList<Boolean> check = new ArrayList<>();
 		int count = 0;
 		for (int i = 0; i < row; i++) {
 			for (int j = 0; j < col; j++) {
 				v[j] = Integer.MAX_VALUE;
 				check.add(false);
 			}
 			u[i]=Integer.MAX_VALUE;
 			}
 		u[0]=0;
 		do{
 			count=0;
 		for (int i = 0; i < row; i++) {
 			for (int j = 0; j < col; j++) {
 				if(totalSupply[i][j]!=0){
 					if(!check.get(count)){
 						if(v[j]!=Integer.MAX_VALUE&&u[i] == Integer.MAX_VALUE){
 	 						u[i] = c[i][j] - v[j];
 	 						check.remove(count);
 	 						check.add(count, true);
 	 					}else if(u[i]!=Integer.MAX_VALUE&&v[j]==Integer.MAX_VALUE){
 	 						v[j] = c[i][j] - u[i];
 	 						check.remove(count);
 	 						check.add(count, true);
 	 					}
 					}
 				}else{
						check.remove(count);
						check.add(count, true);
 				}
 				count++;
 			}
 		}
 		}while(check.contains(false));
 	}
 	
 	// calculate pij and check status
 	static boolean pij(){
 		p = new int[row][col];
 		boolean status = false;
 		for (int i = 0; i < row; i++) {
 			for (int j = 0; j < col; j++) {
 				if(totalSupply[i][j]==0){
 						p[i][j] = u[i] + v[j] - c[i][j] ;
 						if(p[i][j] > 0) status = true;
 					}
 				else{
 					p[i][j] = 0;
 				}
 				}
 			}
// 		for (int i = 0; i < row; i++) {
// 			for (int j = 0; j < col; j++) {
// 				System.out.print("   "+p[i][j]);
// 			}
// 			System.out.println("");
// 		}
 		return status;
 	}
 	// finding smallest pij
 	static int location(){
 		int max=0,loc=0;
 		 for (int i = 0; i < row; i++) {
 			for (int j = 0; j < col; j++) {
 				if(totalSupply[i][j]==0){
 					if(p[i][j]>max){
 	 					max = c[i][j];
 	 					loc = i*10+j;
 	 				}
 				}
 			}
 		}
 		 return loc;
 	}
 	//finding smallest loop
 	static void loopFind(int location,boolean lastScanRow){
 		 int r = location/10,c=location%10;
 		 found = false;
 		 //start from min and parse both row and col as its first time
 		int condition=0;
 		 if(pos == 0){
 			 for(int i=0;i<col;i++){
 				 if(totalSupply[r][i]!=0){
 					 if(loopRejected.contains((Object)(r*10+i))||loopArray.contains((Object)(r*10+i))){ 
 					 }
 					 else{
 						 found = true;
 						 location = r*10+i;
 						lastScanRow  = true;
 	 					condition = 1;
 	 					break; 
 					 }
 				 }
 			 }
 			 if(condition==0){
 				for(int i=0;i<row;i++){
 	 				 if(totalSupply[i][c]!=0){
 	 					 if(loopRejected.contains((Object)(i*10+c))||loopArray.contains((Object)(i*10+c))){
 	 					 }
 	 					 else{
 	 						found = true;
 	 						 location = i*10+c;
 	 						lastScanRow = false;
 	 	 					break; 
 	 					 }
 	 				 }
 	 			 }
 			 }
 		 } 
 		 //after finding the first totalsupply check for second
 		 else{
 			 if(!lastScanRow){
 				for(int i=0;i<col;i++){
 					countloop++;
 					 if(totalSupply[r][i]!=0){
 						 if(loopRejected.contains((Object)(r*10+i))||loopArray.contains((Object)(r*10+i))){
 						 }
 						 else{
 							 location = r*10+i;
 							lastScanRow  = true;
 		 					condition = 1;
 		 					found = true;
 		 					break; 
 						 }
 					 }
 					else if(r==start/10 && i == start%10){
 						location = start;
 						break;
 					}
 				 } 
 			 }
 			 else{
 					for(int i=0;i<row;i++){
 						countloop++;
 		 				 if(totalSupply[i][c]!=0){
 		 					 if(loopRejected.contains((Object)(i*10+c))||loopArray.contains((Object)(i*10+c))){ 
 		 					 }
 		 					 else{
 		 						 found = true;
 		 						 location = i*10+c;
 		 						lastScanRow = false;
 		 	 					break; 
 		 					 }
 		 				 }
 		 				else if(i==start/10 && c == start%10){
 	 						location = start;
 	 						break;
 	 					}
 		 			 }
 			 }
 		 }
 		if(start != location){
 		if(found){
 				countloop = 0;
				pos++; 
				//if the last parse was col wise then check new for row wise
				 //if found repeat that method again until reached to min value
					loopArray.add(location);
					loopFind(location, lastScanRow);
			 }else if((countloop < row&&lastScanRow==true)||(countloop < col&&lastScanRow==false)){
				 loopRejected.add(location);
				 loopArray.remove((Object)location);
				 lastScanRow = !lastScanRow;
				 loopFind(loopArray.get(loopArray.size()-1), lastScanRow);
			 }else{
				 countloop = 0;
				 pos--;
				 loopRejected.add(location);
				 
				 loopArray.remove((Object)location);
				 lastScanRow = !lastScanRow;
				 loopFind(loopArray.get(loopArray.size()-1), lastScanRow); 
			 }
 		}else{
// 			for (int is : loopArray) {
//				System.out.print("  arrayloop : "+is);
//			}
// 			System.out.println("");
// 			for (int is : loopRejected) {
//				System.out.print("  rejectedloop : "+is);
//			}
// 			System.out.println("");
 		}
 	}
 	static void changeSupply(){
 		int[] array = new int[loopArray.size()];
 		int i=0;
 		int k=1;
 		int min=Integer.MAX_VALUE;
 		for (Integer is : loopArray) {
			array[i] = totalSupply[is/10][is%10];
			i++;
		}
 		for (int j = 0; j < array.length; j++) {
// 			System.out.println("array : "+array[j]);
 			if(j%2!=0)
			if(min>array[j]){
				min = array[j];
//				System.out.println("min : " + min );
			}
		}
 		int v = 0;
 		for (Integer is : loopArray) {
 			if(v%2!=0)
 				totalSupply[is/10][is%10] -= min;
 			else
 				totalSupply[is/10][is%10] += min;
 			v++;
		}
// 		for (int j = 0; j < totalSupply.length; j++) {
//			for (int j2 = 0; j2 < totalSupply[j].length; j2++) {
//				System.out.print(" 	"+totalSupply[j][j2]);
//			}
//			System.out.println("");
//		}
 	}
}  



