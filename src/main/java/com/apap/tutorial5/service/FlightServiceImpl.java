package com.apap.tutorial5.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.FlightDB;

/*
 * PilotServiceImpl
 */
@Service
@Transactional
public class FlightServiceImpl implements FlightService {
	@Autowired
	private FlightDB flightDb;
	
	@Override
	public void addFlight(FlightModel flight) {
		flightDb.save(flight);
	}

	@Override
	public void deleteFlight(FlightModel flight) {
		flightDb.delete(flight);
	}

	@Override
	public void updateFlight(FlightModel flight, String flightNumber) {
		FlightModel archive = flightDb.findByFlightNumber(flightNumber);
		archive.setOrigin(flight.getOrigin());
		archive.setDestination(flight.getDestination());
		archive.setTime(flight.getTime());
		flightDb.save(archive);
	}

	@Override
	public FlightModel getFlightById(Long id) {
		return flightDb.getOne(id);
	}

	@Override
	public FlightModel getPilotDetailByFlightNumber(String flightNumber) {
		return flightDb.findByFlightNumber(flightNumber);
	}

}
