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
package org.alfresco.po.rm.fileplan;

import java.util.concurrent.TimeUnit;

import org.alfresco.po.rm.RmFolderRulesPage;
import org.alfresco.po.rm.fileplan.filter.FilePlanFilter;
import org.alfresco.po.rm.fileplan.toolbar.CreateNewRecordCategoryDialog;
import org.alfresco.po.rm.fileplan.toolbar.CreateNewRecordFolderDialog;
import org.alfresco.po.rm.util.RmPageObjectUtils;
import org.alfresco.po.share.site.document.DocumentLibraryPage;
import org.alfresco.po.share.site.document.FileDirectoryInfo;
import org.alfresco.webdrone.RenderTime;
import org.alfresco.webdrone.WebDrone;
import org.alfresco.webdrone.WebDroneUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Records management file plan page, based on the document library
 * page with specific action and operation related to file plan.
 *
 * @author Michael Suzuki
 * @author Tuna Aksoy
 * @version 1.7.1
 */
public class FilePlanPage extends DocumentLibraryPage
{
    protected static final By MANAGE_RULES_BTN = By.cssSelector("button[id$='_default-manageRules-button-button']");
    protected static final By NEW_CATEGORY_BTN = By.cssSelector("button[id$='default-newCategory-button-button']");
    protected static final By NEW_FOLDER_BTN = By.cssSelector("button[id$='default-newFolder-button-button']");
    protected static final By NEW_DECLARE_RECORD_BTN = By.cssSelector("button[id$='default-declareRecord-button-button']");
    protected static final By NEW_UNFILED_RECORDS_FOLDER_BTN = By.cssSelector("button[id$='default-newUnfiledRecordsFolder-button-button']");
    protected static final By RM_ADD_META_DATA_LINK = By.cssSelector("div#onActionAddRecordMetadata a");
    protected static final By RECORD = By.cssSelector("tbody.yui-dt-data > tr");
    protected static final By DESCRIPTION = By.cssSelector("div[id$='_default-description'] div");
    protected static final By FILEPLAN = By.id("template_x002e_tree_x002e_documentlibrary_x0023_default");
    protected boolean inFilePlanRoot;
    protected boolean inRecordCategory;
    protected boolean inRecordFolder;

    /**
     * Indicates that the user is in the file plan root
     *
     * @param inFilePlanRoot <code>true</code> if the user is in the file plan root <code>false</code> otherwise
     */
    public void setInFilePlanRoot(boolean inFilePlanRoot)
    {
        this.inFilePlanRoot = inFilePlanRoot;
    }

    /**
     * Indicates that the user is in a record category
     *
     * @param inRecordCategory <code>true</code> if the user is in a record category <code>false</code> otherwise
     */
    public void setInRecordCategory(boolean inRecordCategory)
    {
        this.inRecordCategory = inRecordCategory;
    }

    /**
     * Indicates that the user is in a record folder
     *
     * @param inRecordFolder <code>true</code> if the user is in a record folder <code>false</code> otherwise
     */
    public void setInRecordFolder(boolean inRecordFolder)
    {
        this.inRecordFolder = inRecordFolder;
    }

    /**
     * Constructor.
     *
     * @param drone {@link WebDrone}
     */
    public FilePlanPage(WebDrone drone)
    {
        super(drone);
    }

    /**
     * @see org.alfresco.webdrone.Render#render(org.alfresco.webdrone.RenderTime)
     */
    @SuppressWarnings("unchecked")
    @Override
    public FilePlanPage render(RenderTime timer)
    {
        WebDroneUtil.checkMandotaryParam("timer", timer);

        return render(timer, null);
    }

    /**
     * Renders the page and waits until the element with the
     * expected name has has been displayed.
     *
     * @param expectedName {@link String} The name of the expected element
     * @return {@link FilePlanPage} The file plan page displaying the expected element
     */
    public FilePlanPage render(String expectedName)
    {
        // "expectedName" can be blank so no check required

        RenderTime timer = new RenderTime(maxPageLoadingTime);
        return render(timer, expectedName);
    }

    /**
     * Renders the page and waits until the element with the
     * expected name has has been displayed.
     *
     * timer {@link RenderTime} time to wait
     * @param expectedName {@link String} The name of the expected element
     * @return {@link FilePlanPage} The file plan page displaying the expected element
     */
    private FilePlanPage render(RenderTime timer, String expectedName)
    {
        WebDroneUtil.checkMandotaryParam("timer", timer);
        // "expectedName" can be blank

        boolean found = false;
        while (true)
        {
            setViewType(getNavigation().getViewType());
            timer.start();
            try
            {
                if (RmPageObjectUtils.isDisplayed(drone, FILEPLAN) && !isJSMessageDisplayed())
                {
                    if (StringUtils.isNotBlank(expectedName) && !found)
                    {
                        for (FileDirectoryInfo fileDirectoryInfo : getFiles())
                        {
                            if (fileDirectoryInfo.getName().contains(expectedName))
                            {
                                found = true;
                                break;
                            }
                        }
                        if (!found)
                        {
                            continue;
                        }
                    }
                    long timeOut = TimeUnit.SECONDS.convert(maxPageLoadingTime, TimeUnit.MILLISECONDS);
                    if (inFilePlanRoot)
                    {
                        if (!isCreateNewCategoryDisplayed())
                        {
                            continue;
                        }
                        else
                        {
                            drone.waitUntilElementClickable(NEW_CATEGORY_BTN, timeOut);
                        }
                    }
                    if (inRecordCategory)
                    {
                        if (!(isCreateNewCategoryDisplayed() && isCreateNewFolderDisplayed()))
                        {
                            continue;
                        }
                        else
                        {
                            drone.waitUntilElementClickable(NEW_CATEGORY_BTN, timeOut);
                            drone.waitUntilElementClickable(NEW_FOLDER_BTN, timeOut);
                        }
                    }
                    if (inRecordFolder)
                    {
                        if (!isDeclaredRecordButtonDisplayed())
                        {
                            continue;
                        }
                        else
                        {
                            drone.waitUntilElementClickable(NEW_DECLARE_RECORD_BTN, timeOut);
                        }
                    }
                    break;
                }
            }
            catch (NoSuchElementException e)
            {
            }
            finally
            {
                timer.end();
                setInFilePlanRoot(false);
                setInRecordCategory(false);
                setInRecordFolder(false);
            }
        }
        return this;
    }

    /**
     * @see org.alfresco.webdrone.Render#render(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public FilePlanPage render(long time)
    {
        RenderTime timer = new RenderTime(time);
        return render(timer);
    }

    /**
     * @see org.alfresco.webdrone.Render#render()
     */
    @SuppressWarnings("unchecked")
    @Override
    public FilePlanPage render()
    {
        RenderTime timer = new RenderTime(maxPageLoadingTime);
        return render(timer);
    }

    /**
     * Checks visibility of create new category button
     * on the file plan page header.
     *
     * @return <code>true</code> if visible <code>false</code> otherwise
     */
    public boolean isCreateNewCategoryDisplayed()
    {
        return RmPageObjectUtils.isDisplayed(drone, NEW_CATEGORY_BTN);
    }

    /**
     * Action mimicking select click on new category button.
     *
     * @return {@link CreateNewRecordCategoryDialog} Returns the create category form dialog
     */
    public CreateNewRecordCategoryDialog selectCreateNewCategory()
    {
        RmPageObjectUtils.select(drone, NEW_CATEGORY_BTN);
        return new CreateNewRecordCategoryDialog(drone);
    }

    /**
     * Checks visibility of create new folder button
     * on the file plan page header.
     *
     * @return <code>true</code> if visible <code>false</code> otherwise
     */
    public boolean isCreateNewFolderDisplayed()
    {
        return RmPageObjectUtils.isDisplayed(drone, NEW_FOLDER_BTN);
    }

    /**
     * Action mimicking select click on new folder button.
     *
     * @return {@link CreateNewRecordFolderDialog} Returns the create folder form dialog
     */
    public CreateNewRecordFolderDialog selectCreateNewFolder()
    {
        RmPageObjectUtils.select(drone, NEW_FOLDER_BTN);
        return new CreateNewRecordFolderDialog(drone);
    }

    /**
     * Checks visibility of the undeclared records button on the unfiled records container toolbar.
     *
     * @return <code>true</code> if visible <code>false</code> otherwise
     */
    public boolean isDeclaredRecordButtonDisplayed()
    {
        return RmPageObjectUtils.isDisplayed(drone, NEW_DECLARE_RECORD_BTN);
    }

    /**
     * Verify if records exists.
     *
     * @return <code>true</code> if visible <code>false</code> otherwise
     */
    public boolean hasRecords()
    {
        return RmPageObjectUtils.isDisplayed(drone, RECORD);
    }

    /**
     * Verify if header sub navigation has manage rules button visible.
     *
     * @return <code>true</code> if visible <code>false</code> otherwise
     */
    public boolean isManageRulesDisplayed()
    {
        return RmPageObjectUtils.isDisplayed(drone, MANAGE_RULES_BTN);
    }

    /**
     * Action of click on manage rules button.
     *
     * @return {@link RmFolderRulesPage} page response
     */
    public RmFolderRulesPage selectManageRules()
    {
        RmPageObjectUtils.select(drone, MANAGE_RULES_BTN);
        return new RmFolderRulesPage(drone);
    }

    /**
     * Get html element on the side of the page with file plan sub
     * navigation also known as filters.
     *
     * @return {@link FilePlanFilter} side element filter
     */
    public FilePlanFilter getFilePlanFilter()
    {
        return new FilePlanFilter(drone);
    }

    /**
     * Selects the title of the category link.
     *
     * @param title String category title
     * @return FilePlanPage page response object
     */
    public FilePlanPage selectCategory(final String title, final long timeout)
    {
        selectEntry(title, timeout).click();
        return new FilePlanPage(drone);
    }

    /**
     * Selects the title of the folder link.
     *
     * @param title String folder title
     * @return FilePlanPage page response object
     */
    public FilePlanPage selectFolder(final String title, final long timeout)
    {
        selectEntry(title, timeout).click();
        return new FilePlanPage(drone);
    }

    /**
     * Selects an entry regardless of type (file or folder)
     * @return
     */
    protected WebElement selectEntry(final String title, final long timeout)
    {
        if(title == null || title.isEmpty()) throw new IllegalArgumentException("Title is required");
        String search = String.format("//h3/span/a[text()='%s']",title);
        return drone.findAndWait(By.xpath(search), timeout);
    }

    // FIXME: This method will be deleted after the original method has been fixed
    @Override
    public FileDirectoryInfo getFileDirectoryInfo(String title)
    {
        FileDirectoryInfo result = null;
        for (FileDirectoryInfo fileDirectoryInfo : getFiles())
        {
            if (fileDirectoryInfo.getName().equals(title))
            {
                result = fileDirectoryInfo;
                break;
            }
        }
        return result;
    }
}