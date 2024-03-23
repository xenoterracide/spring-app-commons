// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.xenoterracide.model.EntityIdentifier;
import java.io.Serializable;
import org.jspecify.annotations.NonNull;

public record AggregateIdentifier<
  CID extends Serializable, ID extends AbstractIdentity, CLASS extends AbstractAggregate<@NonNull ID, ?>
>(Class<CLASS> type, ID id)
  implements EntityIdentifier<@NonNull ID, @NonNull CLASS> {}
