package com.itson.cm.mini.minidosapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itson.cm.mini.minidosapi.models.Host;

@RestController
public class ApiController {
	
	private static final String template = "Hello, %s!";
	
	private final AtomicLong counter = new AtomicLong();
	
	private List<Host> listOfHost = new ArrayList<Host>();
	
	private Host currentHost = null;
	
	public double distanceInKms(double lat1, double lon1, double lat2, double lon2) {
		if((lat1 == lat2) && (lon1 == lon2)) {
			return 0.0;
		}else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			
			return dist;
		}
	}
	
	
	
	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
				String.format(template, name));
	}
	
	@RequestMapping(value="/testin", method = RequestMethod.GET)
	public HashMap<String, Object> testin() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("key1", "some text");
		map.put("foo", "bar");
		return map;
	}
	
	@GetMapping("/api/currenthost")
	@ResponseBody
	public HashMap<String, Object> currentHost(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("responseCode", "404");
		map.put("responseText", "error ");
		if(currentHost == null) {
			map.put("responseCode", "404");
			map.put("responseText", "host not found");
		}else {
			map.put("responseCode", "200");
			map.put("hostName: ", currentHost.getSurname());
			map.put("hostLat", currentHost.getLan());
			map.put("hostLon", currentHost.getLon());
		}
		return map;

	}
	
	@PostMapping("/api/checkinUser")
	@ResponseBody
	public HashMap<String, Object> checkIn(@RequestParam(name = "isHost", defaultValue = "false") boolean isHost,
			@RequestParam(name="surname", defaultValue = "generic") String surname,
			@RequestParam(required = true)double lat,
			@RequestParam(required= true) double lon) {
		
		String responseCode = "404";
		String responseText = "there was an error";
		
		if(isHost) {
			Host host = new Host(surname, lat, lon);
			this.currentHost = host;
			responseCode = "200";
			responseText = "a host has been registered";
			//listOfHost.add(host);
		}else {
			if(currentHost == null) {
				responseCode = "300";
				responseText = "There's no host available";
			}else {
				double dist = distanceInKms(lat, lon, currentHost.getLan(), currentHost.getLon());
				if (dist < 2) { 
					responseCode = "200";
					responseText = "good to go";
				}else {
					responseCode = "305";
					responseText = "too far from host";
				}
			}
		}
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("responseCode", responseCode);
		map.put("responseText", responseText);
		
		return map;
		
		}
	
	
}
