
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

import play.data.validation.MaxSize;
import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Scope.Session;

@With(Secure.class)


public class RestaurantManagement extends Controller 
{
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
	
	public static void index() 
	{
		init();
		List<Restaurant> restaurants= Restaurant.findAll();
		renderJSON(restaurants);
    }
	
	public static void listRecords() {
		init();
		List<Restaurant_record> rest_record = Restaurant_record.findAll();		
		render(rest_record);
	}
	
	public static void addrestaurant() throws FileNotFoundException 
	{
		Restaurant restaurant = new Restaurant();
		restaurant.save();
		redirect("/RestaurantManagement" + "/" + restaurant.getId()+ "/editrestaurant");

    }
	public static void editrestaurant(long id) throws FileNotFoundException 
	{
		init();
		final Restaurant restaurant = Restaurant.findById(id);
        render(restaurant, restaurant.getId());
    }
	
	public static void updaterestaurantbyID(long id, String name, String description,
			String time, int seat_num) throws ParseException 
	{
		final Restaurant restaurant = Restaurant.findById(id);
		restaurant.name = name;
		restaurant.time = time;
		restaurant.description = description;
		restaurant.seat_num = seat_num;
		restaurant.save();
        records();
	}
	
	public static void deleteRestaurant(long id) {
		final Restaurant restaurant = Restaurant.findById(id);
		restaurant.delete();
		redirect("/RestaurantManagement/records");
	}
	
	public static void records()
	{
		init();
		List<Restaurant> rests = Restaurant.findAll();
		render(rests);
	}
	
	public static void deleteRecord(long id) {
		Restaurant_record record = Restaurant_record.findById(id);

		
		record.delete();
		
		listRecords();
	}


}

