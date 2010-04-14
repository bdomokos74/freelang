package org.bds.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Apr 14, 2010
 * Time: 1:08:23 AM
 */

@Action(value="/welcome", results={
        @Result(name="success", location="first.jsp")
})
public class HelloWorldAction extends ActionSupport {

    public static final String MESSAGE = "Struts is up and running ...";

    public String execute() throws Exception {
        setMessage(MESSAGE);
        return SUCCESS;
    }

    private String message;

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}