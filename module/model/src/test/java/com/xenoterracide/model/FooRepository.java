// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import java.util.Optional;

interface FooRepository extends WritableRepository<Foo, Long> {
  Optional<Foo> findById(Long id);
}
