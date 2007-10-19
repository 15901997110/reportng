// ============================================================================
//   Copyright 2006, 2007 Daniel W. Dyer
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
package org.uncommons.reportng.sample;

import org.testng.annotations.Test;
import org.testng.Reporter;

/**
 * Some successful tests to be included in the sample output.
 * @author Daniel Dyer
 */
public class SuccessfulTests
{
    @Test
    public void test1()
    {
        assert true;
    }


    @Test
    public void test2()
    {
        assert true;
    }


    @Test
    public void testWithOutput()
    {
        Reporter.log("Here is some output from a successful test.");
        assert true;
    }
}
