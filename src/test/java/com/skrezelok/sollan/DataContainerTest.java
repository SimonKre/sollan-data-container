package com.skrezelok.sollan;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class DataContainerTest {

    static Path csvPath = Paths.get("src/test/data/csvlist.csv");
    static DataProvider dataProvider;
    static DataContainer <Integer, String> testContainer;

    @BeforeClass
    public static void onlyOnce() throws Exception {
        dataProvider = new CSVDataProvider(csvPath);
    }

    @Before
    public void setUp() throws Exception {
        testContainer = new DataContainer<>();
        systemOutRule.clearLog();
    }

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void load() {
        assertNull(testContainer.findById(1));

        testContainer.load(dataProvider);
        assertEquals("France, Paris", testContainer.findById(1).toString());
    }

    @Test
    public void add() {
        assertNull(testContainer.findById(1));

        testContainer.add(1, "Test Data");
        testContainer.add(1, "Test Data 2");

        assertEquals("Test Data", testContainer.findById(1).toString());
    }

    @Test
    public void update() {
        testContainer.add(1, "Test Data");

        testContainer.update(1, "Update Test");
        assertEquals("Update Test", testContainer.findById(1).toString());
    }

    @Test
    public void findById() {
        testContainer.add(1, "Test Data");

        assertEquals("Test Data", testContainer.findById(1).toString());
        assertNull(testContainer.findById(12));
        assertNull(testContainer.findById(-1));
    }

    @Test
    public void remove() {
        testContainer.add(1, "Test Data");
        testContainer.add(1, "Test Data 2");

        testContainer.remove(1);
        assertNull(testContainer.findById(1));
    }

    @Test
    public void printAll() {
        testContainer.printAll();
        assertEquals("[]\n", systemOutRule.getLog());

        systemOutRule.clearLog();
        testContainer.load(dataProvider);
        testContainer.printAll();
        assertEquals("[{id=1, data=France, Paris}, {id=2, data=Poland, Warsaw}]\n", systemOutRule.getLog());
    }

    @Test
    public void print() {
        testContainer.load(dataProvider);
        testContainer.print(0, 1);
        assertEquals("[{id=1, data=France, Paris}]\n", systemOutRule.getLog());

        systemOutRule.clearLog();
        testContainer.print(0, 2);
        assertEquals("[{id=1, data=France, Paris}, {id=2, data=Poland, Warsaw}]\n", systemOutRule.getLog());
    }

    @Test
    public void clear() {
        testContainer.load(dataProvider);
        assertNotNull(testContainer.findById(1));

        testContainer.clear();
        assertNull(testContainer.findById(1));
    }

    @Test
    public void get() {
        testContainer.load(dataProvider);
        assertEquals(0, testContainer.get(0,0).size());
        assertEquals(0, testContainer.get(1,1).size());
        assertEquals(1, testContainer.get(0,1).size());
        assertEquals(2, testContainer.get(0,2).size());
        assertEquals(0, testContainer.get(4,-2).size());
    }

    @Test
    public void filter() {
        testContainer.load(dataProvider);
        assertEquals("[France, Paris]", testContainer.filter(data -> data.contains("France")).toString());

        assertEquals("[Poland, Warsaw]", testContainer.filter(data -> data.contains("Warsaw")).toString());
    }
}