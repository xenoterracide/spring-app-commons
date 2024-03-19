// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Entity;
import java.io.Serial;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity
public class RevEntity extends DefaultRevisionEntity {

  @Serial
  private static final long serialVersionUID = 1L;
}
