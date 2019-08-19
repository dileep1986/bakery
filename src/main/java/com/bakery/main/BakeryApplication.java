package com.bakery.main;

import java.util.Scanner;

import com.bakery.model.PackageResult;
import com.bakery.service.PackageService;

/**
 * Hello world!
 *
 */
public class BakeryApplication 
{
    public static void main( String[] args ) throws Exception
    {
        
        Scanner input = new Scanner(System.in);
        System.out.println("Please select any product code from [VS5, MB11, CF] : ");
        String productCode = input.nextLine();
        System.out.println("Please enter the quantity :");
        int quantity = input.nextInt();
        input.close();
        PackageService service = new PackageService();
        PackageResult finalResult = service.processOrder(productCode, quantity);
        System.out.print("Package Quantity : "+ finalResult.packQty +" \n");
        System.out.print("Package Set : "+ finalResult.packs +" \n");
        System.out.print("Package Total Price : "+ finalResult.totalPrice +" \n");
        
    }
}
