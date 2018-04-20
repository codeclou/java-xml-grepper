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

import org.junit.Rule;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


public class XmlGrepperTest {

    // NOTE: Bazel has its own GoogleSecurityManager that prevents System.exit()
    //@Rule
    //public PreventExitTestRule preventExit = new PreventExitTestRule();

    private File getTestFile(String filename) throws IOException {
        // Since Bazel creates a test.jar somewhere we need to extract the testfile
        // out of the jar and put it accessible on the Filesystem somewhere to e.g. /tmp/
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputstream = classLoader.getResource(filename).openStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputstream, writer, "utf-8");
        String fileContent = writer.toString();
        File testFileOutsideTestJar = File.createTempFile("xmlgrepper", ".dat");
        FileUtils.write(testFileOutsideTestJar, fileContent, "utf-8");
        return testFileOutsideTestJar;
    }

    @Test
    public void testParseXml() throws Exception {
        XmlGrepper grepper = new XmlGrepper();
        File pomXMl = getTestFile("pom-1-test.xml");
        assertEquals(grepper.parseXml("/project/version", pomXMl), "1.4.5");
    }




    @Test
    public void testRunInvalidInput1() throws Exception {
        String[] args = {};
        XmlGrepper grepper = new XmlGrepper();
        grepper.run(args);
        Boolean hasCmdLineParameterErrors = (Boolean) Whitebox.getInternalState(grepper, "hasCmdLineParameterErrors");
        assertTrue(hasCmdLineParameterErrors);
    }

    @Test
    public void testRunInvalidInput2() throws Exception {
        String[] args = {"-x=foo"};
        XmlGrepper grepper = new XmlGrepper();
        grepper.run(args);
        Boolean hasCmdLineParameterErrors = (Boolean) Whitebox.getInternalState(grepper, "hasCmdLineParameterErrors");
        assertTrue(hasCmdLineParameterErrors);
    }

    @Test
    public void testRunValidInputWithInvalidFolders() throws Exception {
        String[] args = {"-x=foo", "-f=?x/bar.xml"};
        XmlGrepper grepper = new XmlGrepper();
        grepper.run(args);
        Boolean hasFileNotFoundErrors = (Boolean) Whitebox.getInternalState(grepper, "hasFileNotFoundErrors");
        assertTrue(hasFileNotFoundErrors);
    }

    @Test
    public void testRunValidInputWithValidXpath() throws Exception {
        File pomXMl = getTestFile("pom-1-test.xml");
        String[] args = {"-f=" + pomXMl.getAbsolutePath(), "-x=foo"};
        XmlGrepper grepper = new XmlGrepper();
        grepper.run(args);
        Boolean hasCmdLineParameterErrors = (Boolean) Whitebox.getInternalState(grepper, "hasCmdLineParameterErrors");
        assertFalse(hasCmdLineParameterErrors);
        Boolean hasFileNotFoundErrors = (Boolean) Whitebox.getInternalState(grepper, "hasFileNotFoundErrors");
        assertFalse(hasFileNotFoundErrors);
    }

    @Test()
    public void testRunValidInputWithInValidXpath() throws Exception {
        File pomXMl = getTestFile("pom-1-test.xml");
        String[] args = {"-f=" + pomXMl.getAbsolutePath(), "-x=???S?Sßß"};
        XmlGrepper grepper = new XmlGrepper();
        try {
            grepper.run(args);
        } catch (SecurityException e) {
            // expected
        }
        Boolean hasCmdLineParameterErrors = (Boolean) Whitebox.getInternalState(grepper, "hasCmdLineParameterErrors");
        assertFalse(hasCmdLineParameterErrors);
        Boolean hasFileNotFoundErrors = (Boolean) Whitebox.getInternalState(grepper, "hasFileNotFoundErrors");
        assertFalse(hasFileNotFoundErrors);
    }

}
