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
package org.alfresco.repo.action.parameter;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the DateParameterProcessor
 *
 * @author Mark Hibbins
 */
public class DateParameterProcessorTest
{

    private DateParameterProcessor dateParameterProcessor;

    @Before
    public void setUp() throws Exception
    {
        this.dateParameterProcessor = new DateParameterProcessor();
        this.dateParameterProcessor.setName("date");
    }

    @Test
    public void testGetSubstitutionSuggestions_All_01()
    {
        List<String> suggestions = this.dateParameterProcessor.getSubstitutionSuggestions("date");
        assertTrue(suggestions.contains("date.day.short"));
        assertTrue(suggestions.contains("date.day"));
        assertTrue(suggestions.contains("date.day.long"));
        assertTrue(suggestions.contains("date.day.number"));
        assertTrue(suggestions.contains("date.day.year"));
        assertTrue(suggestions.contains("date.month.short"));
        assertTrue(suggestions.contains("date.month"));
        assertTrue(suggestions.contains("date.month.long"));
        assertTrue(suggestions.contains("date.month.number"));
        assertTrue(suggestions.contains("date.year.short"));
        assertTrue(suggestions.contains("date.year"));
        assertTrue(suggestions.contains("date.year.long"));
        assertTrue(suggestions.contains("date.year.week"));
        assertEquals(13, suggestions.size());
    }

    @Test
    public void testGetSubstitutionSuggestions_All_02()
    {
        List<String> suggestions = this.dateParameterProcessor.getSubstitutionSuggestions("dat");
        assertTrue(suggestions.contains("date.day.short"));
        assertTrue(suggestions.contains("date.day"));
        assertTrue(suggestions.contains("date.day.long"));
        assertTrue(suggestions.contains("date.day.number"));
        assertTrue(suggestions.contains("date.day.year"));
        assertTrue(suggestions.contains("date.month.short"));
        assertTrue(suggestions.contains("date.month"));
        assertTrue(suggestions.contains("date.month.long"));
        assertTrue(suggestions.contains("date.month.number"));
        assertTrue(suggestions.contains("date.year.short"));
        assertTrue(suggestions.contains("date.year"));
        assertTrue(suggestions.contains("date.year.long"));
        assertTrue(suggestions.contains("date.year.week"));
        assertEquals(13, suggestions.size());
    }

    @Test
    public void testGetSubstitutionSuggestions_All_03()
    {
        List<String> suggestions = this.dateParameterProcessor.getSubstitutionSuggestions("at");
        assertTrue(suggestions.contains("date.day.short"));
        assertTrue(suggestions.contains("date.day"));
        assertTrue(suggestions.contains("date.day.long"));
        assertTrue(suggestions.contains("date.day.number"));
        assertTrue(suggestions.contains("date.day.year"));
        assertTrue(suggestions.contains("date.month.short"));
        assertTrue(suggestions.contains("date.month"));
        assertTrue(suggestions.contains("date.month.long"));
        assertTrue(suggestions.contains("date.month.number"));
        assertTrue(suggestions.contains("date.year.short"));
        assertTrue(suggestions.contains("date.year"));
        assertTrue(suggestions.contains("date.year.long"));
        assertTrue(suggestions.contains("date.year.week"));
        assertEquals(13, suggestions.size());
    }

    @Test
    public void testGetSubstitutionSuggestions_Partial_01()
    {
        List<String> suggestions = this.dateParameterProcessor.getSubstitutionSuggestions("ay");
        assertTrue(suggestions.contains("date.day.short"));
        assertTrue(suggestions.contains("date.day"));
        assertTrue(suggestions.contains("date.day.long"));
        assertTrue(suggestions.contains("date.day.number"));
        assertTrue(suggestions.contains("date.day.year"));
        assertEquals(5, suggestions.size());
    }

    @Test
    public void testGetSubstitutionSuggestions_Partial_02()
    {
        List<String> suggestions = this.dateParameterProcessor.getSubstitutionSuggestions("on");
        assertTrue(suggestions.contains("date.day.long"));
        assertTrue(suggestions.contains("date.month.short"));
        assertTrue(suggestions.contains("date.month"));
        assertTrue(suggestions.contains("date.month.long"));
        assertTrue(suggestions.contains("date.month.number"));
        assertTrue(suggestions.contains("date.year.long"));
        assertEquals(6, suggestions.size());
    }

}
