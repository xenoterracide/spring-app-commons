// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import jakarta.persistence.Entity;
import java.io.Serial;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

/**
 * Represents a revision entity.
 */
@Entity
@RevisionEntity
public class RevEntity extends DefaultRevisionEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * For JPA.
   */
  protected RevEntity() {}
}
