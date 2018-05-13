package com.ccc.chestersprinkles.carnivaltours.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ct_interaction")
public class CTInteraction {
	
	@Id
	private String interactionId;
	
	@Column(name="location_id")
	private String locationId;
	
	@Column(name="item_id")
	private String itemId;
	
	@Column(name="interaction")
	private String interaction;
	
	@Column(name="stan_act")
	private String standardAction;
	
	@Column(name="found_hat_itm_act")
	private String foundHatItemAction;
	
	@Column(name="found_cane_itm_act")
	private String foundCaneItemAction;
	
	@Column(name="found_wax_itm_act")
	private String foundWaxItemAction;
	
	public CTInteraction() {
		
	}

	public String getInteractionId() {
		return interactionId;
	}
	
	public String getInteraction() {
		return interaction;
	}
	
	public String getLocationId() {
		return locationId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getStandardAction() {
		return standardAction;
	}

	public String getFoundHatItemAction() {
		return foundHatItemAction;
	}

	public String getFoundCaneItemAction() {
		return foundCaneItemAction;
	}

	public String getFoundWaxItemAction() {
		return foundWaxItemAction;
	}

	public void setFoundCaneItemAction(String foundCaneItemAction) {
		this.foundCaneItemAction = foundCaneItemAction;
	}

	public void setFoundWaxItemAction(String foundWaxItemAction) {
		this.foundWaxItemAction = foundWaxItemAction;
	}

	public void setStandardAction(String standardAction) {
		this.standardAction = standardAction;
	}

	public void setFoundHatItemAction(String foundHatItemAction) {
		this.foundHatItemAction = foundHatItemAction;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setInteractionId(String interactionId) {
		this.interactionId = interactionId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}
}
