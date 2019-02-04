package testCases;

import java.util.*;
public class WorkingDaysOfCurerntMonth {

	public static void main(String[] args)  {
        
		int day=1;
		for(;day<30;day++) {
			System.out.println(day+", "+isWorkDay(day));
		}
		System.out.println("Finish");
    	
	}
	public static boolean isWorkDay(int dayInMonth) {
		// TODO Auto-generated method stub
		boolean res=false;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        int maxDays=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(dayInMonth>=1 && dayInMonth<=maxDays) {
			
	            //first day :
	            cal.set(Calendar.DAY_OF_MONTH, 1);
	            Date firstDay = cal.getTime();
	            //System.out.println("firstDay=" + firstDay);

	            //last day
	            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	            Date lastDay = cal.getTime();
	            //System.out.println("lastDay=" + lastDay);
	            Date temp=firstDay;
//	            int day=1;
//	            do {
	            	cal.set(Calendar.DAY_OF_MONTH, dayInMonth);
		            temp = cal.getTime();
		            if(cal.get(Calendar.DAY_OF_WEEK)<6) {
		            	res=true;
		            }
		            //System.out.println("Current=" + temp+", "+cal.get(Calendar.DAY_OF_MONTH)+", "+cal.get(Calendar.DAY_OF_WEEK));
//		            day++;
//	            }while(!lastDay.equals(temp));
	    
		}
		return res;
	}
}