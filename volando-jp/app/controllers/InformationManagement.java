package controllers;

import java.io.File;
import java.io.FileNotFoundException;
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
public class InformationManagement extends Controller {
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
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        init();
		List<Information> information = Information.find("byIsDelete", false).fetch();
		
		//List<Information> information = Information.findAll();
		renderJSON(information);
    }
	
	public static void list() {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        init();
		List<Information> information = Information.find("byIsDelete", false).fetch();
		
		render(information);
    }
	

	public static void add() throws FileNotFoundException 
	{
		Information information = new Information();
		information.create_time = new Date();
		information.isDraft = true;
		information.isDelete = false;
		information.save();
        redirect("/InformationManagement" + "/" + information.getId() + "/editInfo");
    }
	public static void editInfo(long id) throws FileNotFoundException 
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        init();
		final Information information = Information.findById(id);
        render(information,information.getId());
    }
	
	public static void show(long id) 
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        init();
		final Information information = Information.findById(id);
		render( information);
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
	
	
	public static void updateInformationByID(long id,String title,String type,String order_num,
			String content, String tag) throws ParseException, FileNotFoundException 
			//, String saveAsDraft
	{
		final Information information = Information.findById(id);
		information.title = title;
		information.type = type;
		information.order_num = order_num;
		information.content = content;
		information.tag = tag;
		
		information.update_time = new Date();
		//System.out.println("saveAsDraft=="+saveAsDraft);
		/*if(saveAsDraft.equals("yes")){
			information.isDraft = true;
		}
		else{
			information.isDraft = false;
		}*/
		information.isDraft = false;
		
		information.save();
		editInfo(id);
        //show(id);
	}
	
	public static void deleteRecord(long id) {
		Information information = Information.findById(id);
		if(information != null){
			information.isDelete = true;
			information.save();
		}
		
		redirect("/InformationManagement/list");
	}
	 
}
