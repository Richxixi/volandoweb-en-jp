package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.*;
import org.apache.commons.io.FilenameUtils;


import play.data.Upload;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.With;

@With(Secure.class)
public class MeetingManagement extends Controller {
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
		List<Meeting> meeting = Meeting.find("byIsDelete", false).fetch();
		
		//List<Information> information = Information.findAll();
		renderJSON(meeting);
    }
	
	public static void list() {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);


        init();
		List<Meeting> meeting = Meeting.find("byIsDelete", false).fetch();
		render(meeting);
    }
	

	public static void add() throws FileNotFoundException 
	{
		Meeting meeting = new Meeting();
		meeting.create_time = new Date();
//		meeting.isDraft = true;
		meeting.isDelete = false;
		meeting.save();
        redirect("/MeetingManagement" + "/" + meeting.getId() + "/editInfo");
    }
	public static void editInfo(long id) throws FileNotFoundException 
	{
		init();
		final Meeting meeting = Meeting.findById(id);
        render(meeting,meeting.getId());
    }
	
	public static void show(long id) 
	{
		init();
		final Meeting meeting = Meeting.findById(id);
		render( meeting);
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
	
	
	public static void updateMeetingByID(long id,String title,String type,String order_num,
			String content, String start_time, String end_time, String publish_time, String saveAsDraft,String pic_location) throws ParseException, FileNotFoundException
	{
       // System.out.println(pic_location);
		final Meeting meeting = Meeting.findById(id);
		meeting.title = title;
		meeting.type = type;
		meeting.order_num = order_num;
		meeting.content = content;
        meeting.iconUrl = pic_location;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
/*		try {
			meeting.start_time = dateFormat.parse(start_time);
			meeting.end_time = dateFormat.parse(end_time);
			meeting.publish_time = dateFormat.parse(publish_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
		
		meeting.update_time = new Date();
		
		if(saveAsDraft.equals("yes")){
			meeting.isDraft = true;
		}
		else{
			meeting.isDraft = false;
		}
		
		meeting.save();
		editInfo(id);
        //show(id);
	}
	
	public static void deleteRecord(long id) {
		Meeting meeting = Meeting.findById(id);
		if(meeting != null){
			meeting.isDelete = true;
			meeting.save();
		}
		
		redirect("/MeetingManagement/list");
	}
	
	 
}
