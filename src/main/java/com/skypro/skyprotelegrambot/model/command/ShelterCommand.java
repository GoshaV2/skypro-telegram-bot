package com.skypro.skyprotelegrambot.model.command;

public enum ShelterCommand {
    CHOOSE_SHELTER("/chooseShelter/", "\\/chooseShelter\\/\\d*"),
    GET_INFO_MENU("/getInfoMenu"),
    SEND_REPORT("/sendReport");


    private final String startPath;
    private final String startPathPattern;

    ShelterCommand(String startPath) {
        this.startPath = startPath;
        this.startPathPattern = null;
    }

    ShelterCommand(String startPath, String startPathPattern) {
        this.startPath = startPath;
        this.startPathPattern = startPathPattern;
    }

    public String getStartPath() {
        return startPath;
    }

    public String getStartPathPattern() {
        if (startPathPattern == null) {
            throw new UnsupportedOperationException();
        }
        return startPathPattern;
    }
}
