package com.itson.cm.mini.minidosapi.models;

public class Host {
	
	private String surname; 
	private double lat;
	private double lon;
	
	public Host(String surname, double lat, double lon) {
		super();
		this.surname = surname;
		this.lat = lat;
		this.lon = lon;
	} 
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public double getLan() {
		return lat;
	}
	public void setLan(double lan) {
		this.lat = lan;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}

	
	
	
}
