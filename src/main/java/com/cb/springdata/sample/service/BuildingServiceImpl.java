package com.cb.springdata.sample.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cb.springdata.sample.entities.Building;
import com.cb.springdata.sample.repositories.BuildingRepository;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.queries.DisjunctionQuery;
import com.couchbase.client.java.search.result.SearchQueryResult;
import com.couchbase.client.java.search.result.SearchQueryRow;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	@Override
	public List<Building> findByCompanyId(String companyId) {
		return buildingRepository.findByCompanyId(companyId);
	}

	public List<Building> findByCompanyIdAndNameLike(String companyId, String name, int page) {
		return buildingRepository.findByCompanyIdAndNameLikeOrderByName(companyId, name, new PageRequest(page, 20))
				.getContent();
	}

	@Override
	public Building findByCompanyAndAreaId(String companyId, String areaId) {
		return buildingRepository.findByCompanyAndAreaId(companyId, areaId);
	}

	@Override
	public List<Building> findByPhoneNumber(String telephoneNumber) {
		return buildingRepository.findByPhoneNumber(telephoneNumber);
	}

	@Override
	public Building findById(String buildingId) {
		return buildingRepository.findOne(buildingId);
	}

	@Override
	public Building save(@Valid Building building) {
		return buildingRepository.save(building);
	}

	@Override
	public Long countBuildings(String companyId) {
		return buildingRepository.countBuildings(companyId);
	}

	@Override
	public List<Building> searchByName(String name) {
		List<Building> buildings = new ArrayList<Building>();
		DisjunctionQuery ftsQuery = SearchQuery.disjuncts(SearchQuery.wildcard(name).field("companyId"),
				SearchQuery.wildcard(name).field("name"));

		SearchQueryResult result = buildingRepository.getCouchbaseOperations().getCouchbaseBucket()
				.query(new SearchQuery("nameSearch", ftsQuery).limit(10).highlight());

		if (result != null && result.errors().isEmpty()) {
			Iterator<SearchQueryRow> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				SearchQueryRow row = resultIterator.next();
				buildings.add(buildingRepository.findOne(row.id()));
			}
		}
		return buildings;
	}

}
