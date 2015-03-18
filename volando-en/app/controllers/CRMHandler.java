package controllers;

import models.CRM_record;
import models.User;

/**
 * Created by apple on 2014/8/9.
 */
public class CRMHandler {
    public static void insertCRM(String siteName,String email,String phone)
    {
        CRM_record crm = new CRM_record();
        crm.email = email;
        crm.phone= phone;
        crm.site = siteName;
        crm.save();
    }
}
