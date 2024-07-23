package com.dqops.metadata.settings;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * SMTP server configuration specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SmtpServerConfigurationSpec extends AbstractSpec implements InvalidYamlStatusHolder {

    private static final ChildHierarchyNodeFieldMapImpl<SmtpServerConfigurationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("SMTP server host")
    private String host;

    @JsonPropertyDescription("SMTP server port")
    private String port;

    @JsonPropertyDescription("SMTP server use SSL option")
    private Boolean useSSL;

    @JsonPropertyDescription("SMTP server username")
    private String username;

    @JsonPropertyDescription("SMTP server password")
    private String password;

    @JsonIgnore
    private String yamlParsingError;

    /**
     * Returns an SMTP server host.
     * @return SMTP server host.
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets an SMTP server host.
     * @param host SMTP server host.
     */
    public void setHost(String host) {
        setDirtyIf(!Objects.equals(this.host, host));
        this.host = host;
    }

    /**
     * Returns an SMTP server port.
     * @return SMTP server port.
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets an SMTP server port.
     * @param port SMTP server port.
     */
    public void setPort(String port) {
        setDirtyIf(!Objects.equals(this.port, port));
        this.port = port;
    }

    /**
     * Returns whether the SMTP server uses SSL.
     * @return whether the SMTP server uses SSL.
     */
    public Boolean getUseSSL() {
        return useSSL;
    }

    /**
     * Sets an SMTP server to use SSL.
     * @param useSSL SMTP server to use SSL.
     */
    public void setUseSSL(Boolean useSSL) {
        setDirtyIf(!Objects.equals(this.useSSL, useSSL));
        this.useSSL = useSSL;
    }

    /**
     * Returns an SMTP server user name.
     * @return SMTP server user name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username SMTP server user name.
     * Sets an SMTP server user name.
     */
    public void setUsername(String username) {
        setDirtyIf(!Objects.equals(this.username, username));
        this.username = username;
    }

    /**
     * Returns an SMTP server password.
     * @return SMTP server password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password SMTP server password
     * Sets an SMTP server password
     */
    public void setPassword(String password) {
        setDirtyIf(!Objects.equals(this.password, password));
        this.password = password;
    }

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
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
     * Creates and returns a deep copy of this object.
     */
    @Override
    public SmtpServerConfigurationSpec deepClone() {
        return (SmtpServerConfigurationSpec) super.deepClone();
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider to use.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public SmtpServerConfigurationSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        SmtpServerConfigurationSpec cloned = (SmtpServerConfigurationSpec) super.deepClone();
        cloned.host = secretValueProvider.expandValue(this.host, lookupContext);
        cloned.port = secretValueProvider.expandValue(this.port, lookupContext);
        cloned.username = secretValueProvider.expandValue(this.username, lookupContext);
        cloned.password = secretValueProvider.expandValue(this.password, lookupContext);
        return cloned;
    }

    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

}
