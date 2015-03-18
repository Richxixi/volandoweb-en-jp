
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
import models.User;

import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Scope.Session;

@With(Secure.class)

public class RestaurantRecordManagement extends Controller {
	private static void init(){
		// Get Users
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
		init();
		List<Restaurant_record> restaurant_record= Restaurant_record.findAll();
		renderJSON(restaurant_record);
    }
	public static void listrestaurants()
	{
		init();
		List<Restaurant_record> restaurant_records= Restaurant_record.findAll();
		render(restaurant_records);
	}
	public static void addrestaurantrecordByID(long restaurant_id) throws FileNotFoundException 
	{
		Restaurant_record restaurant_record = new Restaurant_record();
		restaurant_record.save();
		redirect("/RestaurantRecordManagement" + "/" + restaurant_record.getId()+"/"+ restaurant_id+ "/editrestaurantrecord");

    }
	public static void addrestaurantrecord() throws FileNotFoundException 
	{
		init();
		List<Restaurant> restaurants= Restaurant.findAll();
		render(restaurants);
		
    }
	public static void editrestaurantrecord(long id,long restaurant_id) throws FileNotFoundException 
	{
		Restaurant restaurant = Restaurant.findById(restaurant_id);
		String email = Session.current().get("email");
        System.out.println("Admin.profile(): email = " + email);
        User user = User.find("byEmail", email).first();
		final Restaurant_record restaurant_record = Restaurant_record.findById(id);
        render(restaurant_record, restaurant_record.getId(),user,restaurant);
    }
	
	//By Kyo
	public static void showrestaurantrecord(long id) {
		init();
		final Restaurant_record restaurant_record = Restaurant_record.findById(id);
		render(restaurant_record, restaurant_record.getId());
	}
	
	public static void updaterestaurntrecordbyID(long id,int restaurant_record_id,long restaurant_id,
			int discount_id,int person_number,String pay_approach,String phone,String habit,String members, String other_requirement,
			String order_date,String communicate_approach,String communicate_time,String VAT) throws ParseException 
	{
		String email = Session.current().get("email");
		final Restaurant_record restaurant_record = Restaurant_record.findById(id);
		restaurant_record.customer_email =email;
		restaurant_record.restaurant_id = restaurant_id;
		restaurant_record.discount_id = discount_id;
		restaurant_record.person_number = person_number;
		restaurant_record.pay_approach = pay_approach;
		restaurant_record.other_requirement = other_requirement;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date order_date_d = sdf.parse(order_date);
		restaurant_record.order_date = order_date_d;
		restaurant_record.communicate_time = communicate_time;
		restaurant_record.VAT = VAT;
		restaurant_record.phone = phone;
		restaurant_record.habit = habit;
		restaurant_record.members = members;
		restaurant_record.save();
        //index();
		showrestaurantrecord(id);
	}
	
	public static void updaterestaurntrecord(Restaurant_record restaurant_record) 
	{
		restaurant_record.save();
        index();
	}
	
	public static void forms() {
		render();
	}
	 
}
