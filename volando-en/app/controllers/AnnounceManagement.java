package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;


import models.*;

import play.data.Upload;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.With;

@With(Secure.class)
public class AnnounceManagement extends Controller {
	public static void insertCRM(String siteName)
    {
        User user = User.find("byEmail", session.get("email")).first();
        if (user != null)
        {
            CRM_record crm = new CRM_record();
            crm.email = user.email;
            crm.phone=user.phone;
            crm.site = siteName;
            crm.save();
        }
    }
	private static void init(){
		User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

		
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
		List<Announce> announce = Announce.find("byIsDelete", false).fetch();
		
		//List<Information> information = Information.findAll();
		renderJSON(announce);
    }
	
	public static void list() {
		User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);


		init();
		List<Announce> announce = Announce.find("byIsDelete", false).fetch();
		render(announce);
    }
	

	public static void add() throws FileNotFoundException 
	{
		Announce announce = new Announce();
		announce.create_time = new Date();
		announce.isDraft = true;
		announce.isDelete = false;
		announce.save();
        redirect("/AnnounceManagement" + "/" + announce.getId() + "/editInfo");
    }
	public static void editInfo(long id) throws FileNotFoundException 
	{
		init();
		final Announce announce = Announce.findById(id);
        render(announce,announce.getId());
    }
	
	public static void show(long id) 
	{
		init();
		final Announce announce = Announce.findById(id);
		render( announce);
	}
	
	
	public static void imageHandler(Upload imageName)
	{
		if (imageName != null) {
			String dateString;
			Date date = new Date();
			dateString = String.valueOf(date.getTime());

	        File file = imageName.asFile();
	        String newFileName = dateString + "." + FilenameUtils.getExtension(file.getPath());
	        file.renameTo(new File("public/upload/cleditor", newFileName));
	        
	        String html = "<div id='image'>/public/upload/cleditor/" + newFileName + "</div>";
	        renderHtml(html);
	        
    	} else {
    		
    	}
    	
		
	}
	
	
	public static void updateAnnounceByID(long id,String title,String type,String order_num,
			String content, String start_time, String end_time, String publish_time, String saveAsDraft,String pic_location) throws ParseException, FileNotFoundException 
	{
		System.out.println(pic_location);
		final Announce announce = Announce.findById(id);
		announce.title = title;
		announce.type = type;
		announce.order_num = order_num;
		System.out.println("=============================="+content);
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
		editInfo(id);
        //show(id);
	}
	
	public static void deleteRecord(long id) {
		Announce announce = Announce.findById(id);
		if(announce != null){
			announce.isDelete = true;
			announce.save();
		}
		
		redirect("/AnnounceManagement/list");
	}
	
	 
}
