package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.GuestBook;
import models.Hotspring;
import models.Restaurant;
import models.Restaurant_record;
import models.Room;
import models.Room_record;
import models.Traffic;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)

public class TrafficManagement extends Controller {
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
		init();
		
		List<Traffic> traffics = Traffic.findAll();
		renderJSON(traffics);
    }
	

	public static void listtraffics() 
	{
		init();
		
		List<Traffic> traffics = Traffic.findAll();
		render(traffics);
    }
	
	public static void addtraffic() throws FileNotFoundException 
	{
		Traffic traffic = new Traffic();
		traffic.save();
        redirect("/TrafficManagement" + "/" + traffic.getId() + "/edittraffic");
    }
	public static void edittraffic(long id) throws FileNotFoundException 
	{
		init();
		
		final Traffic traffic = Traffic.findById(id);
        render(traffic,traffic.getId());
    }
	
	public static void showtraffic(long id) 
	{
		init();
		
		final Traffic traffic = Traffic.findById(id);
		render( traffic);
	}
	
	
	public static void updatetrafficbyID(long id,String start_point,String end_point,int reserved_number,
			int seat_num,String name,Date time) throws ParseException 
	{
		final Traffic traffic = Traffic.findById(id);
		traffic.start_point = start_point;
		traffic.end_point = end_point;
		traffic.reserved_number = reserved_number;
		traffic.seat_num = seat_num;
		traffic.name = name;
		traffic.time = time;
		traffic.save();
        //showtraffic(id);
        records();
	}
	
	public static void deleteTraffic(long id) {
		final Traffic traffic = Traffic.findById(id);
		traffic.delete();
		redirect("/TrafficManagement/records");
	}
	
	public static void records()
	{
		init();
		
		List<Traffic> traffics = Traffic.findAll();
		render(traffics);
	}
	
	public static void updatetraffic(Traffic traffic) 
	{
		traffic.save();
        index();
	}
	 
}
