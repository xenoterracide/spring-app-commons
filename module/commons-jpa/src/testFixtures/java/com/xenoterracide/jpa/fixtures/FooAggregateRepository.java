// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.jpa.fixtures;

import io.helidon.data.Data;

@Data.Repository
public interface FooAggregateRepository extends Data.BasicRepository<FooAggregate, FooAggregate.Id> {
  @Data.Query("from FooAggregate f inner join fetch f.bars where f.id = :id")
  FooAggregate findOneById(FooAggregate.Id id);

  @Data.Query("from BarEntity b where b.id = :id")
  BarEntity findOneBarEntityById(BarEntity.Id id);
}
