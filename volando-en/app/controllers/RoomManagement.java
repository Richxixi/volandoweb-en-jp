
package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.GuestBook;
import models.Hotspring;
import models.Restaurant;
import models.Restaurant_record;
import models.Room;
import models.Room_record;
import models.User;

import play.db.jpa.JPABase;
import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Scope.Session;

@With(Secure.class)
public class RoomManagement extends Controller {
	private static void init(){
		List<User> users = User.findAll();
		int users_size = users.size();
	
		renderArgs.current().put("users", users);
		renderArgs.current().put("users_size", users_size);
		
		//Get messages
		List<GuestBook> guestbooks = GuestBook.find("byIsDeleteAndStatus", false, false).fetch(); 
		int guestbooks_size = guestbooks.size();
		
		renderArgs.current().put("guestbooks", guestbooks);
		renderArgs.current().put("guestbooks_size", guestbooks_size);
		
		//Get right top numbers
		List<Restaurant> restaurants= Restaurant.findAll();
		renderArgs.current().put("restaurants_size", restaurants.size());
		
		List<Hotspring> hotspring = Hotspring.findAll();
		renderArgs.current().put("hotspring_size", hotspring.size());
		
		List<Room> rooms= Room.findAll();
		renderArgs.current().put("rooms_size", rooms.size());
		
	}
	
	public static void index() {
		
		List<Room> rooms= Room.findAll();
		
		renderJSON(rooms);
		
    }
	
	public static void addroom(long id) throws FileNotFoundException 
	{
		
		Room room = new Room();
		room.save();
        redirect("/RoomManagement" + "/" + room.getId()+"/editroom");
    }
	public static void editroom(long id) throws FileNotFoundException 
	{
		init();
		
		final Room room = Room.findById(id);
        render(room, room.getId());
    }
	
	//By Kyo
	public static void showroomrecord(long room_id,long restaurant_id) {
		init();
		
		final Room_record room_record = Room_record.findById(room_id);
		final Restaurant_record restaurant_record = Restaurant_record.findById(restaurant_id);
		render(room_record, room_record.getId(),restaurant_record);
	}
	
	public static void updateroombyID(long id, String name, String name_en, String pic_location,
			String pic_location2, String pic_location3, String pic_location4, String pic_location5,
			String pic_location6, String pic_location7, String pic_location8, String pic_location9,
			String pic_location10, String pic_location_poster,
			int prize_day, int prize_holigy, int person_num, float size,
			String description,String bad_type) throws ParseException 
	{
		final Room room = Room.findById(id);
		room.description =description;
		room.name = name;
		room.name_en = name_en;
		room.person_num  =person_num;
		room.pic_location = pic_location;
		room.pic_location2 = pic_location2;
		room.pic_location3 = pic_location3;
		room.pic_location4 = pic_location4;
		room.pic_location5 = pic_location5;
		room.pic_location6 = pic_location6;
		room.pic_location7 = pic_location7;
		room.pic_location8 = pic_location8;
		room.pic_location9 = pic_location9;
		room.pic_location_poster = pic_location_poster;
		
		room.prize_day = prize_day;
		room.prize_holigy = prize_holigy;
		room.bad_type = bad_type;
		room.size = size;
		room.save();
        records();
	}
	
	public static void deleteRoom(long id) {
		final Room room = Room.findById(id);
		room.delete();
		redirect("/RoomManagement/records");
	}
	
	public static void records()
	{
		init();
		List<Room> rooms = Room.findAll();
		
		render(rooms);
	}
	
	public static void listRecords() {
		init();
		
		List<Room_record> room_record = Room_record.findAll();
		render(room_record);
	}
	
	
	
	public static void deleteRecord(long id) {
		Room_record record = Room_record.findById(id);
		long restraurant_record_id = record.restaurant_record_id;
	
		Restaurant_record restaurant_record = Restaurant_record.findById(restraurant_record_id);
		if(restaurant_record != null){
			restaurant_record.delete();
		}
		
		
		record.delete();
		
		listRecords();
	}
}
