package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Customer;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)

public class CustomerManagement extends Controller {

	public static void index() {
		
		List<Customer> customer = Customer.findAll();
		renderJSON(customer);
    }
	public static void addGeneralCustomer(String email, String id, String name, String lang) throws FileNotFoundException
	{
		//final Customer cumtomer = new Customer(email,id,name);
   
		//cumtomer.save();
		//redirect("/news/" + lang + "/" + id);
	}
	public static void addcustomer() throws FileNotFoundException 
	{
        Customer customer = new Customer();
        customer.save();
        redirect("/CustomerManagement" + "/" + customer.getId() + "/editcustomer");
    }
	public static void editcustomer(long id) throws FileNotFoundException 
	{
		final Customer cus = Customer.findById(id);
        render(cus, cus.getId());
    }
	
	public static void updatecustomerbyID(long id,String email,String name,String address,String phone,
			int gender,String country,String birthday) throws ParseException 
	{
		final Customer cus = Customer.findById(id);
		cus.email = email;
		cus.address = address;
		cus.name = name;
		cus.phone = phone;
		cus.country = country;
		cus.gender = gender;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date d_birthday = sdf.parse(birthday);
		cus.birthday = d_birthday;
		cus.save();
        index();
	}
	
	public static void updatecustomer(Customer cus) 
	{
        cus.save();
        index();
	}
	
	public static void records()
	{
		List<Customer> cus = Customer.findAll();
		render(cus);
	}
	
	public static void login()
	{
		render();
	}
}
