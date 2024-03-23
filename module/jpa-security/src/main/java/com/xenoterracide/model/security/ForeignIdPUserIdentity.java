// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.jpa.AbstractEntity;
import com.xenoterracide.jpa.AbstractIdentity;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

@Audited
@Entity
@Table(
  name = "foreign_idp_user_identities",
  uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "idp", "idp_user_id" })
)
public class ForeignIdPUserIdentity extends AbstractEntity<ForeignIdPUserIdentity.@NonNull Id> {

  private @NonNull User user;
  private @NonNull IdP idP;
  private @NonNull String idPUserId;

  protected ForeignIdPUserIdentity() {}

  protected ForeignIdPUserIdentity(@NonNull Id id, @NonNull User user, @NonNull IdP idP, @NonNull String idPUserId) {
    super(id);
    this.user = user;
    this.idP = idP;
    this.idPUserId = idPUserId;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public @NonNull IdP getIdP() {
    return this.idP;
  }

  @Initializer
  void setIdP(@NonNull IdP idP) {
    this.idP = idP;
  }

  @NotNull
  @Column(nullable = false, name = "idp_user_id")
  public @NonNull String getIdPUserId() {
    return this.idPUserId;
  }

  @Initializer
  void setIdPUserId(@NonNull String idPUserId) {
    this.idPUserId = idPUserId;
  }

  @ManyToOne(
    optional = false,
    fetch = FetchType.LAZY,
    cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
  )
  @JoinColumn(
    nullable = false,
    updatable = false,
    unique = true,
    name = "user_id",
    foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
  )
  public @NonNull User getUser() {
    return user;
  }

  @Initializer
  void setUser(@NonNull User user) {
    this.user = user;
  }

  @Override
  protected boolean canEqual(@NonNull AbstractEntity<?> that) {
    return that instanceof ForeignIdPUserIdentity;
  }

  public static enum IdP {
    AUTH0,
  }

  public static class Id extends AbstractIdentity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * NO-OP parent constuctor for JPA only.
     */
    @SuppressWarnings("unused")
    protected Id() {}

    protected Id(@NonNull UUID id) {
      super(id);
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentity that) {
      return that instanceof Id;
    }
  }
}
