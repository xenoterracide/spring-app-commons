// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.model.WritableRepository;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for {@link User}.
 */
public interface UserRepository extends WritableRepository<@NonNull User, User.@NonNull Identifier> {
  /**
   * Finds a user by id.
   *
   * @param id the surrogate id
   * @return the user
   */
  @NonNull
  Optional<@Nullable User> findById(User.@NonNull Identifier id);

  /**
   * Finds a user by name.
   *
   * @param name the username
   * @return the user
   */
  @NonNull
  Optional<@Nullable User> findByName(@NonNull String name);

  /**
   * Finds a user by identity provider user.
   *
   * @param id the identity provider user id
   * @return the user
   */
  @Query("select u from User u join u.identityProviderUsers i where i.id = ?1")
  @NonNull
  Optional<@Nullable User> findByIdentityProviderUser(IdentityProviderUser.@NonNull Identifier id);
}
