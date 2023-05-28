package com.skypro.skyprotelegrambot.model.command;

public enum AnswerCommand {
    CHOOSE_ANSWER("/chooseAnswer/", "\\/chooseAnswer\\/\\d*");

    private final String startPath;
    private final String startPathPattern;

    AnswerCommand(String startPath, String startPathPattern) {
        this.startPath = startPath;
        this.startPathPattern = startPathPattern;
    }

    public String getStartPath() {
        return startPath;
    }

    public String getStartPathPattern() {
        return startPathPattern;
    }
}
