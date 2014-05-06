package org.app.core.service;

/**
 * Created by ngandriau on 5/3/14.
 */
public class PrinterService
{
    public void print(String msg){
        System.out.println("" + msg!=null?msg:"No Message to print");
    }
}
