package org.booking.core.domain.entity.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Author: Sergey.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected UUID uuid;

	@Column(name = "created_at")
	protected Date createdAt;

	@Column(name = "modified_at")
	protected Date modifiedAt;

	protected boolean deleted = false;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.uuid = UUID.randomUUID();
	}

	@PreUpdate
	protected void onUpdate() {
		this.modifiedAt = new Date();
	}

}
