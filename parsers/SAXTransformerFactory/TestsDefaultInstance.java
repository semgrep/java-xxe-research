package parsers.SAXTransformerFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class TestsDefaultInstance {
  public static void main(String[] args) {
    // ignore error messages
    System.setErr(new PrintStream(new OutputStream() {
      public void write(int b) {
      }
    }));

    try {
      System.out.println("TESTING SAXTransformerFactory's newDefaultInstance configurations");
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

  public static void testDefaultConfig() {
    System.out.println("Default Config");
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    parseAll(tf);
  }

  public static void testSetFeatureSecreProcessing() throws TransformerConfigurationException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    parseAll(tf);
  }

  public static void testAccessExternalDTD() throws TransformerConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(tf);
  }

  public static void testAccessExternalSchema() {
    System.out.println("ACCESS_EXTERNAL_SCHEMA is n/a");
  }

  public static void testAccessExternalStylesheet() throws TransformerConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, \"\")");
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
    parseAll(tf);
  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      parseAll(tf);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    try {
      tf.setFeature("http://xml.org/sax/features/external-general-entities", true);
      parseAll(tf);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    try {
      tf.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
      parseAll(tf);
    } catch (TransformerConfigurationException e) {
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
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/xinclude", false);
      parseAll(tf);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    TransformerFactory tf = SAXTransformerFactory.newDefaultInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      parseAll(tf);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  /* Parsers */

  public static void parseAll(TransformerFactory tf) {
    parseXmlBomb(tf);
    parseXmlDtd(tf);
    parseXmlParameterEntity(tf);
    parseSchemaDtd(tf);
    parseSchemaImport(tf);
    parseSchemaInclude(tf);
    parseStylesheetDtd(tf);
    parseStylesheetImport(tf);
    parseStylesheetInclude(tf);
    parseStylesheetDocument(tf);
  }

  public static void parseXmlBomb(TransformerFactory tf) {
    try {
      File input = new File("payloads-new/xml-attacks/xml-bomb.xml");
      File style = new File("payloads-new/ok-stylesheet.xsl");
      File output = new File("payloads/input-with-stylesheet/output.csv");

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);

      Transformer transformer = tf.newTransformer(xsltSource);

      Result target = new StreamResult(output);
      transformer.transform(source, target);

      System.out.println("    Parse XML Bomb: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains(
          "The parser has encountered more than \"64000\" entity expansions in this document; this is the limit imposed by the JDK")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Bomb: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Bomb: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseXmlDtd(TransformerFactory tf) {
    try {
      File input = new File("payloads-new/xml-attacks/input-dtd.xml");
      File style = new File("payloads-new/ok-stylesheet.xsl");
      File output = new File("payloads/input-with-stylesheet/output.csv");

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);

      Transformer transformer = tf.newTransformer(xsltSource);

      Result target = new StreamResult(output);
      transformer.transform(source, target);
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

  public static void parseXmlParameterEntity(TransformerFactory tf) {
    try {
      File input = new File("payloads-new/xml-attacks/input-with-parameter-entity.xml");
      File style = new File("payloads-new/ok-stylesheet.xsl");
      File output = new File("payloads/input-with-stylesheet/output.csv");

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);

      Transformer transformer = tf.newTransformer(xsltSource);

      Result target = new StreamResult(output);
      transformer.transform(source, target);
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

  public static void parseSchemaDtd(TransformerFactory tf) {
    System.out.println("    Parse Schema Dtd: n/a");
  }

  public static void parseSchemaImport(TransformerFactory tf) {
    System.out.println("    Parse Schema Import: n/a");
  }

  public static void parseSchemaInclude(TransformerFactory tf) {
    System.out.println("    Parse Schema Include: n/a");
  }

  public static void parseStylesheetDtd(TransformerFactory tf) {
    try {
      File input = new File("payloads-new/ok-input.xml");
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-dtd.xsl");
      File output = new File("payloads/input-with-stylesheet/output.csv");

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);

      Transformer transformer = tf.newTransformer(xsltSource);

      Result target = new StreamResult(output);
      transformer.transform(source, target);
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

  public static void parseStylesheetDocument(TransformerFactory tf) {
    try {
      File input = new File("payloads-new/ok-input.xml");
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-document.xsl");
      File output = new File("payloads/input-with-stylesheet/output.csv");

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);

      Transformer transformer = tf.newTransformer(xsltSource);

      Result target = new StreamResult(output);
      transformer.transform(source, target);
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

  public static void parseStylesheetImport(TransformerFactory tf) {
    try {
      File input = new File("payloads-new/ok-input.xml");
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-import.xsl");
      File output = new File("payloads/input-with-stylesheet/output.csv");

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);

      Transformer transformer = tf.newTransformer(xsltSource);

      Result target = new StreamResult(output);
      transformer.transform(source, target);
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

  public static void parseStylesheetInclude(TransformerFactory tf) {
    try {
      File input = new File("payloads-new/ok-input.xml");
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-include.xsl");
      File output = new File("payloads/input-with-stylesheet/output.csv");

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);

      Transformer transformer = tf.newTransformer(xsltSource);

      Result target = new StreamResult(output);
      transformer.transform(source, target);
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
