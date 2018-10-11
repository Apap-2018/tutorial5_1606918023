package com.apap.tutorial5.service;

import java.util.Date;

import com.apap.tutorial5.model.FlightModel;

/*
 * FlightService
 */
public interface FlightService {
	void addFlight(FlightModel flight);
	void deleteFlight(FlightModel flight);
	void updateFlight(FlightModel flight, String flightNumber);
	FlightModel getFlightById(Long id);
	FlightModel getPilotDetailByFlightNumber(String flightNumber);
}