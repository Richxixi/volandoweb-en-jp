package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.*;
import play.mvc.Controller;
import play.mvc.Scope.Session;
import play.mvc.With;

@With(Secure.class)
public class Admin extends Controller {
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
		
		List<User> users = User.findAll();
		int users_size = users.size();
	
		//Get messages
		List<GuestBook> dash_guestbook = GuestBook.find("byIsDelete", false).fetch(5); 
		
		render(dash_guestbook);
	}

	public static void post() {
		init();
		
		List<User> users = User.findAll();
		int users_size = users.size();
		render();
	}

	public static void records() {
		init();
		
		List<User> users = User.findAll();
		int users_size = users.size();
		render();
	}

	public static void profile() {
		String email = Session.current().get("email");
		System.out.println("Admin.profile(): email = " + email);
		User user = User.find("byEmail", email).first();
		
		showUser(Long.valueOf(user.getId().toString()));
	}

	public static void showUser(Long id) {
		User user = User.findById(id);
		
		
		render(user, user.getId(), session.get("userId"));
	}

	public static void findMyRoom() {
		String email = Session.current().get("email");
		List<Room_record> rooms = Room_record.find("customer_email", email)
				.fetch();
		System.out.println("findMyRoom: " + rooms);
		renderXml(rooms);
	}

	public static void findMyRestaurant() {
		String email = Session.current().get("email");
		List<Restaurant_record> rests = Restaurant_record.find(
				"customer_email", email).fetch();
		System.out.println("findMyRestaurant: " + rests);
		renderXml(rests);
	}

	public static void findMyHotspring() {
		String email = Session.current().get("email");
		List<Hotspring_record> hots = Hotspring_record.find("customer_email",
				email).fetch();
		System.out.println("findMyHotspring: " + hots);
		renderXml(hots);
	}

	public static void findMyTraffic() {
		String email = Session.current().get("email");
		List<Traffic_record> traffics = Traffic_record.find("customer_email",
				email).fetch();
		System.out.println("findMyTraffic: " + traffics);
		renderXml(traffics);
	}

	public static void quickUpdateInformation(String title,String type,
			String content, String saveAsDraft) throws ParseException, FileNotFoundException 
	{
		Information information = new Information();
		information.create_time = new Date();
		
		information.title = title;
		information.type = type;
		information.content = content;
		information.order_num = "";
		information.tag = "";
		information.isDelete = false;
		information.update_time = new Date();
		
		if(saveAsDraft.equals("yes")){
			information.isDraft = true;
		}
		else{
			information.isDraft = false;
		}
		
		information.save();
		redirect("/InformationManagement" + "/" + information.getId() + "/editInfo");
	}

	public static void quickUpdateAnnounce(long id,String title,String type,String order_num,
			String content, String start_time, String end_time, String publish_time, String saveAsDraft,String pic_location) throws ParseException, FileNotFoundException 
	{
		Announce announce =  new Announce();
		announce.create_time = new Date();
		announce.title = title;
		announce.type = type;
		announce.order_num = order_num;
		announce.content = content;
		announce.iconUrl = pic_location;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			announce.start_time = dateFormat.parse(start_time);
			announce.end_time = dateFormat.parse(end_time);
			announce.publish_time = dateFormat.parse(publish_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		announce.update_time = new Date();
		
		if(saveAsDraft.equals("yes")){
			announce.isDraft = true;
		}
		else{
			announce.isDraft = false;
		}
		
		announce.save();
		id = announce.id;
		redirect("/AnnounceManagement" + "/list");
	}
	
}