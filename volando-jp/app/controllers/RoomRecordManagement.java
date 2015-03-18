
package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Restaurant_record;
import models.Room;
import models.Room_record;
import models.User;

import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Scope.Session;

@With(Secure.class)
public class RoomRecordManagement extends Controller {
	public static void index() {
		
		List<Room_record> room_record= Room_record.findAll();
		renderJSON(room_record);
    }
	
	public static void addroomrecordbyID(long room_id) throws FileNotFoundException 
	{
		Room_record room_record = new Room_record();
		room_record.save();
        redirect("/RoomRecordManagement" + "/" + room_record.getId()+"/"+ room_id+ "/editroomrecord");
    }
	public static void addroomrecord() throws FileNotFoundException 
	{
		List<Room> rooms= Room.findAll();
		render(rooms);
		
    }
	public static void editroomrecord(long id,long room_id) throws FileNotFoundException 
	{
		Room room = Room.findById(room_id);
		
		String email = Session.current().get("email");
        System.out.println("Admin.profile(): email = " + email);
        User user = User.find("byEmail", email).first();
        Restaurant_record restaurant_record = new Restaurant_record();
		final Room_record room_record = Room_record.findById(id);
		
		
        render(room_record, room_record.getId(),user,restaurant_record,room);
    }
	
	//By Kyo
	public static void showroomrecord(long room_id,long restaurant_id) {
		final Room_record room_record = Room_record.findById(room_id);
		final Restaurant_record restaurant_record = Restaurant_record.findById(restaurant_id);
		render(room_record, room_record.getId(),restaurant_record);
	}
	
	public static void updateroomrecordbyID(long id,long room_id,int restaurant_record_id,
			int discount_room_id,int person_number,String pay_approach, String other_requirement,
			String checkin_date,String live_day,Date checkout_date,String communicate_approach,
			String habit,String phone,String members,String communicate_time,String VAT) throws ParseException 
	{
		String email = Session.current().get("email");
		final Room_record room_record = Room_record.findById(id);
		room_record.customer_email =email;
		room_record.room_id = room_id;
		room_record.restaurant_record_id = restaurant_record_id;
		room_record.discount_room_id = discount_room_id;
		room_record.person_number = person_number;
		room_record.pay_approach = pay_approach;
		room_record.other_requirement = other_requirement;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date_checkin = sdf.parse(checkin_date);
		
		room_record.checkin_date = date_checkin;
		if (live_day.equalsIgnoreCase(""))
			live_day = "1";
		int day = Integer.parseInt(live_day);
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(room_record.checkin_date); 
		calendar.add(Calendar.DATE, day);
		room_record.checkout_date = calendar.getTime();
		room_record.communicate_approach = communicate_approach;
		room_record.communicate_time = communicate_time;
		room_record.VAT = VAT;
		room_record.save();
		final Restaurant_record restaurant_record = new Restaurant_record();
		restaurant_record.customer_email =email;
		restaurant_record.restaurant_id = restaurant_record_id;
		restaurant_record.discount_id = discount_room_id;
		restaurant_record.person_number = person_number;
		restaurant_record.pay_approach = pay_approach;
		restaurant_record.other_requirement = other_requirement;
		
		Date order_date_d = sdf.parse(checkin_date);
		restaurant_record.order_date = order_date_d;
		restaurant_record.communicate_time = communicate_time;
		restaurant_record.VAT = VAT;
		restaurant_record.phone = phone;
		restaurant_record.habit = habit;
		restaurant_record.members = members;
		restaurant_record.save();
		
        //index();
		showroomrecord(id,restaurant_record.getId());
	}
	
	public static void updateroomrecord(Room_record room_record) 
	{
		room_record.save();
        index();
	}
	
	public static void forms() {
		render();
	}
	 
}
