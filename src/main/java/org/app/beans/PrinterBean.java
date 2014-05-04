package org.app.beans;

/**
 * Created by ngandriau on 5/3/14.
 */
public class PrinterBean
{
    public void print(String msg){
        System.out.println("" + msg!=null?msg:"No Message to print");
    }
}
