// ============================================================================
//   Copyright 2006, 2007, 2008 Daniel W. Dyer
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// ============================================================================
package org.uncommons.reportng;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.testng.IReporter;
import java.io.File;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileFilter;

/**
 * @author Daniel Dyer
 */
public abstract class AbstractReporter implements IReporter
{
    private static final String ENCODING = "UTF-8";

    protected static final String TEMPLATE_EXTENSION = ".vm";

    private static final String META_KEY ="meta";
    private static final ReportMetadata META = new ReportMetadata();
    private static final String UTILS_KEY ="utils";
    private static final ReportNGUtils UTILS = new ReportNGUtils();


    protected AbstractReporter()
    {
        Velocity.setProperty("resource.loader", "classpath");
        Velocity.setProperty("classpath.resource.loader.class",
                             "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        try
        {
            Velocity.init();
        }
        catch (Exception ex)
        {
            throw new ReportNGException("Failed to initialise Velocity.", ex);
        }
    }


    /**
     * Helper method that creates a Velocity context and initialises it
     * with a reference to the ReportNG utils.
     */
    protected VelocityContext createContext()
    {
        VelocityContext context = new VelocityContext();
        context.put(META_KEY, META);
        context.put(UTILS_KEY, UTILS);
        return context;
    }


    /**
     * Generate the specified output file by merging the specified
     * Velocity template with the supplied context.
     */
    protected void generateFile(File file,
                                String template,
                                VelocityContext context) throws Exception
    {
        Writer writer = new BufferedWriter(new FileWriter(file));
        try
        {
            Velocity.mergeTemplate(template,
                                   ENCODING,
                                   context,
                                   writer);
            writer.flush();
        }
        finally
        {
            writer.close();
        }
    }


    /**
     * Deletes any empty directories under the output directory.  These
     * directories are created by TestNG for its own reports regardless
     * of whether those reports are generated.  If you are using the
     * default TestNG reports as well as ReportNG, these directories will
     * not be empty and will be retained.  Otherwise they will be removed.
     */
    protected void removeEmptyDirectories(File outputDirectory)
    {
        for (File file : outputDirectory.listFiles(new EmptyDirectoryFilter()))
        {
            file.delete();
        }
    }


    private static final class EmptyDirectoryFilter implements FileFilter
    {
        public boolean accept(File file)
        {
            return file.isDirectory() && file.listFiles().length == 0;
        }
    }
}
