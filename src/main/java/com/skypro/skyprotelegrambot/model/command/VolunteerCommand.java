package com.skypro.skyprotelegrambot.model.command;

public enum VolunteerCommand {
    SEND_VOLUNTEER_CONTACT("/sendVolunteerContact");
    private final String command;

    VolunteerCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
