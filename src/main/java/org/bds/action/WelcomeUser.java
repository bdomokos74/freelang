package org.bds.action;

import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.LoggerFactory;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Action;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Apr 14, 2010
 * Time: 1:08:23 AM
 */

public class WelcomeUser extends ActionSupport {
    private String message;

    public WelcomeUser() {
    }

    private String userName;

    public String execute() {
        LoggerFactory.getLogger(getClass()).error("Execute called");
        message = "WelcomeUser " + userName;
        return SUCCESS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}