package com.ccc.chestersprinkles.utility;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.chestersprinkles.model.ManagerAssociation;
import com.ccc.chestersprinkles.model.Pirate;
import com.ccc.chestersprinkles.model.PiratePointsHistory;
import com.ccc.chestersprinkles.service.ManagerAssociationService;
import com.ccc.chestersprinkles.service.PiratePointsHistoryService;
import com.ccc.chestersprinkles.service.PirateService;

import me.ramswaroop.jbot.core.slack.models.Event;

@Component
public class ManagerAssociationCommand extends Command {
	@Autowired
	private PirateService pirateService;

	@Autowired
	private ManagerAssociationService managerAssociationService;

	@Autowired
	private PiratePointsHistoryService piratePointsHistoryService;

	public String getMyDirectReportsCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			if (StringUtils.isEmpty(pirate.getPirateName())) {
				return "You haven't been setup as a pirate in the #piratescove channel yet.";
			}

			List<ManagerAssociation> associations = managerAssociationService
					.getManagerAssociationByManagerPirateId(pirate.getPirateId());

			if (associations.isEmpty()) {
				return "You do not have any direct reports at this time.";
			}

			StringBuilder output = new StringBuilder();

			output.append("*Here is a list of all the events your direct reports have been to:*\n");
			for (ManagerAssociation association : associations) {
				if (association.getDirectReportPirateId() == 0) {
					output.append("*" + association.getDirectReportName()
							+ "* has not been set up as a pirate in the #piratescove channel.\n\n");
				} else {
					List<PiratePointsHistory> pointsHistory = piratePointsHistoryService
							.getHistoryByPirateId(association.getDirectReportPirateId());

					if (pointsHistory.isEmpty()) {
						output.append("*" + association.getDirectReportName()
								+ "* has not been to any events that award Pirate Points.\n\n");
					} else {
						output.append("*" + association.getDirectReportName() + ":*\n");
						for (PiratePointsHistory pointsItem : pointsHistory) {
							output.append("*Date:* " + pointsItem.getDateOfEvent() + " --- *Event:* "
									+ pointsItem.getEvent() + "\n");
						}
						output.append("\n");
					}
				}
			}

			return output.toString();
		}

		return null;
	}

	public String getMyManagerCommandResponse(Event event) {
		if (validateInput(event)) {
			Pirate pirate = pirateService.getPirateBySlackId(event.getUserId());

			if (StringUtils.isEmpty(pirate.getPirateName())) {
				return "You haven't been setup as a pirate in the #piratescove channel yet.";
			}

			ManagerAssociation association = managerAssociationService
					.getManagerAssociationByDirectReportPirateId(pirate.getPirateId());

			StringBuilder output = new StringBuilder();

			output.append("*Here is a list of all the events your manager has been to:*\n");
			if (association.getManagerPirateId() == 0) {
				output.append("*" + association.getManagerName()
						+ "* has not been set up as a pirate in the #piratescove channel.\n\n");
			} else {
				List<PiratePointsHistory> pointsHistory = piratePointsHistoryService
						.getHistoryByPirateId(association.getManagerPirateId());

				if (pointsHistory.isEmpty()) {
					output.append("*" + association.getManagerName()
							+ "* has not been to any events that award Pirate Points.\n\n");
				} else {
					output.append("*" + association.getManagerName() + ":*\n");
					for (PiratePointsHistory pointsItem : pointsHistory) {
						output.append("*Date:* " + pointsItem.getDateOfEvent() + " --- *Event:* "
								+ pointsItem.getEvent() + "\n");
					}
					output.append("\n");
				}
			}

			return output.toString();
		}

		return null;
	}
	
	public String getWhoManagerCommandResponse(Event event) {
		if (validateInput(event)) {
			String[] inputArray = event.getText().split(" ");
			
			if (inputArray.length != 3) {
				return "You need to enter a first and last name separated by a space.";
			}
			
			String name = inputArray[1] + " " + inputArray[2];
			
			ManagerAssociation association = managerAssociationService.getManagerAssociationByDirectReportName(name);			
			
			if (StringUtils.isEmpty(association.getManagerName())) {
				return "*" + name + "* is not an employee here. Check your spelling and try again!";
			}
			
			return "*" + name + "*'s manager is *" + association.getManagerName() + "*";
		}
			
		return null;
	}
	
	public String getWhoDirectReportsCommandResponse(Event event) {
		if (validateInput(event)) {
			String[] inputArray = event.getText().split(" ");
			
			if (inputArray.length != 3) {
				return "You need to enter a first and last name separated by a space.";
			}
			
			String name = inputArray[1] + " " + inputArray[2];
			
			List<ManagerAssociation> associations = managerAssociationService.getManagerAssociationByManagerName(name);			
			
			if (associations.isEmpty()) {
				return "*" + name + "* does not have any direct reports at this moment. Be sure to check your spelling if this is inaccurate.";
			}
			
			StringBuilder output = new StringBuilder();
			int counter = 1;
			
			output.append("*" + name + "* has the following direct reports:\n");
			
			for (ManagerAssociation association : associations) {
				output.append(counter++ + ". ").append(association.getDirectReportName()).append("\n");
			}
			
			return output.toString();
		}
			
		return null;
	}
}
