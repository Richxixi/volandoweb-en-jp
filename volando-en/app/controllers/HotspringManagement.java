package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Customer;
import models.GuestBook;
import models.Hotspring;
import models.Restaurant;
import models.Room;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)

public class HotspringManagement extends Controller {
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
		
		List<Hotspring> hotspring = Hotspring.findAll();
		renderJSON(hotspring);
    }
	
	public static void addhotspring() throws FileNotFoundException 
	{
	/*	Hotspring hotspring = new Hotspring();
		hotspring.save();
        redirect("/HotspringManagement" + "/" + hotspring.getId() + "/edithotspring"); */
		        //Hotspring hotspring = new Hotspring();
        //hotspring.save();
        redirect("/HotspringManagement" + "/0/edithotspring");

    }
	public static void edithotspring(long id) throws FileNotFoundException 
	{
		init();
		
		//final Hotspring hot = Hotspring.findById(id);
		        final Hotspring hot;
        if (id ==0)
            hot =  new Hotspring();
        else
            hot = Hotspring.findById(id);


        render(hot, hot.getId());
    }
	
	public static void updatehotspringbyID(long id,String name, String pic_location,
			String pic_location2,String pic_location3,String pic_location4,String pic_location5,
			String pic_location6,String pic_location7,
			        /*    String prize_day,String prize_holigy, float size, String description, String prize_day2, String prize_day3) throws ParseException */

                        String prize_day,String prize_holigy, float size, String description, String prize_day2, String prize_day3,String information) throws ParseException
	{
		//final Hotspring hot = Hotspring.findById(id);
		final Hotspring hot;
        if (id ==0)
            hot =  new Hotspring();
        else
            hot = Hotspring.findById(id);

		hot.description =description;
		hot.name = name;
		hot.person_num  = 0;
		hot.pic_location = pic_location;
		hot.pic_location2 = pic_location2;
		hot.pic_location3 = pic_location3;
		hot.pic_location4 = pic_location4;
		hot.pic_location5 = pic_location5;
		hot.pic_location6 = pic_location6;
		hot.pic_location7 = pic_location7;
		hot.prize_day = prize_day;
		hot.prize_holigy = prize_holigy;
        hot.prize_day2 = prize_day2;
        hot.prize_day3 = prize_day3;
		hot.size = size;
        hot.information = information; // francy modified
		hot.save();
		
		redirect("/HotspringManagement" + "/" + hot.getId() + "/edithotspring");
	}
	
	public static void updatehotspring(Hotspring hot) 
	{
		hot.save();
        index();
	}
	
	public static void deleteHotspring(long id) {
		final Hotspring hotspring = Hotspring.findById(id);
		hotspring.delete();
		redirect("/HotspringManagement/records");
	}
	
	public static void records()
	{
		init();
		
		List<Hotspring> hots = Hotspring.findAll();
		render(hots);
	}
	 
}
