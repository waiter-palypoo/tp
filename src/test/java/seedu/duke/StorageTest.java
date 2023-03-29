package seedu.duke;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class StorageTest {
    @Test
    public void storageFilesCreated_successful() {
        Storage storage = new Storage();
        Path storageFileDir = Path.of("").resolve(Storage.DATA_DIR_NAME).toAbsolutePath();
        assertTrue(Files.exists(storageFileDir));
        Path storageFile = storageFileDir.resolve(Storage.DATA_FILE_NAME);
        assertTrue(Files.exists(storageFile));
    }
}
