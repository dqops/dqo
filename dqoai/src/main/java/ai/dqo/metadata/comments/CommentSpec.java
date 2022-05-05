/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.metadata.comments;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Objects;

/**
 * Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class CommentSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<CommentSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Comment date and time.")
    private Date date;

    @JsonPropertyDescription("Commented by.")
    private String commentBy;

    @JsonPropertyDescription("Comment text.")
    private String comment;

    /**
     * Comment date.
     * @return Commend date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the comment date.
     * @param date Commend date.
     */
    public void setDate(Date date) {
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
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public CommentSpec clone() {
        try {
            CommentSpec cloned = (CommentSpec)super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }
}
