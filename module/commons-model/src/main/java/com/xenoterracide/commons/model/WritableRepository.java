// Copyright 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.model;

import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Interface for repositories that can write to a data store. For use instead of
 * {@link org.springframework.data.repository.CrudRepository}.
 *
 * @param <AGG>
 *   the type of the aggregate
 * @param <ID>
 *   the type of the identifier
 */
@NoRepositoryBean
public interface WritableRepository<AGG, ID extends Serializable> extends Repository<AGG, ID> {
  /**
   * Saves an aggregate.
   *
   * @param aggregate
   *   the aggregate to save.
   * @param <S>
   *   potentially a subtype of the aggregate.
   * @return the saved aggregate.
   */
  <S extends AGG> S save(S aggregate);

  /**
   * Saves all aggregates.
   *
   * @param aggregates
   *   the aggregates to save.
   * @param <S>
   *   potentially a subtype of the aggregate.
   * @return the saved aggregates.
   */
  <S extends AGG> Iterable<S> saveAll(Iterable<S> aggregates);
}
