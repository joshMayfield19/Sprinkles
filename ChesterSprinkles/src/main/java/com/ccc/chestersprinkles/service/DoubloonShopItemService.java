package com.ccc.chestersprinkles.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.DoubloonShopItemDao;
import com.ccc.chestersprinkles.model.DoubloonShopItem;

@Transactional
@Service
public class DoubloonShopItemService {
	@Autowired
	private DoubloonShopItemDao doubloonShopItemDao;
	
	public List<DoubloonShopItem> getAllItems() {
		return doubloonShopItemDao.getAllItems();
	}
	
	public DoubloonShopItem getItemByCommand(String command) {
		return doubloonShopItemDao.getItemByCommand(command);
	}
	
	public void updateQuantity(int itemId, int quantity) {
		doubloonShopItemDao.updateQuantity(itemId, quantity);
	}
}
