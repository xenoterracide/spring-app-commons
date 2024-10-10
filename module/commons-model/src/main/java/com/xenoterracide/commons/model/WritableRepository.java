// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide.commons.model;

import java.io.Serializable;
import org.jspecify.annotations.NonNull;
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
public interface WritableRepository<AGG, ID extends Serializable> extends Repository<@NonNull AGG, @NonNull ID> {
  /**
   * Saves an aggregate.
   *
   * @param aggregate
   *   the aggregate to save.
   * @param <S>
   *   potentially a subtype of the aggregate.
   * @return the saved aggregate.
   */
  <S extends @NonNull AGG> @NonNull S save(@NonNull S aggregate);

  /**
   * Saves all aggregates.
   *
   * @param aggregates
   *   the aggregates to save.
   * @param <S>
   *   potentially a subtype of the aggregate.
   * @return the saved aggregates.
   */
  <S extends @NonNull AGG> Iterable<@NonNull S> saveAll(@NonNull Iterable<@NonNull S> aggregates);
}
