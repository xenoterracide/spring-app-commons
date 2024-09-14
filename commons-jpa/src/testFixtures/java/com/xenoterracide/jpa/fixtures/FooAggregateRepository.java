// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa.fixtures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

public interface FooAggregateRepository
  extends JpaRepository<FooAggregate, FooAggregate.Id>, RevisionRepository<FooAggregate, FooAggregate.Id, Integer> {
  @Query("from FooAggregate f inner join fetch f.bars where f.id = :id")
  FooAggregate findOneById(FooAggregate.Id id);

  @Query("from BarEntity b where b.id = :id")
  BarEntity findOneBarEntityById(BarEntity.Id id);
}
