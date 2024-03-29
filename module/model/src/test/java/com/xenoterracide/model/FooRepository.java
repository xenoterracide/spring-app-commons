// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import java.util.Optional;
import org.jspecify.annotations.NonNull;

interface FooRepository extends WritableRepository<Foo, @NonNull Long> {
  Optional<Foo> findById(@NonNull Long id);
}
