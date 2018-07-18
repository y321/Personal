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
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
	    public static String toAddZero(int n){
			String s=Integer.toString(n);
		 	if(s.length()==1){
		 		s="0"+n;
			}
		 	return s;
		}


		private static String midStr1 =  "";
		private static String midStr2 =  "";
		public static String getStrDate(Calendar calendar){
			int year;
			int month;
			int day;
			int hour;
			int minute;
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minute = calendar.get(Calendar.MINUTE);
			if (minute<10){
				midStr2 = "0";
			}
			if (hour<10){
				midStr1 = "0";
			}
			return year + "年" + month + "月" + day + "日 " +midStr1 + hour + ":"+ midStr2 + minute;
		}
	  
	}  
	

