package com.github.prxy_rgb.city_break_manager.Service;

import com.github.prxy_rgb.city_break_manager.Entity.City;
import com.github.prxy_rgb.city_break_manager.Entity.Trip;
import com.github.prxy_rgb.city_break_manager.Exception.ResourceNotFoundException;
import com.github.prxy_rgb.city_break_manager.Repository.CityRepository;
import com.github.prxy_rgb.city_break_manager.Repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {
    private final CityRepository cityRepository;
    private final TripRepository tripRepository;

    public AppService(CityRepository cityRepository, TripRepository tripRepository) {
        this.cityRepository = cityRepository;
        this.tripRepository = tripRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + id));
    }

    public City createCity(City city) {
        return cityRepository.save(city);
    }

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public City updateCity(Long id, City city) {
        return cityRepository.findById(id).map(existingCity -> {
            existingCity.setName(city.getName());
            existingCity.setCountry(city.getCountry());
            existingCity.setDetails(city.getDetails());
            return cityRepository.save(existingCity);
        }).orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));
    }

    public Trip updateTrip(Long id, Trip trip) {
        return tripRepository.findById(id).map(existingTrip -> {
            existingTrip.setCity(trip.getCity());
            existingTrip.setStartDate(trip.getStartDate());
            existingTrip.setEndDate(trip.getEndDate());
            return tripRepository.save(existingTrip);
        }).orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + id));
    }

    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) throw new ResourceNotFoundException("City not found with id: " + id);
        cityRepository.deleteById(id);
    }

    public void deleteTrip(Long id) {
        if (!tripRepository.existsById(id)) throw new ResourceNotFoundException("Trip not found with id: " + id);
        tripRepository.deleteById(id);
    }
}
