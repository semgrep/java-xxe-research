package parsers.XMLReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
// import org.xml.sax.helpers.XMLReaderFactory;

public class Tests {
  public static void main(String[] args) {
    // ignore error messages
    System.setErr(new PrintStream(new OutputStream() {
      public void write(int b) {
      }
    }));

    try {
      System.out.println("TESTING XMLReader configurations");
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

  public static void testDefaultConfig() throws SAXException, ParserConfigurationException {
    System.out.println("Default Config");
    XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    parseAll(reader);
  }

  public static void testSetFeatureSecreProcessing() throws SAXException, ParserConfigurationException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    reader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    parseAll(reader);
  }

  public static void testAccessExternalDTD() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    reader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(reader);
  }

  public static void testAccessExternalSchema() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
    XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    reader.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    parseAll(reader);
  }

  public static void testAccessExternalStylesheet() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, \"\") - n/a");
  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    try {
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    try {
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    try {
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      parseAll(reader);
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
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://apache.org/xml/features/xinclude", false);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    try {
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Parse the test files
   */

  public static void parseAll(XMLReader reader) {
    parseXmlBomb(reader);
    parseXmlDtd(reader);
    parseXmlParameterEntity(reader);
    parseSchemaDtd(reader);
    parseSchemaImport(reader);
    parseSchemaInclude(reader);
    parseStylesheetDtd(reader);
    parseStylesheetImport(reader);
    parseStylesheetInclude(reader);
    parseStylesheetDocument(reader);
  }

  public static void parseXmlBomb(XMLReader reader) {
    try {
      File xmlFile = new File("payloads-new/xml-attacks/xml-bomb.xml");
      InputStream is = new FileInputStream(xmlFile);
      reader.parse(new InputSource(is));
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

  public static void parseXmlDtd(XMLReader reader) {
    try {
      File xmlFile = new File("payloads-new/xml-attacks/input-dtd.xml");
      InputStream is = new FileInputStream(xmlFile);
      reader.parse(new InputSource(is));
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

  public static void parseXmlParameterEntity(XMLReader reader) {
    try {
      File xmlFile = new File("payloads-new/xml-attacks/input-with-parameter-entity.xml");
      InputStream is = new FileInputStream(xmlFile);
      reader.parse(new InputSource(is));
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

  public static void parseSchemaDtd(XMLReader reader) {
    try {
      File xsdFile = new File("payloads-new/xsd-schema-attacks/schema-dtd.xsd");
      InputStream is = new FileInputStream(xsdFile);
      reader.parse(new InputSource(is));
      System.out.println("    Parse Schema Dtd: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Schema Dtd: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Dtd: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Dtd: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseSchemaImport(XMLReader reader) {
    try {
      File xsdFile = new File("payloads-new/xsd-schema-attacks/schema-import.xsd");
      InputStream is = new FileInputStream(xsdFile);
      reader.parse(new InputSource(is));
      System.out.println("    Parse Schema Import: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Schema Import: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Import: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Import: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseSchemaInclude(XMLReader reader) {
    try {
      File xsdFile = new File("payloads-new/xsd-schema-attacks/schema-include.xsd");
      InputStream is = new FileInputStream(xsdFile);
      reader.parse(new InputSource(is));
      System.out.println("    Parse Schema Include: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Schema Include: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Include: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Include: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseStylesheetDtd(XMLReader reader) {
    try {
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-dtd.xsl");
      InputStream is = new FileInputStream(style);
      reader.parse(new InputSource(is));
      System.out.println("    Parse Stylesheet Dtd: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Stylesheet Dtd: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Dtd: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Dtd: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseStylesheetDocument(XMLReader reader) {
    try {
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-document.xsl");
      InputStream is = new FileInputStream(style);
      reader.parse(new InputSource(is));
      System.out.println("    Parse Stylesheet Document: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Stylesheet Document: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Document: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Document: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseStylesheetImport(XMLReader reader) {
    try {
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-import.xsl");
      InputStream is = new FileInputStream(style);
      reader.parse(new InputSource(is));
      System.out.println("    Parse Stylesheet Import: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Stylesheet Import: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Import: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Import: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseStylesheetInclude(XMLReader reader) {
    try {
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-include.xsl");
      InputStream is = new FileInputStream(style);
      reader.parse(new InputSource(is));
      System.out.println("    Parse Stylesheet Include: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Stylesheet Include: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Include: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Include: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

}
