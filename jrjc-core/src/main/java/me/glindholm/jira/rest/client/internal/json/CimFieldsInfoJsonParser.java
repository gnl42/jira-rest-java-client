package me.glindholm.jira.rest.client.internal.json;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jdt.annotation.Nullable;

import me.glindholm.jira.rest.client.api.domain.CimFieldInfo;
import me.glindholm.jira.rest.client.api.domain.FieldSchema;
import me.glindholm.jira.rest.client.api.domain.StandardOperation;

public class CimFieldsInfoJsonParser implements JsonObjectParser<CimFieldInfo> {

    private final FieldSchemaJsonParser fieldSchemaJsonParser = new FieldSchemaJsonParser();

    protected final Map<String, JsonObjectParser> registeredAllowedValueParsers = new HashMap<>() {
        private static final long serialVersionUID = 1L;

        {
            put("project", new BasicProjectJsonParser());
            put("version", new VersionJsonParser());
            put("issuetype", new IssueTypeJsonParser());
            put("priority", new BasicPriorityJsonParser());
            put("customFieldOption", new CustomFieldOptionJsonParser());
            put("component", new BasicComponentJsonParser());
            put("resolution", new ResolutionJsonParser());
            put("securitylevel", new SecurityLevelJsonParser());
        }
    };

    @Override
    public CimFieldInfo parse(final JSONObject json) throws JSONException, URISyntaxException {
        final String id = JsonParseUtil.getOptionalString(json, "id");
        return parse(json, id);
    }

    public CimFieldInfo parse(final JSONObject json, final String id) throws JSONException, URISyntaxException {
        final boolean required = json.getBoolean("required");
        final String name = JsonParseUtil.getOptionalString(json, "name");
        final FieldSchema schema = fieldSchemaJsonParser.parse(json.getJSONObject("schema"));
        final Set<StandardOperation> operations = parseOperations(json.getJSONArray("operations"));
        final List<Object> allowedValues = parseAllowedValues(json.optJSONArray("allowedValues"), schema);
        final URI autoCompleteUri = JsonParseUtil.parseOptionalURI(json, "autoCompleteUrl");

        return new CimFieldInfo(id, required, name, schema, operations, allowedValues, autoCompleteUri);
    }

    private List<Object> parseAllowedValues(@Nullable final JSONArray allowedValues, final FieldSchema fieldSchema) throws JSONException, URISyntaxException {
        if (allowedValues == null || allowedValues.equals(JSONObject.NULL)) {
            return null;
        }

        if (allowedValues.length() == 0) {
            return Collections.emptyList();
        }

        final JsonObjectParser<Object> allowedValuesJsonParser = getParserFor(fieldSchema);
        if (allowedValuesJsonParser != null) {
            JSONArray valuesToParse;
            // fixes for JRADEV-12999
            final boolean isProjectCF = "project".equals(fieldSchema.getType())
                    && "com.atlassian.jira.plugin.system.customfieldtypes:project".equals(fieldSchema.getCustom());
            final boolean isVersionCF = "version".equals(fieldSchema.getType())
                    && "com.atlassian.jira.plugin.system.customfieldtypes:version".equals(fieldSchema.getCustom());
            final boolean isMultiVersionCF = "array".equals(fieldSchema.getType()) && "version".equals(fieldSchema.getItems())
                    && "com.atlassian.jira.plugin.system.customfieldtypes:multiversion".equals(fieldSchema.getCustom());

            if ((isProjectCF || isVersionCF || isMultiVersionCF) && allowedValues.get(0) instanceof JSONArray) {
                valuesToParse = allowedValues.getJSONArray(0);
            } else {
                valuesToParse = allowedValues;
            }
            return GenericJsonArrayParser.create(allowedValuesJsonParser).parse(valuesToParse);
        } else {
            // fallback - just return collection of JSONObjects
            final int itemsLength = allowedValues.length();
            final List<Object> res = new ArrayList<>(itemsLength);
            for (int i = 0; i < itemsLength; i++) {
                res.add(allowedValues.get(i));
            }
            return res;
        }
    }

    private Set<StandardOperation> parseOperations(final JSONArray operations) throws JSONException {
        final int operationsCount = operations.length();
        final Set<StandardOperation> res = new HashSet<>(operationsCount);
        for (int i = 0; i < operationsCount; i++) {
            final String opName = operations.getString(i);
            final StandardOperation op = StandardOperation.valueOf(opName.toUpperCase());
            res.add(op);
        }
        return res;
    }

    @Nullable
    private JsonObjectParser<Object> getParserFor(final FieldSchema fieldSchema) throws JSONException {
        final Set<String> customFieldsTypesWithFieldOption = Set.of("com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes",
                "com.atlassian.jira.plugin.system.customfieldtypes:radiobuttons", "com.atlassian.jira.plugin.system.customfieldtypes:select",
                "com.atlassian.jira.plugin.system.customfieldtypes:cascadingselect", "com.atlassian.jira.plugin.system.customfieldtypes:multiselect");
        String type = "array".equals(fieldSchema.getType()) ? fieldSchema.getItems() : fieldSchema.getType();
        final String custom = fieldSchema.getCustom();
        if (custom != null && customFieldsTypesWithFieldOption.contains(custom)) {
            type = "customFieldOption";
        }
        @SuppressWarnings("unchecked")
        final JsonObjectParser<Object> jsonParser = registeredAllowedValueParsers.get(type);
        return jsonParser;
    }
}
