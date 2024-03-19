// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.github.f4b6a3.uuid.UuidCreator;
import com.xenoterracide.jpa.annotation.Initializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

@Audited
@Entity
public class BarEntity extends AbstractEntity<BarEntity.@NonNull Id> {

  private static final String[] INCLUDED_FIELDS_IN_TO_STRING = { "id", "name" };

  private @NonNull String name;

  BarEntity(@NonNull Id id, @NonNull String name) {
    super(id);
    this.name = name;
  }

  public BarEntity() {}

  static @NonNull BarEntity create(@NonNull String name) {
    return new BarEntity(Id.create(), name);
  }

  @Override
  protected boolean canEqual(@NonNull AbstractEntity<?> that) {
    return that instanceof BarEntity;
  }

  @NotNull
  @Column(nullable = false)
  public @NonNull String getName() {
    return name;
  }

  @Initializer
  void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  protected @NonNull String[] includedFieldsInToString() {
    return INCLUDED_FIELDS_IN_TO_STRING;
  }

  public static class Id extends AbstractIdentity<@NonNull UUID> {

    @Serial
    @Transient
    private static final long serialVersionUID = 1L;

    protected Id() {}

    private Id(@NonNull UUID id) {
      super(id);
    }

    public static Id create() {
      return new Id(UuidCreator.getTimeOrderedEpoch());
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentity<?> that) {
      return that instanceof Id;
    }
  }
}
