// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.tm.model;

import com.xenoterracide.model.Identifiable;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Foo implements Identifiable<Long> {

  @Id
  @GeneratedValue
  private Long id;

  @Override
  public Long getId() {
    return id;
  }

  @Initializer
  void setId(Long id) {
    this.id = id;
  }
}
