package com.cb.springdata.sample.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cb.springdata.sample.entities.Building;
import com.cb.springdata.sample.service.BuildingService;

@RestController
@RequestMapping("/api/building")
public class BuildingServiceController {

	@Autowired
	private BuildingService buildingService;

	@RequestMapping(value = "/building", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Building findById(@RequestParam("building") String building) {

		return buildingService.findById(building);
	}
	
	@RequestMapping(value = "/company", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Building> findByCompany(@RequestParam("company") String company) {

		return buildingService.findByCompanyId(company);
	}

	@RequestMapping(value = "/name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Building> findByCompanyAndName(@RequestParam("name") String name,
			@RequestParam("company") String company) {

		return buildingService.findByCompanyIdAndNameLike(company, name, 0);
	}
	
	@RequestMapping(value = "/phone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Building> findByPhoneNumber(@RequestParam("phone") String phoneNumber) {

		return buildingService.findByPhoneNumber(phoneNumber);
	}

	@RequestMapping(value = "/area", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Building findByCompanyAndArea(@RequestParam("area") String area, @RequestParam("company") String company) {

		return buildingService.findByCompanyAndAreaId(company, area);
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Long countBuildings(@RequestParam("company") String company) {

		return buildingService.countBuildings(company);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Building countBuildings(@RequestBody Building building) {

		return buildingService.save(building);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Building> searchByName(@RequestParam("name") String name) {

		return buildingService.searchByName(name);
	}
}
