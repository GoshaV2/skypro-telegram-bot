package com.skypro.skyprotelegrambot.service;

import java.io.IOException;
import java.nio.file.Path;

public interface FileServise {
    void saveFile(Path path, byte[] source) throws IOException;

    byte[] readFile(Path path) throws IOException;
}
