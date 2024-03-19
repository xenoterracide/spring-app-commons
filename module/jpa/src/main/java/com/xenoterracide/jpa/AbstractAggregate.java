// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import org.jspecify.annotations.NonNull;

/**
 * An abstract class for Domain Aggregates.
 *
 * @param <ID> the type parameter
 */
@MappedSuperclass
public abstract class AbstractAggregate<ID extends AbstractIdentity<? extends Serializable>>
  extends AbstractEntity<ID> {

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractAggregate() {}

  /**
   * Instantiates a new Abstract aggregate.
   *
   * @param id the id
   */
  protected AbstractAggregate(@NonNull ID id) {
    super(id);
  }
}
