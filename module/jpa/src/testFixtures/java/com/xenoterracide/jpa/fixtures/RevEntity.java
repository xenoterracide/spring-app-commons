// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa.fixtures;

import jakarta.persistence.Entity;
import java.io.Serial;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

// keep me in test as I'm a workaround for https://hibernate.atlassian.net/browse/HHH-17612
@Entity
@RevisionEntity
public class RevEntity extends DefaultRevisionEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  public RevEntity() {}
}
