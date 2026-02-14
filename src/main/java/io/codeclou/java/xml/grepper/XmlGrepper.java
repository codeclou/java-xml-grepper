/*
 * MIT License
 *
 * Copyright (c) 2017 Bernhard Grünewaldt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.codeclou.java.xml.grepper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XmlGrepper {

    private CommandLineParser parser = new DefaultParser();
    protected Options options = new Options();
    protected Boolean hasCmdLineParameterErrors = false;
    protected Boolean hasFileNotFoundErrors = false;

    protected String parseXml(String xpathExpression, File xmlFile) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        FileInputStream fileIS = new FileInputStream(xmlFile);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        return (String) xPath.compile(xpathExpression).evaluate(xmlDocument, XPathConstants.STRING);
    }

    protected boolean run(String[] args) throws Exception {
        //
        // ONLY OUTPUT STUFF ON ERROR!
        //
        options.addOption("f", "file", true, "the XML file you want to grep");
        options.addOption("x", "xpath", true, "the xpath expression");
        CommandLine cmd = this.parser.parse(options, args);
        if (!cmd.hasOption("file")) {
            System.out.println("\033[31;1mError >> Please specify file with -f\033[0m");
            hasCmdLineParameterErrors = true;
        }
        if (!cmd.hasOption("xpath")) {
            System.out.println("\033[31;1mError >> Please specify xpath expression with -x\033[0m");
            hasCmdLineParameterErrors = true;
        }
        if (!hasCmdLineParameterErrors) {
            File inputFile = new File(cmd.getOptionValue("file"));
            if (!inputFile.isFile()) {
                hasFileNotFoundErrors = true;
                System.out.println("\033[31;1mError >> Input file not readable\033[0m");
                System.out.println(inputFile.getAbsolutePath());
            }
            if (!hasFileNotFoundErrors) {
                try {
                    String result = this.parseXml(cmd.getOptionValue("xpath"), inputFile);
                    System.out.println(result);
                } catch (Exception e) {
                    System.out.println("\033[31;1mError >> Parse Exception\033[0m");
                    System.out.println(e.getMessage());
                    return true;
                }
            }
        }
        return false;
    }
}
