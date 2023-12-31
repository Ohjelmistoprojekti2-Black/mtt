package com.op2.op2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locationId;
    @Size(max = 5, message = "Zipcode cannot exceed 5 characters")
    @NotBlank(message = "Zipcode is required")
    private String zipcode;
    @Size(max = 40, message = "City cannot exceed 40 characters")
    @NotBlank(message = "City is required")
    private String city;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<Event> events;

    public Location() {

    }

    public Location(String zipcode, String city) {
        super();
        this.zipcode = zipcode;
        this.city = city;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Location [locationId = " + locationId + " zipcode =" + zipcode + " city =" + city + "]";
    }

}
