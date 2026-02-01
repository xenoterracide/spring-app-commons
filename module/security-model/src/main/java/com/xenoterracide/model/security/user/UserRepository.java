// Copyright 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.model.security.user;

import io.helidon.data.Data;
import java.util.Optional;
import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for {@link User}.
 */
@Repository
@Data.Repository
public interface UserRepository extends Data.BasicRepository<User, User.UserId> {
  /**
   * Finds a user by name.
   *
   * @param name
   *   the username
   * @return the user
   */
  Optional<User> findByName(String name);

  /**
   * Finds a user by identity provider user.
   *
   * @param id
   *   the identity provider user id
   * @return the user
   */
  @Query("select u from User u join u.identityProviderUsers i where i.id = ?1")
  Optional<User> findByIdentityProviderUser(IdentityProviderUser.IdentityProviderUserId id);
}
