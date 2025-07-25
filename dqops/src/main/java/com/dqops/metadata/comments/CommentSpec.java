/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.comments;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import org.apache.parquet.Strings;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class CommentSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<CommentSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Comment date and time")
    private LocalDateTime date;

    @JsonPropertyDescription("Commented by")
    private String commentBy;

    @JsonPropertyDescription("Comment text")
    private String comment;

    /**
     * Comment date.
     * @return Commend date.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the comment date.
     * @param date Commend date.
     */
    public void setDate(LocalDateTime date) {
		this.setDirtyIf(!Objects.equals(this.date, date));
        this.date = date;
    }

    /**
     * Commented by (person name or login name).
     * @return Commented by person / login.
     */
    public String getCommentBy() {
        return commentBy;
    }

    /**
     * Sets the name of the person who added a comment.
     * @param commentBy Commented by name.
     */
    public void setCommentBy(String commentBy) {
		this.setDirtyIf(!Objects.equals(this.commentBy, commentBy));
        this.commentBy = commentBy;
    }

    /**
     * Gets the comment text.
     * @return Comment text.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment text.
     * @param comment New comment text.
     */
    public void setComment(String comment) {
		this.setDirtyIf(!Objects.equals(this.comment, comment));
        this.comment = comment;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public CommentSpec deepClone() {
        CommentSpec cloned = (CommentSpec)super.deepClone();
        return cloned;
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    @JsonIgnore
    public boolean isDefault() {
        if (!Strings.isNullOrEmpty(this.comment) ||
                this.date != null ||
                !Strings.isNullOrEmpty(this.commentBy)) {
            return false;
        }

        return true;
    }

    public static class CommentSpecSampleFactory implements SampleValueFactory<CommentSpec> {
        @Override
        public CommentSpec createSample() {
            return new CommentSpec() {{
                setDate(LocalDateTime.of(2007, 12, 3, 10, 15,30));
                setCommentBy(SampleStringsRegistry.getUserName());
                setComment(SampleStringsRegistry.getComment());
            }};
        }
    }
}
