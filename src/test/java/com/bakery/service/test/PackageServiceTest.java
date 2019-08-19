package com.bakery.service.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bakery.model.PackageResult;
import com.bakery.service.PackageService;

import junit.framework.Assert;
import junit.framework.TestCase;

@Test
public class PackageServiceTest extends TestCase{
	
	private PackageService packageService;
	
	@BeforeTest
	public void initialize() {
		packageService = new PackageService();  
	} 

	@DataProvider(name="bakeryData")
	public static Object[][] bakeryData() {
		return new Object[][] { {"VS5", new ArrayList<Integer>(Arrays.asList(new Integer(3), new Integer(5)))}, 
			{"MB11", new ArrayList<Integer>(Arrays.asList(new Integer(2), new Integer(5), new Integer(8)))},
			{"CF", new ArrayList<Integer>(Arrays.asList(new Integer(3), new Integer(5), new Integer(9)))}};
	}
	@Test(dataProvider = "bakeryData")
	public void testGetBakeryData(String inputCode, List<Integer> expectedData) {
			try {
				List<Integer> actualData = packageService.getBakeryPackOptions(inputCode);
				Assert.assertEquals(expectedData, actualData);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	@DataProvider(name="calcPackData")
	public static Object[][] calcPackData() {
		return new Object[][] { {new ArrayList<Integer>(Arrays.asList(new Integer(5), new Integer(3))), 10, new PackageResult(2, Arrays.asList(new Integer(5), new Integer(5)), 0.0)}, 
			{new ArrayList<Integer>(Arrays.asList(new Integer(8), new Integer(5), new Integer(2))), 14, new PackageResult(4, Arrays.asList(new Integer(8), new Integer(2), new Integer(2),  new Integer(2)), 0.0)},
			{new ArrayList<Integer>(Arrays.asList(new Integer(9), new Integer(5), new Integer(3))), 13, new PackageResult(3, Arrays.asList(new Integer(5), new Integer(5), new Integer(3)), 0.0)}};
	}
	@Test(dataProvider = "calcPackData")
	public void testCalculatePackage(List<Integer> inputPacks, int inputQty, PackageResult expectedResult) {
		PackageResult actual = packageService.calculateMinPacks(inputPacks, inputQty);
		Assert.assertEquals(expectedResult.getPackQty(), actual.getPackQty());
		Assert.assertEquals(expectedResult.getTotalPrice(), actual.getTotalPrice());
		Assert.assertEquals(expectedResult.getPacks(), actual.getPacks());
	}
	
	@DataProvider(name="calcTotalPrice")
	public static Object[][] calcTotalPrice() {
		return new Object[][] { {"VS5", new PackageResult(2, Arrays.asList(new Integer(5), new Integer(5)), 0.0), new PackageResult(2, Arrays.asList(new Integer(5), new Integer(5)), 17.98)}, 
			{"MB11", new PackageResult(4, Arrays.asList(new Integer(8), new Integer(2), new Integer(2),  new Integer(2)), 0.0), new PackageResult(4, Arrays.asList(new Integer(8), new Integer(2), new Integer(2),  new Integer(2)), 54.8)},
			{"CF", new PackageResult(3, Arrays.asList(new Integer(5), new Integer(5), new Integer(3)), 0.0), new PackageResult(3, Arrays.asList(new Integer(5), new Integer(5), new Integer(3)), 25.849999999999998)}};
	}
	
	@Test(dataProvider = "calcTotalPrice")
	public void testCalculateTotalPrice(String inputCode, PackageResult inputResult, PackageResult expectedResult) throws FileNotFoundException, IOException, ParseException {
	
		PackageResult actual = packageService.calculatePrice(inputCode, inputResult);
		Assert.assertEquals(expectedResult.getPackQty(), actual.getPackQty());
		Assert.assertEquals(expectedResult.getTotalPrice(), actual.getTotalPrice());
		Assert.assertEquals(expectedResult.getPacks(), actual.getPacks());
	}
	
	@DataProvider(name="exceptionData")
	public static Object[][] exceptionData() {
		return new Object[][] { {"VS5", 1}, 
			{"MB11", 3},
			{"CF", 2}};
	}
	
	@Test(dataProvider="exceptionData", expectedExceptions = {Exception.class}, expectedExceptionsMessageRegExp = "Cannot create packs for .*")
	public void testCalculatePackageException(String inputCode, int inputQty) throws Exception {
		packageService.processOrder(inputCode, inputQty);
	}
}
