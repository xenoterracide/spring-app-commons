// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestAggregateRepository extends JpaRepository<Foo, Foo.Id> {}