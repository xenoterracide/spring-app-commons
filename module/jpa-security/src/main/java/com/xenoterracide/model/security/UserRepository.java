// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.model.WritableRepository;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface UserRepository extends WritableRepository<User, User.Id> {
  Optional<@Nullable User> findById(User.@NonNull Id id);
}
