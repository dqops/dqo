package com.dqops.cli.terminal;

import com.dqops.BaseTest;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.table.TableModel;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;
import tech.tablesaw.columns.Column;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootTest
class TerminalTableWriterImplTest extends BaseTest {

    private TerminalWriterImplWrapper terminalWriter;
    private TerminalTableWriterImpl sut;
    private LineReaderWrapper lineReader;

    @BeforeEach
    public void setUp() {
        TerminalFactory terminalFactory = BeanFactoryObjectMother.getBeanFactory().getBean(TerminalFactory.class);
        terminalWriter = new TerminalWriterImplWrapper(terminalFactory.getWriter());
        lineReader = new LineReaderWrapper();
        TerminalReaderImpl terminalReader = new TerminalReaderImpl(terminalFactory.getWriter(), lineReader);
        FileWriter fileWriter = BeanFactoryObjectMother.getBeanFactory().getBean(FileWriter.class);
        sut = new TerminalTableWriterImpl(terminalWriter, terminalReader, fileWriter);
        terminalWriter.setTerminalWidth(50);
        terminalWriter.setTerminalHeight(10);
    }

    @Test
    void writeTable_oneRowWithSetNoHeader_returnsOneLine(){
        Column<String> col1 = TextColumn.create("schema name header");
        col1.append("value_1");

        TableModel tableModel = new RowSelectionTableModel(Table.create(col1));

        sut.writeTable(tableModel, false, true);

        String trimmedLines = trimEachRow(terminalWriter.getWrittenText());

        Assert.assertEquals("""
                    [1]  value_1""", trimmedLines);
    }

    @Test
    void writeTable_justHeaderInTable_returnsNothing(){
        Column<String> col1 = TextColumn.create("header");

        TableModel tableModel = new TablesawDatasetTableModel(Table.create(col1));

        sut.writeTable(tableModel, false);

        String trimmedLines = trimEachRow(terminalWriter.getWrittenText());

        Assert.assertEquals("", trimmedLines);
    }

    @Test
    void writeTable_twoRowsWithSetNoHeader_returnsTwoLines(){
        Column<String> col1 = TextColumn.create("schema name");
        col1.append("value_1");
        col1.append("value_2");

        TableModel tableModel = new RowSelectionTableModel(Table.create(col1));

        sut.writeTable(tableModel, false);

        String trimmedLines = trimEachRow(terminalWriter.getWrittenText());

        Assert.assertEquals("""
                    [1]  value_1
                    [2]  value_2""", trimmedLines);
    }

    @Test   // User prompt will appear once, just before the last element
    void writeTable_rowsCountSameAsTerminalHeightWhichIs10_returns12Lines(){
        Column<String> col1 = TextColumn.create("header");
        for (int i = 1; i <= terminalWriter.getTerminalHeight(); i++) {
            col1.append("value_" + i);
        }

        TablesawDatasetTableModel tableModel = new TablesawDatasetTableModel(Table.create(col1));
        lineReader.addStringPromptPushQueueElement("y");

        sut.writeTable(tableModel, false);

        String trimmedLines = trimEachRow(terminalWriter.getWrittenText());

        Assert.assertEquals(12, trimmedLines.split("\n").length);
        Assert.assertEquals("""
                header
                value_1
                value_2
                value_3
                value_4
                value_5
                value_6
                value_7
                value_8
                header
                value_9
                value_10""", trimmedLines);
    }

    @Test   // User prompt will appear once, just before the last element
    void writeTable_10RowsWithSetNoHeader_returns10Lines(){
        Column<String> col1 = TextColumn.create("schema name");

        for (int i = 1; i <= terminalWriter.getTerminalHeight(); i++) {
            col1.append("value_" + i);
        }

        TableModel tableModel = new RowSelectionTableModel(Table.create(col1));

        lineReader.addStringPromptPushQueueElement("y");

        sut.writeTable(tableModel, false, true);

        String trimmedLines = trimEachRow(terminalWriter.getWrittenText());

        Assert.assertEquals("""
                [ 1]  value_1
                [ 2]  value_2
                [ 3]  value_3
                [ 4]  value_4
                [ 5]  value_5
                [ 6]  value_6
                [ 7]  value_7
                [ 8]  value_8
                [ 9]  value_9
                [10]  value_10""", trimmedLines);
    }

    @Test
    void writeTable_50SchemasWhichPromptsUserTwiceWithSetNoHeader_returns25Lines(){
        Column<String> col1 = TextColumn.create("schema name");

        for (int i = 1; i <= 25; i++) {
            col1.append("value_" + i);
        }

        TableModel tableModel = new RowSelectionTableModel(Table.create(col1));

        lineReader.addStringPromptPushQueueElement("y");
        lineReader.addStringPromptPushQueueElement("y");

        sut.writeTable(tableModel, false, true);

        String trimmedLines = trimEachRow(terminalWriter.getWrittenText());

        Assert.assertEquals("""
                [ 1]  value_1
                [ 2]  value_2
                [ 3]  value_3
                [ 4]  value_4
                [ 5]  value_5
                [ 6]  value_6
                [ 7]  value_7
                [ 8]  value_8
                [ 9]  value_9
                [10]  value_10
                [11]  value_11
                [12]  value_12
                [13]  value_13
                [14]  value_14
                [15]  value_15
                [16]  value_16
                [17]  value_17
                [18]  value_18
                [19]  value_19
                [20]  value_20
                [21]  value_21
                [22]  value_22
                [23]  value_23
                [24]  value_24
                [25]  value_25""", trimmedLines);
    }

    private String trimEachRow(String multilineText){
        String trimmedLines = Arrays.stream(multilineText.replace(" ", " ")
                .split("\n")).map(s -> s.trim()).collect(Collectors.joining("\n"));
        return trimmedLines;
    }


}