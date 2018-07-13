package com.zucc.yxr31501359.DBService;


import  java.util.Date;
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.*;  
import java.text.SimpleDateFormat;  

	  
	public class Time {  
	    /*ʱ��ȴ�С*/
		 public  String getCurrentTime(){
			 
			 Date currentTime = new Date();  
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		        String dateString = formatter.format(currentTime);  
		        return dateString;  
		    }  
	    public  int timeCompare(String t1,String t2) {  //t1����t2����ֵ����Ϊ1
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			try {
				c1.setTime(formatter.parse(t1));
				c2.setTime(formatter.parse(t2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int result = c1.compareTo(c2);
			return result;
		}
		public Date toDate(String t1){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = null;
			try {
				date=formatter.parse(t1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
	    public static void main(String [] args){
	    	Time tc = new Time();
	    	//int t=tc.timeCompare(tc.getCurrentTime(), "2008-01-01 01:00:00");

	    	//System.out.println(t);
	    	System.out.println(tc.getCurrentTime());
			System.out.println(tc.toDate("2008-01-01 01:00:00"));
	    	
	    }
	  
	}  
	

