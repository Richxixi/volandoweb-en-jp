package controllers;

import play.*;
import play.cache.Cache;
import play.data.Upload;
import play.libs.Codec;
import play.libs.Images;
import play.modules.facebook.FbGraph;
import play.modules.facebook.FbGraphException;
import play.mvc.*;
import play.mvc.Scope.Session;

import java.io.File;
import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.mapping.Array;

import com.google.gson.JsonObject;

import models.*;

public class Application extends Controller {


    public static void index() {
    	int template_type = -1;

        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);
        String userName="";
        if (user!=null)
            userName = user.username;


    	render(template_type,userName);
    }

    public static void downLoadApp()
    {
        render();
    }

    public static void listrooms()
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        int template_type = 5;
        
		List<Room> rooms= Room.findAll();
		render(rooms, template_type);
	}
    
    public static void showRoom(long id){
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        int template_type = 3;
        
    	final Room room = Room.findById(id);
        
    	List<Room> rooms= Room.findAll();
    	
    	
		String descriptionEncrypt = "";
        if (room.description !=null)
            descriptionEncrypt = room.description.replace("\r\n","<br>").replace("\n","<br>").replace("\r","<br>");


        render(rooms, room, room.getId(),descriptionEncrypt, template_type);

    }
    
    public static void showHotspring(long id){

        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        int template_type = 3;
        
    	final Hotspring hot = Hotspring.findById(id);
        
		String descriptionEncrypt = hot.description.replace("\r\n","<br>").replace("\n","<br>").replace("\r","<br>");
		
        String informationEncrypt = hot.information.replace("\r\n","<br>").replace("\n","<br>").replace("\r","<br>");

        //render(hot, hot.getId(),descriptionEncrypt, template_type);
		        render(hot, hot.getId(),descriptionEncrypt,informationEncrypt, template_type);


    }
    
    public static void listrestaurants()
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        List<Restaurant> restaurants= Restaurant.findAll();
		render(restaurants);
	}
    public static void listhotsprings()
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        int template_type = 10;
    	
		List<Hotspring> hotsprings= Hotspring.findAll();
		render(hotsprings, template_type);
	}
    public static void listtraffics()
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        List<Traffic> traffics = Traffic.findAll();
		render(traffics);
	}
    
    public static void listInformationAll(String category, String error) {
    	int template_type = 3;
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        List<Information> information = Information.find("byTypeAndIsDeleteAndIsDraft", category, false, false).fetch();
		
		String randomID = Codec.UUID();
		
		render(template_type, information, category, randomID, error);
    }
    
    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#000");
        Cache.set(id, code, "30mn");
        renderBinary(captcha);
    }
    
    
    public static void listAnnounce(String category) {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        List<Announce> announce = Announce.find("byTypeAndIsDeleteAndIsDraft", category, false, false).fetch();

        Collections.sort(announce,
                new Comparator<Announce>() {
                    public int compare(Announce o1, Announce o2) {
                        return o2.publish_time.compareTo(o1.publish_time);
                    }
                });

		Date today = Calendar.getInstance().getTime();
		for(int i = 0; i < announce.size(); i++){
			if(announce.get(i).start_time.compareTo(today) > 0 || announce.get(i).end_time.compareTo(today) < 0){
				announce.remove(i);				
			}
		}
		
		
		render(announce, category);
    }
    
	public static void listAnnounceAll() {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        int template_type = 7;
    	
			List<Announce> announce = Announce.find("byIsDeleteAndIsDraft", false, false).fetch();
			
			
			render(template_type, announce);
	}
	
	public static void listProjectAll() {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        int template_type = 8;
		
		List<Project> project = Project.find("byIsDeleteAndIsDraft", false, false).fetch();
		
		
		render(template_type, project);
    }
	
	 public static void listMeeting(String category) {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        List<Meeting> meeting = Meeting.find("byTypeAndIsDelete", category, false).fetch();
		
		Date today = Calendar.getInstance().getTime();
				
		render(meeting, category);
    }
	
    public static void listProject(String category) {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        List<Project> project = Project.find("byTypeAndIsDeleteAndIsDraft", category, false, false).fetch();
		
		Date today = Calendar.getInstance().getTime();
		for(int i = 0; i < project.size(); i++){
			if(project.get(i).start_time.compareTo(today) > 0 || project.get(i).end_time.compareTo(today) < 0){
				project.remove(i);				
			}
		}
		
		
		render(project, category);
    }
    
    public static void listArts() {
		int template_type = 3;
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        List<Arts> arts1 = Arts.find("byIsDeleteAndType", false, "1").fetch();
		List<Arts> arts2 = Arts.find("byIsDeleteAndType", false, "2").fetch();
		List<Arts> arts3 = Arts.find("byIsDeleteAndType", false, "3").fetch();
		
		
		render(template_type, arts1, arts2, arts3);
    }
    
    public static void showPage(String category) {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        Page page = Page.find("byType", category).first();
		
		render(page, category);
    }
    
	public static void showinfo(long id) 
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        final Information information = Information.findById(id);
		render( information);
	}
	
	public static void guestbook(String type) 
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        render(type);
	}
	
	public static void guestbookMobile(String lang) 
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);


		if (user == null) {
            redirect("/login");
        } else {
        	String language = lang;
        	System.out.println("to guestbookMobile: " + language);
        	render(user, language);
        }
	}
	

	public static void guestbookList(String lang, String device)
	 
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);


		if (user == null) {
            redirect("/login");
        } else {
        	if(device.equals("mobile")){
        		List<GuestBook> gbList = GuestBook.find("byDeviceTypeAndEmailAndIsDelete", "mobile" ,session.get("email"), false).fetch();
        		String language = lang;
        		System.out.println("gList: " + language);
        		render(gbList, language);
        	}
        	else{
        		List<GuestBook> gbList = GuestBook.find("byDeviceTypeAndEmailAndIsDelete", "pc" ,session.get("email"), false).fetch();
        		render(gbList);
        		
        	}
        }
        
	}
	
	public static void mobileHome(String lang) 
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);


        if (user == null) {
        	System.out.println("lang1:" + lang);
        	mobileLogin(lang);
        } else {
        	String language = lang;
    		render(language);
        }
	}
	
	public static void mobileLogin(String lang)
	{
		System.out.println("lang2:" + lang);
		String language = lang;
		Session.current().put("mobile_language", language);
		render(language);
	}
	
	public static void guestbookForm(String type, String email, String name, String phone,
			String content, String lang, String device, String randomID, String cap, String gender, String servicescore,
			String replyway, String contacttime) 
	{

        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

		System.out.println(randomID);
		System.out.println(cap);
        validation.equals(
			cap, Cache.get(randomID)
	    ).message("驗證碼錯誤，請重新輸入！");
        System.out.println(Cache.get(randomID));
	    if(validation.hasErrors()) {
	    	redirect("/Information/list/comment?error=1");
	    }
		
		GuestBook guestbook = new GuestBook();
		guestbook.deviceType = device;
		guestbook.guestBookType = type;
		guestbook.name = name;
		guestbook.gender = gender;
		guestbook.email = email;
		
		guestbook.servicescore = servicescore;
		guestbook.replyway = replyway;
		guestbook.contacttime = contacttime;
		
		if(device != null && device.equals("pc")){
			guestbook.phone = phone;
		}
		
		guestbook.content = content;
		guestbook.lang = lang;
		guestbook.status = false;
		guestbook.view = false;
		guestbook.create_time = new Date();
		guestbook.isDelete = false;
		guestbook.save();
		
		if(device.equals("pc")){
			showGuestbook(guestbook.getId());
		} else {
			showGuestbookMobile(guestbook.getId());
			
		}
	}
	
	public static void showGuestbook(long id) 
	{
		GuestBook guestbook = GuestBook.findById(id);
		List<GuestBook_record> record = GuestBook_record.find("byGb_id", id).fetch();
		
		render(guestbook, record);
	}
	
	public static void showGuestbookMobile(long id) 
	{
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        GuestBook guestbook = GuestBook.findById(id);
		List<GuestBook_record> record = GuestBook_record.find("byGb_id", id).fetch();
		String language = session.get("mobile_language");
		System.out.println("showGuestbookMobile: " + language);
		render(guestbook, record, language);
	}
	
	public static void facebookLogin() {
	    try {
	        JsonObject profile = FbGraph.getObject("me");
	        // or use the basic api method directly -> JsonObject profile = FbGraph.api("me").getAsJsonObject();
	        String email = profile.get("email").getAsString();
	        String username = "";
	        
	        User user = User.find("byEmail", email).first();
	        if (user == null) {
	        	String newName = profile.get("last_name").getAsString() + profile.get("first_name").getAsString();
	        	user = new User();
	        	user.email = email;
	        	user.password = email;
	        	user.name = newName;
	        	user.isAdmin = false;
	        	user.isApproved = true;
	        	user.isManager = false;
	        	
	        	username = newName;
	        	user.save();
	        	
	        	Session.current().put("email", email);
		        Session.current().put("user", user);
		        Session.current().put("userId", user.id);
		        Session.current().put("name", user.name);
		        editUser(user.id);
		        
	        }
	        else{
	        	username = user.name;
	        	
	        	Session.current().put("email", email);
		        Session.current().put("user", user);
		        Session.current().put("userId", user.id);
		        Session.current().put("name", user.name);
		        redirect("/");
	        }
	        
	        
	        
	        
	        
	    } catch (FbGraphException fbge) {
	        if (fbge.getType().equals("OAuthException")) {
	            flash.error("Facebook Authentication Failure", "");
	        }
	        redirect("/");
	    }
	    //redirect("/");
	   
	    
	}
	
	public static void login(String error) {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);
        
        String randomID = Codec.UUID();

        render(error, randomID);
	}
	
	public static void register(String error) {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);
        
        String randomID = Codec.UUID();
        System.out.println(randomID);

        render(error, randomID);
	}
	
	// For users registration by Kyo
	public static void addUser(){
		if (session.get("userId") == null) {
			User user = new User();
			user = user.save();
			redirect("/user/" + user.getId() + "/edituser");
		}
	}

	public static void editUser(Long id) {
		User user = User.findById(id);
		if (user == null) {
			user = new User();
			user = user.save();                                                                                     
		}
		render(user, user.getId(), session.get("userId"));
	}

	public static void doUpload(Long id, String username, String password,
			String gender, String email, String address, String name,
			String phone, String country,String zipcode,String company,
			String title, String last_name, String nationality, String visa_no, 
			String birth_year, String birth_month, String birth_day, String phone_code, 
			String ext_no, String city, Boolean isApproved) {
		User user = User.findById(id);
		user.name = name;
		user.username = username;
		user.password = password;
		user.gender = gender;
		user.email = email;
		user.address = address;
		user.phone = phone;
		user.country = country;
		user.zipcode = zipcode;
		user.title = title;
		user.company = company;
		user.last_name = last_name;
        user.nationality = nationality;
        user.visa_no = visa_no;
        user.birth_year = birth_year;
        user.birth_month = birth_month;
        user.birth_day = birth_day;
        user.phone_code = phone_code;
        //user.ext_no = ext_no;
        user.city = city;
		if (isApproved == null || false) {
			user.isApproved = true;
		}

		user.save();
		if (Session.current().get("userId") == null) {
			long userId = (Long) user.getId();
			Session.current().put("userId", userId);
			Session.current().put("user", user);
			Session.current().put("name", user.name);
		}
		showUser(id);
		// redirect("/");
	}
	
	public static void registerForm(String name, String password, String email, String cap, String randomID) {
		User user = User.find("byEmail", email).first();
		System.out.println("randomID:" + randomID);
		System.out.println("cap:" + cap);
		validation.equals(
				cap, Cache.get(randomID)
	    		).message("驗證碼錯誤，請重新輸入！");
	    	System.out.println(Cache.get(randomID));
	    		if(validation.hasErrors()) {
	    			redirect("/application/register?error=1");
	    			}
		
		if(user == null){
			System.out.println("no user");
			User newUser = new User();
			newUser.name = name;
			newUser.email = email;
			newUser.password = password;
			newUser.isAdmin = false;
			newUser.isManager = false;
			newUser.isApproved = true;
			newUser.save();
			
			user = newUser;
		}
		else{
			System.out.println("user!!");
			
		}

		
		
		long userId = (Long) user.getId();
		Session.current().put("userId", userId);
		Session.current().put("user", user);
		Session.current().put("email", user.email);
		Session.current().put("name", user.name);
		editUser(user.id);
	}
	
	public static void loginForm(String password, String email, String cap, String randomID) {

        System.out.println("Email:"+email);
		User user = User.find("byEmail", email).first();
		validation.equals(
				cap, Cache.get(randomID)
	    		).message("驗證碼錯誤，請重新輸入！");
	    	System.out.println(Cache.get(randomID));
	    		if(validation.hasErrors()) {
	    			redirect("/application/register?error=1");
	    			}
		
		if(user != null){
			
			long userId = (Long) user.getId();
			Session.current().put("userId", userId);
			Session.current().put("user", user);
			Session.current().put("email", user.email);
			Session.current().put("name", user.name);
			showUser(Long.valueOf(user.getId().toString()));
		}
		System.out.println(flash.get("url"));
		redirect("/user/login?error=1");
	}
	
	public static void loginFormMobile(String password, String email, String lang) {
		User user = User.find("byEmail", email).first();
		
		if(user != null){
			
			long userId = (Long) user.getId();
			Session.current().put("userId", userId);
			Session.current().put("user", user);
			Session.current().put("email", user.email);
			Session.current().put("name", user.name);
		}
		String language = lang;
		System.out.println(flash.get("url"));
		System.out.println("to mobileHome: " + language);
		mobileHome(language);
	}
	
	public static void profile() {
		String email = Session.current().get("email");
		User user = User.find("byEmail", email).first();
		showUser(Long.valueOf(user.getId().toString()));
	}

	public static void showUser(Long id) {
		User user = User.findById(id);
		render(user, user.getId(), session.get("userId"));
	}
	
	public static void logout() {
		Session.current().remove("email");
		Session.current().remove("user");
		Session.current().remove("userId");
		Session.current().remove("name");
		redirect("/");
	}
	
	public static void imageHandler(Upload imageName)
	{
		if (imageName != null) {
			String dateString;
			Date date = new Date();
			dateString = String.valueOf(date.getTime());

	        File file = imageName.asFile();
	        String newFileName = dateString + "." + FilenameUtils.getExtension(file.getPath());
	        file.renameTo(new File("public/upload/information", newFileName));
	        
	        System.out.println("Application.imageHandler(): imageName = " + imageName + "; file = " + file);
	        
	        String html = "/public/upload/information/" + newFileName;
	       
	        
	        renderHtml(html);
	        
    	} else {
    		
    	}

	}

    public static void projectDetail(long id)
    {
        System.out.println("ID"+id);
        Project project = Project.findById(id);
        System.out.println(project.content);
        render(project);

    }
	
	
}