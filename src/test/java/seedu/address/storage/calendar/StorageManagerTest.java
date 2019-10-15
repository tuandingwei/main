//package seedu.address.storage.calendar;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import java.nio.file.Path;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import seedu.address.commons.core.GuiSettings;
//
//public class StorageManagerTest {
//
//    @TempDir
//    public Path testFolder;
//
//    private StorageManager storageManager;
//
//    @BeforeEach
//    public void setUp() {
//        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
//        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
//        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
//    }
//
//    private Path getTempFilePath(String fileName) {
//        return testFolder.resolve(fileName);
//    }
//
//    @Test
//    public void prefsReadSave() throws Exception {
//        /*
//         * Note: This is an integration test that verifies the StorageManager is properly wired to the
//         * {@link JsonUserPrefsStorage} class.
//         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
//         */
//        CalendarUserPrefs original = new CalendarUserPrefs();
//        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
//        storageManager.saveUserPrefs(original);
//        CalendarUserPrefs retrieved = storageManager.readUserPrefs().get();
//        assertEquals(original, retrieved);
//    }
//
//    @Test
//    public void addressBookReadSave() throws Exception {
//        /*
//         * Note: This is an integration test that verifies the StorageManager is properly wired to the
//         * {@link JsonAddressBookStorage} class.
//         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
//         */
//        CalendarCalendarAddressBook original = getTypicalAddressBook();
//        storageManager.saveAddressBook(original);
//        ReadOnlyCalendarAddressBook retrieved = storageManager.readAddressBook().get();
//        assertEquals(original, new CalendarCalendarAddressBook(retrieved));
//    }
//
//    @Test
//    public void getAddressBookFilePath() {
//        assertNotNull(storageManager.getAddressBookFilePath());
//    }
//
//}
