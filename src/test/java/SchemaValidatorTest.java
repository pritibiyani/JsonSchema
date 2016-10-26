import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class SchemaValidatorTest {

  private static final String SCHEMA_FILE = "book_schema.json";

  @org.junit.Test
  public void shouldReturnTrueWhenJSONDocumentIsValid() throws ProcessingException, IOException {
    String validJson = "book_valid.json";
    File jsonFile = new File("resources/jsonDocument/" + validJson);
    File schemaFile = new File("resources/schema/" + SCHEMA_FILE);

    SchemaValidator schemaValidator = new SchemaValidator(schemaFile, jsonFile);
    ProcessingReport report = schemaValidator.validate();
    assertTrue(report.isSuccess());
  }

  @Test
  public void shouldReturnFalseWhenJsonDocumentIsNotValid() throws IOException, ProcessingException {
//    String missingFieldsJson = "book_missing_fields.json";
    String missingFieldsJson = "book_price_is_invalid.json";
    File jsonFile = new File("resources/jsonDocument/" + missingFieldsJson);
    File schemaFile = new File("resources/schema/" + SCHEMA_FILE);

    SchemaValidator schemaValidator = new SchemaValidator(schemaFile, jsonFile);
    ProcessingReport report = schemaValidator.validate();

    for (ProcessingMessage processingMessage : report) {
      System.out.println(processingMessage.getMessage());
    }
    assertFalse(report.isSuccess());
  }

}
