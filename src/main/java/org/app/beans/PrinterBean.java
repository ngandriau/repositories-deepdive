package org.app.beans;

/**
 * Created by ngandriau on 5/3/14.
 */
public class PrinterBean
{
    public void sayHello(String msg){
        System.out.println("Hello " + msg!=null?msg:"world");
    }
}
