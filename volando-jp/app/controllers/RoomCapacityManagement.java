package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import models.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Scope.Session;

public class RoomCapacityManagement extends Controller
{

    public static void index()
    {
       render();
    }
    public static void editRoomCapacity(long id)
    {
    	Room room = Room.findById(id);
        render(room);
    }
    
    public static void listRooms()
    {
    	List<Room> rooms = Room.findAll();
		System.out.println("Room Number " + rooms.size());
		render(rooms);
    }
    public static void listRoomcapacity(long id)
    {
    	List<Room_capacity> capacities = Room_capacity.find("byRoom_id",id).fetch();
		render(capacities);
    }
    public static void updateRoomCapacity(long id,String open_date, String day_price, 
    		String holidy_price, String day, String room_number, String holidy,String room_id)
    {
    	try
    	{
    		DateFormat df = new SimpleDateFormat("yyyy/MM");
            String[] days = day.split(",");
            String[] holidys = holidy.split(",");
            ArrayList<String> daylist = new ArrayList<String>(Arrays.asList(days));
            ArrayList<String> holidaylist = new ArrayList<String>(Arrays.asList(holidys));
        	Date openDate = df.parse(open_date);
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(openDate);
        	int month = calendar.get(Calendar.MONTH);
        	while (true)
        	{
        		
        		Date currentDay = calendar.getTime();
        		
        		int price = 0;
        		String dayOFWeek = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)) ;
        		System.out.println(currentDay.toString()+"XXX" + dayOFWeek);
        		if (daylist.contains(dayOFWeek))
        		{
        			price = Integer.parseInt(day_price);
        		}
        		else 
        		{	
        			price = Integer.parseInt(holidy_price);
    			}
        		
        		
        		int capacity = Integer.parseInt(room_number);
        		Room_capacity rcapacity = new Room_capacity();
        		rcapacity.capicity = capacity;
        		rcapacity.room_date = currentDay;
        		rcapacity.prize = price;
        		rcapacity.room_id  = Integer.parseInt(room_id);
        		rcapacity.save();
        		
        		System.out.println("Date "+currentDay.toString());
        		System.out.println("Price "+price);
        		System.out.println("Capicity "+capacity);
        		
        		calendar.add(Calendar.DAY_OF_MONTH, 1);
        		
        		int currentMonth = calendar.get(Calendar.MONTH);
        		
        		System.out.println("Month "+month);        		
        		System.out.println("Current Month "+currentMonth);
    			if (currentMonth != month)
    				break;
    		}
    		
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
			// TODO: handle exception
		}
    	redirect("/RoomCapacityManagement/listRoomcapacity/"+room_id);
    	
    }
}
