package com.cb.springdata.sample.repositories;

import com.cb.springdata.sample.entities.Building;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * This annotation makes sure that the bucket associated with the current
 * repository will have a N1QL primary index. Not recomended for production
 **/
@N1qlPrimaryIndexed

/**
 * This annotation lets you define the name of the design document and View name
 * as well as a custom map and reduce function.
 **/
@ViewIndexed(designDoc = "building")

/**
 * We are extending CouchbasePagingAndSortingRepository, which allows you to
 * paginate your queries by simply adding a Pageable param at the end of your
 * method definition. As it is essentially a repository, you can leverage all
 * Spring Data keywords like FindBy, Between, IsGreaterThan, Like, Exists, etc.
 * So, you can start using Couchbase with almost no previous knowledge and still
 * be very productive
 **/
public interface BuildingRepository extends CouchbasePagingAndSortingRepository<Building, String> {

	List<Building> findByCompanyId(String companyId);

	Page<Building> findByCompanyIdAndNameLikeOrderByName(String companyId, String name, Pageable pageable);

	/**
	 * #(#n1ql.bucket): Use this syntax avoids hard-coding your bucket name in
	 * your query (#n1ql.selectEntity): syntax-sugar to SELECT * FROM
	 * #(#n1ql.bucket): #{#n1ql.filter}: syntax-sugar to filter the document by
	 * type, technically it means class = ‘myPackage.MyClassName’ (_class is the
	 * attribute automatically added in the document to define its type when you
	 * are working with Couchbase on Spring Data )
	 **/

	@Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and companyId = $1 and $2 within #{#n1ql.bucket}")
	Building findByCompanyAndAreaId(String companyId, String areaId);

	@Query("#{#n1ql.selectEntity} where #{#n1ql.filter} AND ANY phone IN phoneNumbers SATISFIES phone = $1 END")
	List<Building> findByPhoneNumber(String telephoneNumber);

	@Query("SELECT COUNT(*) AS count FROM #{#n1ql.bucket} WHERE #{#n1ql.filter} and companyId = $1")
	Long countBuildings(String companyId);
}
