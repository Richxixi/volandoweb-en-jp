package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.GuestBook;
import models.Hotspring;
import models.MealSet;
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

public class MealSetManagement extends Controller  {
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
	
	public static void index() 
	{
		List<MealSet> mealset= MealSet.findAll();
		renderJSON(mealset);
    }
	
	public static void records()
	{
		init();
		List<MealSet> mealsets = MealSet.findAll();
		render(mealsets);
	}
	
	
	public static void addmealset() throws FileNotFoundException 
	{
		MealSet mealset = new MealSet();
		mealset.save();
		redirect("/MealSetManagement" + "/" + mealset.getId()+ "/editmealset");

    }
	public static void editmealset(long id) throws FileNotFoundException 
	{
		init();
		final MealSet mealset = MealSet.findById(id);
        render(mealset, mealset.getId());
    }
	
	public static void updatemealsetbyID(long id, String name,String pic_loc, String description,
			int prize, int restaurant_id) throws ParseException 
	{
		final MealSet mealset = MealSet.findById(id);
		mealset.name = name;
		mealset.prize = prize;
		mealset.description = description;
		mealset.pic_location = pic_loc;
		mealset.restaurant_id = restaurant_id;
		mealset.save();
        records();
	}
}
