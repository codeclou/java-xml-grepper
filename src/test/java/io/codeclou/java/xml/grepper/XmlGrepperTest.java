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

import java.io.File;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class XmlGrepperTest {

    @Rule
    public PreventExitTestRule preventExit = new PreventExitTestRule();

    private File getTestFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
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
        String[] args = {"-f=src/test/resources/pom-1-test.xml", "-x=foo"};
        XmlGrepper grepper = new XmlGrepper();
        grepper.run(args);
        Boolean hasCmdLineParameterErrors = (Boolean) Whitebox.getInternalState(grepper, "hasCmdLineParameterErrors");
        assertFalse(hasCmdLineParameterErrors);
        Boolean hasFileNotFoundErrors = (Boolean) Whitebox.getInternalState(grepper, "hasFileNotFoundErrors");
        assertFalse(hasFileNotFoundErrors);
    }

    @Test()
    public void testRunValidInputWithInValidXpath() throws Exception {
        String[] args = {"-f=src/test/resources/pom-1-test.xml", "-x=???S?Sßß"};
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
