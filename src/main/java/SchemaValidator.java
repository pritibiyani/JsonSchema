import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.File;
import java.io.IOException;

public class SchemaValidator {
  public static final String JSON_V4_SCHEMA_IDENTIFIER = "http://json-schema.org/draft-04/schema#";
  public static final String JSON_SCHEMA_IDENTIFIER_ELEMENT = "$schema";

  private final File schemaFile;
  private final File jsonFile;

  public SchemaValidator(File schemaFile, File jsonFile) {
    this.schemaFile = schemaFile;
    this.jsonFile = jsonFile;
  }

  public ProcessingReport validate() throws ProcessingException, IOException {
    final JsonSchema schemaNode = getSchemaNode(schemaFile);
    final JsonNode jsonNode = getJsonNode(jsonFile);
    return schemaNode.validate(jsonNode);
  }

  private JsonSchema getSchemaNode(File schemaFile) throws ProcessingException, IOException {
    final JsonNode schemaNode = getJsonNode(schemaFile);
    return getSchemaNode(schemaNode);
  }

  private JsonSchema getSchemaNode(JsonNode jsonNode) throws ProcessingException {
    final JsonNode schemaIdentifier = jsonNode.get(JSON_SCHEMA_IDENTIFIER_ELEMENT);
    if (null == schemaIdentifier) {
      ((ObjectNode) jsonNode).put(JSON_SCHEMA_IDENTIFIER_ELEMENT, JSON_V4_SCHEMA_IDENTIFIER);
    }

    final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
    return factory.getJsonSchema(jsonNode);
  }

  private JsonNode getJsonNode(File jsonFile) throws IOException {
    return JsonLoader.fromFile(jsonFile);
  }
}
