package com.dqops.cli.terminal;

import org.apache.commons.lang3.NotImplementedException;
import org.jline.keymap.KeyMap;
import org.jline.reader.*;
import org.jline.terminal.MouseEvent;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Line reader wrapped that automatically responds to the prompts in terminal based on prepared queue of respond messages.
 */
public class LineReaderWrapper implements LineReader {

    private Queue<String> stringPromptPushQueue = new LinkedList<>();

    public void addStringPromptPushQueueElement(String element){
        stringPromptPushQueue.add(element);
    }

    @Override
    public Map<String, KeyMap<Binding>> defaultKeyMaps() {
        throw new NotImplementedException();
    }

    @Override
    public String readLine() throws UserInterruptException, EndOfFileException {
        throw new NotImplementedException();
    }

    @Override
    public String readLine(Character character) throws UserInterruptException, EndOfFileException {
        throw new NotImplementedException();
    }

    @Override
    public String readLine(String s) throws UserInterruptException, EndOfFileException {
        return stringPromptPushQueue.poll();
    }

    @Override
    public String readLine(String s, Character character) throws UserInterruptException, EndOfFileException {
        throw new NotImplementedException();
    }

    @Override
    public String readLine(String s, Character character, String s1) throws UserInterruptException, EndOfFileException {
        throw new NotImplementedException();
    }

    @Override
    public String readLine(String s, String s1, Character character, String s2) throws UserInterruptException, EndOfFileException {
        throw new NotImplementedException();
    }

    @Override
    public String readLine(String s, String s1, MaskingCallback maskingCallback, String s2) throws UserInterruptException, EndOfFileException {
        throw new NotImplementedException();
    }

    @Override
    public void printAbove(String s) {

    }

    @Override
    public void printAbove(AttributedString attributedString) {

    }

    @Override
    public boolean isReading() {
        return false;
    }

    @Override
    public LineReader variable(String s, Object o) {
        throw new NotImplementedException();
    }

    @Override
    public LineReader option(Option option, boolean b) {
        throw new NotImplementedException();
    }

    @Override
    public void callWidget(String s) {

    }

    @Override
    public Map<String, Object> getVariables() {
        throw new NotImplementedException();
    }

    @Override
    public Object getVariable(String s) {
        throw new NotImplementedException();
    }

    @Override
    public void setVariable(String s, Object o) {

    }

    @Override
    public boolean isSet(Option option) {
        return false;
    }

    @Override
    public void setOpt(Option option) {

    }

    @Override
    public void unsetOpt(Option option) {

    }

    @Override
    public Terminal getTerminal() {
        throw new NotImplementedException();
    }

    @Override
    public Map<String, Widget> getWidgets() {
        throw new NotImplementedException();
    }

    @Override
    public Map<String, Widget> getBuiltinWidgets() {
        throw new NotImplementedException();
    }

    @Override
    public Buffer getBuffer() {
        throw new NotImplementedException();
    }

    @Override
    public String getAppName() {
        throw new NotImplementedException();
    }

    @Override
    public void runMacro(String s) {

    }

    @Override
    public MouseEvent readMouseEvent() {
        throw new NotImplementedException();
    }

    @Override
    public History getHistory() {
        throw new NotImplementedException();
    }

    @Override
    public Parser getParser() {
        throw new NotImplementedException();
    }

    @Override
    public Highlighter getHighlighter() {
        throw new NotImplementedException();
    }

    @Override
    public Expander getExpander() {
        throw new NotImplementedException();
    }

    @Override
    public Map<String, KeyMap<Binding>> getKeyMaps() {
        throw new NotImplementedException();
    }

    @Override
    public String getKeyMap() {
        throw new NotImplementedException();
    }

    @Override
    public boolean setKeyMap(String s) {
        return false;
    }

    @Override
    public KeyMap<Binding> getKeys() {
        throw new NotImplementedException();
    }

    @Override
    public ParsedLine getParsedLine() {
        throw new NotImplementedException();
    }

    @Override
    public String getSearchTerm() {
        throw new NotImplementedException();
    }

    @Override
    public RegionType getRegionActive() {
        throw new NotImplementedException();
    }

    @Override
    public int getRegionMark() {
        return 0;
    }

    @Override
    public void addCommandsInBuffer(Collection<String> collection) {

    }

    @Override
    public void editAndAddInBuffer(File file) throws Exception {

    }

    @Override
    public String getLastBinding() {
        throw new NotImplementedException();
    }

    @Override
    public String getTailTip() {
        throw new NotImplementedException();
    }

    @Override
    public void setTailTip(String s) {

    }

    @Override
    public void setAutosuggestion(SuggestionType suggestionType) {

    }

    @Override
    public SuggestionType getAutosuggestion() {
        throw new NotImplementedException();
    }
}
