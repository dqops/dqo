package com.dqops.metadata.sources.fileformat;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest
public class TableOptionsFormatterTest extends BaseTest {

    @Test
    void formatFilePaths_forOneFile_formatsIt() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv'
                )""",
                output);
    }

    @Test
    void formatFilePaths_forTwoFiles_formatsIt() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv", "file_two.csv"));

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  ['file_one.csv', 'file_two.csv']
                )""",
                output);
    }

    @Test
    void formatValueWhenSet_booleanValueIsTrue_formatsIt() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        tableOptionsFormatter.formatValueWhenSet("bool_field", true);

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv',
                  bool_field = true
                )""", output);
    }

    @Test
    void formatValueWhenSet_longValueIsGiven_formatsIt() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        tableOptionsFormatter.formatValueWhenSet("long_field", 3L);

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv',
                  long_field = 3
                )""", output);
    }

    @Test
    void formatBooleanWhenSet_valueIsNotSet_boolIsNotAdded() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        tableOptionsFormatter.formatValueWhenSet("bool_field", null);

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv'
                )""", output);
    }

    @Test
    void formatStringWhenSet_valueIsGiven_formatsIt() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        tableOptionsFormatter.formatStringWhenSet("string_field", "text value");

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv',
                  string_field = 'text value'
                )""",
                output);
    }

    @Test
    void formatMapWhenSet_mapIsGiven_formatsIt() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();

        linkedHashMap.put("NAME", "VARCHAR");
        linkedHashMap.put("VALUE", "INTEGER");

        tableOptionsFormatter.formatMapWhenSet("columns", linkedHashMap);

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv',
                  columns = {
                    'NAME': 'VARCHAR',
                    'VALUE': 'INTEGER'
                  }
                )""",
                output);
    }

    @Test
    void formatMapWhenSet_columnsNonAlphabetically_keepsOrderOfColumns() {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("csv_file",
                List.of("file_one.csv"));

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();

        linkedHashMap.put("z", "zzz");
        linkedHashMap.put("a", "aaa");

        tableOptionsFormatter.formatMapWhenSet("columns", linkedHashMap);

        String output = tableOptionsFormatter.toString();

        Assertions.assertEquals("""
                csv_file(
                  'file_one.csv',
                  columns = {
                    'z': 'zzz',
                    'a': 'aaa'
                  }
                )""",
                output);
    }

}