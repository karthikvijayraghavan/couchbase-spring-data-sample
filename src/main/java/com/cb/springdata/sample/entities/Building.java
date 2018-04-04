package com.cb.springdata.sample.entities;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Couchbase’s annotation which defines an entity, similar to @Entity in JPA.
 * Couchbase will automatically add a property called _class in the document to
 * use it as the document type.
 **/
@Document
// Lombok’s annotation, auto-generate getters and setters
@Data
/**
 * Lombok’s annotation, auto-generate a constructor using all fields of the
 * class, this constructor is used in our tests.
 **/
@AllArgsConstructor
/**
 * Lombok’s annotation, auto-generate a constructor with no args (required by
 * Spring Data)
 **/
@NoArgsConstructor
/**
 * Lombok’s annotation, auto-generate equals and hashcode methods, also used in
 * our tests.
 **/
@EqualsAndHashCode
public class Building {

	@NotNull
	// The document’s key
	@Id
	private String id;

	@NotNull
	/**
	 * Couchbase’s annotations, similar to @Column. Can be used as Simple
	 * Property, Arrays and Entities for ManyToOne kind of relationships
	 **/
	@Field
	private String name;

	@NotNull
	@Field
	private String companyId;

	@Field
	private List<Area> areas = new ArrayList<>();

	@Field
	private List<String> phoneNumbers = new ArrayList<>();

}
