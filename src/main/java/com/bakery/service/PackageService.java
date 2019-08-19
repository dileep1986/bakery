/**
 * 
 */
package com.bakery.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bakery.model.PackageResult;

/**
 * @author TOSHIBA
 *
 */
public class PackageService {

	private HashMap<String, String> getBakeryData(String code) throws FileNotFoundException, IOException, ParseException {
		
		JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(
                "src\\main\\resources\\bakery.json"));
        JSONObject jsonObject = (JSONObject) obj;
        
        HashMap<String, String> name =  (HashMap<String, String>)jsonObject.get(code); 
        
        return name;
	}
	
	public List<Integer> getBakeryPackOptions(String code) throws FileNotFoundException, IOException, ParseException {
		
		Map item = getBakeryData(code);
		Map packs = (Map) item.get("packs");
		List<String> keys = new ArrayList<String>(packs.keySet());
		//packs.forEach(x);
		//TreeSet<Stri>
		return item.size() > 0 ? (keys.stream().map(x-> { return Integer.valueOf(x); }).collect(Collectors.toList())) : null;
	}
	
	public PackageResult calculateMinPacks(List<Integer> packs, int quantity) {
		
		PackageResult result = new PackageResult();
		PackageResult minResult = new PackageResult();
		int minP = 0;
		
		for (Iterator iterator = packs.iterator(); iterator.hasNext();) {
			int p = (Integer)iterator.next();
			if(quantity == p) {
				 result.setPackQty(1);
				 result.addPack(p);
				 break;
			}else if (quantity > p) {
				
				PackageResult subResult = calculateMinPacks(packs, quantity-p);
				if(!subResult.isPackUnavailable()) {
					if(minResult != null || minResult.getPackQty() > subResult.getPackQty()) {
						minResult = subResult;
						minP = p;
					}
				}else {
					result.setPackQtyUnavailable();
				}
			} else {
				result.setPackQtyUnavailable();
			}
			
		}
		
		result.updateFromSub(minResult, minP);
		return result;
		
	}
	
	public 	PackageResult calculatePrice(String code, PackageResult result) throws FileNotFoundException, IOException, ParseException {
		
		Map item = getBakeryData(code);
		Map packs = (Map) item.get("packs");
		
		
		List<Integer> keys = new ArrayList<Integer>(result.getPacks());
		int qty = 0;
		double totalPrice = 0;
		
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String p = String.valueOf(iterator.next());
			qty = qty + Integer.valueOf(p);
			totalPrice = totalPrice + Double.valueOf(packs.get(p).toString());
			
		}
		
		result.setTotalPrice(totalPrice);
		
		return result;
		
	}
	
	public PackageResult processOrder(String code, int quantity) throws Exception {
		List<Integer> numbers = getBakeryPackOptions(code);
        Collections.sort(numbers,Collections.reverseOrder());
        PackageResult result = calculateMinPacks(numbers, quantity);
        PackageResult finalResult = calculatePrice(code, result);
        if(finalResult.isPackUnavailable()) {
        	throw new Exception("Cannot create packs for "+quantity+"");
        }
        return finalResult;
	}
}
