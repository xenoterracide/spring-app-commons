// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.model.Identifiable;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
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
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

@Audited
@Entity
@Table(
  name = "foreign_idp_user_identities",
  uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "idp", "idp_user_id" })
)
public class ForeignIdPUserIdentity implements Identifiable<ForeignIdPUserIdentity.Id> {

  @EmbeddedId
  private @NonNull Id id;

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

  @Override
  public Id getId() {
    return this.id;
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

  protected boolean canEqual(@NonNull Identifiable<?> that) {
    return that instanceof ForeignIdPUserIdentity;
  }

  public enum IdP {
    AUTH0,
  }

  /**
   * The primary key for {@link ForeignIdPUserIdentity}.
   */
  @Embeddable
  public static class Id implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private IdP idP;
    private String idPUserId;
    private UUID userId;

    /**
     * For JPA.
     */
    protected Id() {}

    Id(@NonNull IdP idP, @NonNull String idPUserId, @NonNull UUID userId) {
      this.idP = idP;
      this.idPUserId = idPUserId;
      this.userId = userId;
    }

    @Initializer
    void setIdP(@NonNull IdP idP) {
      this.idP = idP;
    }

    @Initializer
    public void setIdPUserId(@NonNull String idPUserId) {
      this.idPUserId = idPUserId;
    }

    @Initializer
    public void setUserId(@NonNull UUID userId) {
      this.userId = userId;
    }
  }
}
