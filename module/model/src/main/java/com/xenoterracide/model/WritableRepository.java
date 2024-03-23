// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import java.io.Serializable;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface WritableRepository<AGG, ID extends Serializable> extends Repository<@NonNull AGG, @NonNull ID> {
  <S extends @NonNull AGG> S save(S entity);

  <S extends @NonNull AGG> Iterable<S> saveAll(@NonNull Iterable<S> entities);
}
