// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide.commons.model.test;

import com.xenoterracide.commons.model.WritableRepository;
import java.util.Optional;

interface FooRepository extends WritableRepository<Foo, Long> {
  Optional<Foo> findById(Long id);
}
