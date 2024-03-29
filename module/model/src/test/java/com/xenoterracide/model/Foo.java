// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Entity
class Foo implements Identifiable<@NonNull Long> {

  @Id
  @GeneratedValue
  private Long id;

  @Override
  public @Nullable Long getId() {
    return id;
  }

  void setId(@NonNull Long id) {
    this.id = id;
  }
}
