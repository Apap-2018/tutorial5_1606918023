package com.apap.tutorial5.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

/*
 * FlightController
 */
@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method=RequestMethod.GET)
	private String add(@PathVariable(value="licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		List<FlightModel> pilotF = new ArrayList<FlightModel>();
		pilotF.add(flight);
		pilot.setPilotFlight(pilotF);
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", pilot);
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method=RequestMethod.POST, params= {"submitFlight"})
	private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for (FlightModel flight: pilot.getPilotFlight()) {
			flight.setPilot(archive);
			flightService.addFlight(flight);
		}
		return "add";
	}
	
	@RequestMapping(value="/flight/add", method=RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping(value="/flight/delete", method=RequestMethod.POST)
	private String delete(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlight(flight);
		}
		return "delete";
	}
	
	@RequestMapping(value="/flight/update/{flightNumber}", method=RequestMethod.GET)
	private String update(@PathVariable(value="flightNumber") String flightNumber, Model model) {
		FlightModel archive = flightService.getPilotDetailByFlightNumber(flightNumber);
		model.addAttribute("flight", archive);
		return "updateFlight";
	}
	
	@RequestMapping(value="/flight/update", method=RequestMethod.POST)
	private String updateFlight(@ModelAttribute FlightModel flight) {
		flightService.updateFlight(flight, flight.getFlightNumber());
		return "update";
	}
	
	@RequestMapping(value="/flight/view")
	private String viewAllPilot(@RequestParam(value="flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.getPilotDetailByFlightNumber(flightNumber);
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", flight.getPilot());
		return "viewall-flight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method=RequestMethod.POST, params={"addRow"})
	public String addRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
	    if (pilot.getPilotFlight()==null) {
	    	pilot.setPilotFlight(new ArrayList<FlightModel>());
	    }
	    pilot.getPilotFlight().add(new FlightModel());
	    model.addAttribute("pilot", pilot);
	    return "addFlight";
	}
	
}
