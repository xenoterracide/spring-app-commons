// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import org.jspecify.annotations.NonNull;

/**
 * The Abstract for Aggregates
 *
 * @param <ID> the type parameter
 */
@MappedSuperclass
public abstract class AbstractUuidAggregate<ID extends AbstractIdentity<? extends Serializable>>
  extends AbstractEntity<ID>
  implements Identifiable<@NonNull ID> {

  protected AbstractUuidAggregate() {}
}
