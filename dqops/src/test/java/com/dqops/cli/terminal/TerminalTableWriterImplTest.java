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

    private TerminalWriterImplWrapper writerImplWrapper;
    private TerminalTableWriterImpl sut;

    @BeforeEach
    public void setUp() {
        FileWriter fileWriter = BeanFactoryObjectMother.getBeanFactory().getBean(FileWriter.class);
        TerminalFactory terminalFactory = BeanFactoryObjectMother.getBeanFactory().getBean(TerminalFactory.class);
        writerImplWrapper = new TerminalWriterImplWrapper(terminalFactory.getWriter());
        sut = new TerminalTableWriterImpl(writerImplWrapper, fileWriter);
    }

    @Test
    void writeTable_twoSchemaNames_returnsBoth(){
        Column<String> col1 = TextColumn.create("schema name");
        col1.append("value_1");
        col1.append("value_2");

        TableModel tableModel = new RowSelectionTableModel(Table.create(col1));

        sut.writeTable(tableModel, false);

        String writtenTerminalText = writerImplWrapper.getWrittenText();

        String trimmedLines = Arrays.stream(writtenTerminalText.replace(" ", " ")
                .split("\n")).map(s -> s.trim()).collect(Collectors.joining("\n"));

        Assert.assertEquals("""
                    [1]  value_1
                    [2]  value_2""", trimmedLines);
    }

    @Test
    void writeTable_oneSchemaName_returnsIt(){
        Column<String> col1 = TextColumn.create("schema name");
        col1.append("value_1");

        TableModel tableModel = new RowSelectionTableModel(Table.create(col1));

        sut.writeTable(tableModel, false);

        String writtenTerminalText = writerImplWrapper.getWrittenText();

        String trimmedLines = Arrays.stream(writtenTerminalText.replace(" ", " ")
                .split("\n")).map(s -> s.trim()).collect(Collectors.joining("\n"));

        Assert.assertEquals("""
                    [1]  value_1""", trimmedLines);
    }


}