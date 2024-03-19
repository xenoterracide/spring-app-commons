// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface FooAggregateRepository
  extends JpaRepository<FooAggregate, FooAggregate.Id>, RevisionRepository<FooAggregate, FooAggregate.Id, Long> {}
