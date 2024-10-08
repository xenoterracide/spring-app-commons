// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa;

import com.xenoterracide.commons.model.Identifiable;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.Audited;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Base class for entities that use a surrogate identifier.
 *
 * @param <ID>
 *   the type parameter
 * @param <AGG>
 *   the type parameter
 */
@Audited
@MappedSuperclass
public abstract class AbstractSurrogateEntity<ID extends Identifier & Serializable, AGG extends AggregateRoot<AGG, ?>>
    implements Entity<AGG, ID>, Identifiable<@NonNull ID> {

    private static final String[] DEFAULT_INCLUDED_FIELDS = { "id" };

    @Transient
    private boolean dirty;

    /**
     * Surrogate Identifier.
     */
    @NonNull
    private ID id;
    private Integer version;

    protected AbstractSurrogateEntity() {
        this.dirty = false;
    }

    protected AbstractSurrogateEntity(@NonNull ID id) {
        this.id = id;
    }

    @Version
    @Nullable
    @Column(nullable = false)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    protected void markDirty() {
        dirty = true;
    }

    @Id
    @Valid
    @NotNull
    @Identity
    @Column(nullable = false, updatable = false, unique = true)
    @Override
    public @NonNull ID getId() {
        return id;
    }

    public void setId(@NonNull ID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, dirty);
    }

    protected abstract boolean canEqual(AbstractSurrogateEntity<?, ?> that);

    @Override
    public boolean equals(Object other) {
        if (other instanceof AbstractSurrogateEntity<?, ?> that) {
            return (
                that.canEqual(this) &&
                    Objects.equals(this.id, that.id) &&
                    Objects.equals(this.version, that.version) &&
                    this.dirty == that.dirty
                );
        }
        return false;
    }

    protected String[] includedFieldsInToString() {
        return DEFAULT_INCLUDED_FIELDS.clone();
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.DEFAULT_STYLE);
        builder.setIncludeFieldNames(includedFieldsInToString());
        return builder.toString();
    }
}
