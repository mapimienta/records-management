/*
 * Copyright (C) 2005-2013 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.po.rm.functional.copymovetoruletests;

import org.alfresco.po.share.util.FailedTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Test the record folder copy to rule within the unfiled records container
 *
 * @author Mark Hibbins
 * @version 2.2
 */
@Listeners(FailedTestListener.class)
public class UnfiledRecordFolderCopyToRuleIntTest extends AbstractUnfiledCopyMoveRuleIntTestBase
{
    private final static String RULE_TITLE = "Copy to test rule";
    private final static String TARGET_FOLDER_NAME = "target";
    private final static String TARGET_FOLDER_PATH = "/target";
    private final static String TEST_FOLDER_NAME = "testFolder";

    /**
     * Test the record folder copy to rule within the unfiled records container
     */
    @Test
    public void unfiledRecordsCopyToFolderTest()
    {
        navigateToUnfiledRecords();
        createUnfiledFolder(TARGET_FOLDER_NAME);
        createRule(RULE_TITLE, RuleType.COPY_TO, TARGET_FOLDER_PATH, true);
        navigateToUnfiledRecords();
        createUnfiledFolder(TEST_FOLDER_NAME);
        navigateToUnfiledRecords();
        Assert.assertEquals(2, unfiledRecordsContainer.getFiles().size());
        navigateToPath("/target");
        Assert.assertEquals(1, unfiledRecordsContainer.getFiles().size());
    }
}