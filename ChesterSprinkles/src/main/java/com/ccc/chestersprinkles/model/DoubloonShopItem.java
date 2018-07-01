package com.ccc.chestersprinkles.model;

public class DoubloonShopItem {
	private int itemId;
	private String itemDescription;
	private String itemCommand;
	private int itemQuantity;
	private int itemPrice;
	
	public DoubloonShopItem() {
		
	}

	public int getItemId() {
		return itemId;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public String getItemCommand() {
		return itemCommand;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public void setItemCommand(String itemCommand) {
		this.itemCommand = itemCommand;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
}
