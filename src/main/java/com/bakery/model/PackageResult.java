/**
 * 
 */
package com.bakery.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TOSHIBA
 *
 */
public class PackageResult {

	public static final int UNAVAILABLE = -1;
	
	public int packQty;
	public List<Integer> packs = new ArrayList<Integer>(); 
	public double totalPrice;
	
	
	public PackageResult(int packQty, List<Integer> packs, double totalPrice) {
		super();
		this.packQty = packQty;
		this.packs = packs;
		this.totalPrice = totalPrice;
	}


	public double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public int getPackQty() {
		return packQty;
	}


	public void setPackQty(int packQty) {
		this.packQty = packQty;
	}

	public List<Integer> getPacks() {
		return packs;
	}


	public void setPacks(List<Integer> packs) {
		this.packs = packs;
	}

	public void addPack(int p) {
		this.packs.add(p);
	}
	
	public boolean isPackUnavailable() {
		return this.packQty == UNAVAILABLE;
	}
	
	public void setPackQtyUnavailable() {
		this.packQty = UNAVAILABLE;
	}
	
	public PackageResult() {
		
	}
	
	public void updateFromSub(PackageResult result, int p) {
		
		if(!result.getPacks().isEmpty()) {
			
			this.packQty = result.packQty + 1;
			this.packs = result.packs;
			this.addPack(p);
		}
		
	}
}
