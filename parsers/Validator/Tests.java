package parsers.Validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

public class Tests {
  public static void main(String[] args) {
    // ignore error messages
    System.setErr(new PrintStream(new OutputStream() {
      public void write(int b) {
      }
    }));

    try {
      System.out.println("TESTING Validator configurations");
      testDefaultConfig();
      testSetFeatureSecreProcessing();
      testAccessExternalDTD();
      testAccessExternalSchema();
      testAccessExternalStylesheet();
      testDisallowDoctypeDecl();
      testExternalGeneralEntities();
      testExternalParameterEntities();
      testSetValidating();
      testSetExpandEntities();
      testSetXIncludeAware();
      testXInclude();
      testLoadExternalDTD();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      // do nothing
    }
  }

  public static void testDefaultConfig() throws SAXException {
    System.out.println("Default Config");
    File xsdFile = new File("payloads-new/ok-schema.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    parseAll(validator);
  }

  public static void testSetFeatureSecreProcessing() throws SAXException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    File xsdFile = new File("payloads-new/ok-schema.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    parseAll(validator);
  }

  public static void testAccessExternalDTD() throws SAXException {
    System.out.println("setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    File xsdFile = new File("payloads-new/ok-schema.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(validator);
  }

  public static void testAccessExternalSchema() throws SAXException {
    System.out.println("setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
    File xsdFile = new File("payloads-new/ok-schema.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    parseAll(validator);
  }

  public static void testAccessExternalStylesheet() throws TransformerConfigurationException {
    System.out.println("ACCESS_EXTERNAL_STYLESHEET - n/a");
  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    try {
      File xsdFile = new File("payloads-new/ok-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    try {
      File xsdFile = new File("payloads-new/ok-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://xml.org/sax/features/external-general-entities", false);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    try {
      File xsdFile = new File("payloads-new/ok-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testSetValidating() {
    System.out.println("setValidating(false) - n/a");
  }

  public static void testSetExpandEntities() {
    System.out.println("setExpandEntityReferences(false) - n/a");
  }

  public static void testSetXIncludeAware() {
    System.out.println("setXIncludeAware(false) - n/a");
  }

  public static void testXInclude() {
    System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
    try {
      File xsdFile = new File("payloads-new/ok-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/xinclude", false);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    try {
      File xsdFile = new File("payloads-new/ok-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Parse the test files
   */

  public static void parseAll(Validator validator) {
    parseXmlBomb(validator);
    parseXmlDtd(validator);
    parseXmlParameterEntity(validator);
    parseSchemaDtd(validator);
    parseSchemaImport(validator);
    parseSchemaInclude(validator);
    parseStylesheetDtd(validator);
    parseStylesheetImport(validator);
    parseStylesheetInclude(validator);
    parseStylesheetDocument(validator);
  }

  public static void parseXmlBomb(Validator validator) {
    try {

      File xmlFile = new File("payloads-new/xml-attacks/xml-bomb.xml");
      validator.validate(new StreamSource(xmlFile));

      System.out.println("    Parse XML Bomb: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains(
          "The parser has encountered more than \"64000\" entity expansions in this document; this is the limit imposed by the JDK")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Bomb: Insecure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Bomb: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseXmlDtd(Validator validator) {
    try {
      File xmlFile = new File("payloads-new/xml-attacks/input-dtd.xml");
      validator.validate(new StreamSource(xmlFile));

      System.out.println("    Parse XML Dtd: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse XML Dtd: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Dtd: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Dtd: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseXmlParameterEntity(Validator validator) {
    try {
      File xmlFile = new File("payloads-new/xml-attacks/input-with-parameter-entity.xml");
      validator.validate(new StreamSource(xmlFile));

      System.out.println("    Parse XML Parameter Entity: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse XML Parameter Entity: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Parameter Entity: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Parameter Entity: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseSchemaDtd(Validator validator) {
    System.out.println("    Parse Schema Dtd: n/a");
  }

  public static void parseSchemaImport(Validator validator) {
    System.out.println("    Parse Schema Import: n/a");
  }

  public static void parseSchemaInclude(Validator validator) {
    System.out.println("    Parse Schema Include: n/a");

  }

  public static void parseStylesheetDtd(Validator validator) {
    System.out.println("    Parse Stylesheet Dtd: n/a");
  }

  public static void parseStylesheetDocument(Validator validator) {
    System.out.println("    Parse Stylesheet Document: n/a");
  }

  public static void parseStylesheetImport(Validator validator) {
    System.out.println("    Parse Stylesheet Import: n/a");
  }

  public static void parseStylesheetInclude(Validator validator) {
    System.out.println("    Parse Stylesheet Include: n/a");
  }

}
