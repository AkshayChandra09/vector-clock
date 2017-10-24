/*
Student Name: Akshay Chandrachood
*/

import java.util.*;
import java.util.Map.Entry;



public class VectorClock{
	
	private static HashMap<Integer,int[][]> p = new HashMap<Integer, int[][]>();
	private static HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
	private static int processes, events_size;
	private static Scanner input = new Scanner(System.in);

	//function to print timestamps
	public static void printTimeStamps(){
		for(int i=0;i<processes;i++){
			System.out.print("p"+(i+1)+": ");		
			for(int j=0;j<map.get(i+1);j++){
				System.out.print((j+1)+ ":(");
				for(int k=0;k<processes;k++){
					System.out.print(p.get(i+1)[j][k]);	
				}
				System.out.print(")  ");
			}
			System.out.println("\n");
		}
	}
	
	//function to initialize events in each process
	public static void init_events(){
		for(int i=0;i<processes;i++){
			System.out.println("Please Enter Number of Events in "+(i+1)+" Process: ");
			events_size = input.nextInt();		
			int[][] t_stamp = new int[events_size][processes];
			map.put(i+1, events_size);	
			for(int j=0;j<events_size;j++){
				int no = j+1;
				for(int k=0;k<processes;k++){
					if(k==i)
						t_stamp[j][k]=no;
					else
						t_stamp[j][k]=0;			
				}
			}	
			p.put(i+1, t_stamp);
		}
	}
	
	//function to calculate timestamps
	public static void communicate(int snd_pro, int snd_event, int rcv_pro, int rcv_event){
		int[][] sent = new int[events_size][processes];
		sent=p.get(snd_pro);
		int[][] recv = new int[events_size][processes];
		recv=p.get(rcv_pro);
		int[] tmp1 = sent[snd_event-1];
		int[] tmp2 = recv[rcv_event-1];

		for(int k=0;k<processes;k++){
			if(k!=rcv_pro-1){
				if(tmp1[k]>tmp2[k]){
					tmp2[k] = tmp1[k];
				}
			}	
		}
		recv[rcv_event-1] = tmp2;
		
		for(int j=rcv_event;j<map.get(rcv_pro);j++){
			for(int k=0;k<processes;k++){
				if(recv[j][k]<recv[j-1][k] && k!=(rcv_pro-1)){
					recv[j][k]=recv[j-1][k];
				}
			}	
		}
	}
	
	
	public static void main(String[] args) {
		
		int count=0;
		char contin;
		System.out.println("Please Enter Number of Processes: ");
		processes = input.nextInt();
		
		//initialize all events in a process
		init_events();
		System.out.println("\n");
		//print timestamp of initialized events
		printTimeStamps();
	 
		//ask user process sending & receiving the message
		do{
			System.out.println("Enter which process sending msg: ");
			int snd_pro = input.nextInt();
			System.out.println("Enter which event in that sending msg: ");
			int snd_event = input.nextInt();
			System.out.println("Enter which process recv msg: ");
			int rcv_pro = input.nextInt();
			System.out.println("Enter which event recv msg: ");
			int rcv_event = input.nextInt();
			//pass send & receive events to communicate function to calculate timestamp
			communicate(snd_pro,snd_event,rcv_pro,rcv_event);
			System.out.print("Any other process communicating? ");
			contin = input.next().charAt(0);
		}while(contin=='Y' || contin=='y');

		
		/*
		//Test Case 1 - given by prof.
		// 3 processes: p1-> 4 events; p2-> 4 events; p3-> 4 events
		communicate(1,1,2,2);		
		communicate(1,3,3,4);
		communicate(2,3,3,1);		
		communicate(3,2,1,2);
		
		//Test Case 2
		// 3 processes: p1-> 7events; p2-> 3events; p3-> 2events
		communicate(1,2,2,2);
		communicate(2,1,1,3);
		communicate(3,2,1,5);
		communicate(1,6,2,3);
				
		//Test Case 2
		// 3 processes: p1-> 2 events; p2-> 2 events; p3-> 3 events;
		communicate(1,1,2,1);
		communicate(3,3,2,2);
		communicate(2,2,1,2);
		*/	
			
		System.out.println("\nTimestamps are: ");	
		printTimeStamps();
	
	}

}
