package ai.dqo.metadata.fields;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Defines a single field that is a sensor parameter or a rule parameter.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ParameterDefinitionSpec", description = "Defines a single field that is a sensor parameter or a rule parameter.")
public class ParameterDefinitionSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<ParameterDefinitionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    /**
     * Field name that matches the field name (snake_case) used in the YAML specification.
     */
    @JsonPropertyDescription("Field name that matches the field name (snake_case) used in the YAML specification.")
    private String fieldName;

    @JsonPropertyDescription("Field display name that should be shown as a label for the control.")
    private String displayName;

    @JsonPropertyDescription("Help text (full description) that will be shown to the user as a hint when the cursor is moved over the control.")
    private String helpHext;

    @JsonPropertyDescription("Parameter data type.")
    private ParameterDataType dataType;

    @JsonPropertyDescription("True when the value for the parameter must be provided.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean required;

    /**
     * List of allowed values for a field that is of an enum type.
     */
    @JsonPropertyDescription("List of allowed values for a field that is of an enum type.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> allowedValues;

    /**
     * Returns the field type as used in the yaml file.
     * @return Field name.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field name.
     * @param fieldName Field name.
     */
    public void setFieldName(String fieldName) {
        this.setDirtyIf(!Objects.equals(this.fieldName, fieldName));
        this.fieldName = fieldName;
    }

    /**
     * Returns the display name of the field. It is a control label on the data quality check editing screen.
     * @return Display name of the field (field's label).
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name used as a label on the editing screen.
     * @param displayName New display name.
     */
    public void setDisplayName(String displayName) {
        this.setDirtyIf(!Objects.equals(this.displayName, displayName));
        this.displayName = displayName;
    }

    /**
     * Returns the help text (hint) that will be shown to the user.
     * @return Help text.
     */
    public String getHelpHext() {
        return helpHext;
    }

    /**
     * Sets the help text (hint) to be shown to the user.
     * @param helpHext New help text.
     */
    public void setHelpHext(String helpHext) {
        this.setDirtyIf(!Objects.equals(this.helpHext, helpHext));
        this.helpHext = helpHext;
    }

    /**
     * Returns true when the field is required.
     * @return True when the value of the field must be provided.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the status if the parameter value must be provided.
     * @param required True when the parameter value must be provided.
     */
    public void setRequired(boolean required) {
        this.setDirtyIf(this.required != required);
        this.required = required;
    }

    /**
     * Returns the field data type.
     * @return Field data type.
     */
    public ParameterDataType getDataType() {
        return dataType;
    }

    /**
     * Sets the field data type.
     * @param dataType Field data type.
     */
    public void setDataType(ParameterDataType dataType) {
        this.setDirtyIf(this.dataType != dataType);
        this.dataType = dataType;
    }

    /**
     * Returns a list of allowed values for a field that is an enum.
     * @return List of allowed values. It is a read-only list.
     */
    public List<String> getAllowedValues() {
        return allowedValues;
    }

    /**
     * Sets a list of allowed field values for an enum field.
     * @param allowedValues List of allowed fields.
     */
    public void setAllowedValues(List<String> allowedValues) {
        this.setDirtyIf(!Objects.equals(this.allowedValues, allowedValues));
        this.allowedValues = allowedValues != null ? Collections.unmodifiableList(allowedValues) : null;
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
    public ParameterDefinitionSpec clone() {
        try {
            ParameterDefinitionSpec cloned = (ParameterDefinitionSpec)super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }
}
