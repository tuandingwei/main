package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.calendar.LogicManager;
import seedu.address.logic.cap.LogicCapManager;
import seedu.address.logic.finance.LogicFinanceManager;
import seedu.address.logic.quiz.LogicQuizManager;
import seedu.address.model.calendar.AddressBook;
import seedu.address.model.calendar.ModelManager;
import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.ModelCapManager;
import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ModelFinanceManager;
import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.ModelQuizManager;
import seedu.address.storage.calendar.JsonAddressBookStorage;
import seedu.address.storage.calendar.JsonUserPrefsStorage;
import seedu.address.storage.calendar.StorageManager;
import seedu.address.storage.cap.CapStorageManager;
import seedu.address.storage.cap.JsonCapUserPrefsStorage;
import seedu.address.storage.cap.JsonCapStorage;
import seedu.address.storage.cap.CapStorage;
import seedu.address.storage.finance.FinanceStorageManager;
import seedu.address.storage.finance.JsonFinanceStorage;
import seedu.address.storage.finance.JsonFinanceUserPrefsStorage;
import seedu.address.storage.quiz.AddressBookStorage;
import seedu.address.storage.quiz.JsonQuizAddressBookStorage;
import seedu.address.storage.quiz.JsonQuizUserPrefsStorage;
import seedu.address.storage.quiz.StorageQuizManager;
import seedu.address.storage.quiz.UserPrefsStorage;
import seedu.address.ui.calendar.UiManager;
import seedu.address.ui.cap.UiCapManager;
import seedu.address.ui.finance.UiFinanceManager;
import seedu.address.ui.quiz.UiQuizManager;

/**
 * Switches the Application to a new component (Calendar, Module, Quiz, or Finance)
 */
public class SwitchOperation {
    private String args;
    private seedu.address.model.quiz.UserPrefs userPrefs;
    private seedu.address.model.quiz.Model quizModel;
    private seedu.address.logic.quiz.Logic quizLogic;
    private seedu.address.ui.quiz.Ui quizUi;

    private seedu.address.model.calendar.UserPrefs userCalendarPrefs;
    private seedu.address.model.calendar.Model calendarModel;
    private seedu.address.logic.calendar.Logic calendarLogic;
    private seedu.address.ui.calendar.Ui calendarUi;

    private seedu.address.model.finance.UserPrefs userFinancePrefs;
    private seedu.address.model.finance.Model financeModel;
    private seedu.address.logic.finance.Logic financeLogic;
    private seedu.address.ui.finance.Ui financeUi;

    private seedu.address.model.cap.UserPrefs userCapPrefs;
    private seedu.address.model.cap.Model capModel;
    private seedu.address.logic.cap.Logic capLogic;
    private seedu.address.ui.cap.Ui capUi;

    public SwitchOperation(String args) {
        this.args = args;
    }

    /**
     * Switches the current application component by initializing Model, Logic, and Ui that corresponds with the type
     * of component currently saved as args
     */
    public void execute() {
        if (args.equals("quiz")) {
            Config config = MainApp.getConfig();
            UserPrefsStorage userPrefsStorage = new JsonQuizUserPrefsStorage(config.getUserPrefsFilePath());
            userPrefs = initPrefs(userPrefsStorage);
            AddressBookStorage addressBookStorage = new JsonQuizAddressBookStorage(userPrefs.getAddressBookFilePath());
            seedu.address.storage.quiz.Storage quizStorage = new StorageQuizManager(addressBookStorage, userPrefsStorage);

            quizModel = initModelManager(quizStorage, userPrefs);
            quizLogic = new LogicQuizManager(quizModel, quizStorage);
            quizUi = new UiQuizManager(quizLogic);
            Stage stages = MainApp.getPrimary();
            quizUi.start(stages);
        } else if (args.equals("calendar")) {
            Config config = MainApp.getConfig();
            seedu.address.storage.calendar.UserPrefsStorage userPrefsStorage =
                    new JsonUserPrefsStorage(config.getUserPrefsFilePath());
            userCalendarPrefs = initPrefs(userPrefsStorage);
            seedu.address.storage.calendar.AddressBookStorage addressBookStorage =
                    new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
            seedu.address.storage.calendar.Storage calendarStorage =
                    new StorageManager(addressBookStorage, userPrefsStorage);

            calendarModel = initModelManager(calendarStorage, userCalendarPrefs);
            calendarLogic = new LogicManager(calendarModel, calendarStorage);
            calendarUi = new UiManager(calendarLogic);
            Stage stages = MainApp.getPrimary();
            calendarUi.start(stages);
        } else if (args.equals("finance")) {
            Config config = MainApp.getConfig();
            seedu.address.storage.finance.UserPrefsStorage userPrefsStorage =
                    new JsonFinanceUserPrefsStorage(config.getFinanceUserPrefsFilePath());
            userFinancePrefs = initPrefs(userPrefsStorage);
            seedu.address.storage.finance.FinanceStorage financeLogStorage =
                    new JsonFinanceStorage(userFinancePrefs.getFinanceLogFilePath());
            seedu.address.storage.finance.Storage financeStorage =
                    new FinanceStorageManager(financeLogStorage, userPrefsStorage);
            financeModel = initModelManager(financeStorage, userFinancePrefs);
            financeLogic = new LogicFinanceManager(financeModel, financeStorage);
            financeUi = new UiFinanceManager(financeLogic);
            Stage stages = MainApp.getPrimary();
            financeUi.start(stages);
        } else if (args.equals("cap")) {
            Config config = MainApp.getConfig();
            seedu.address.storage.cap.UserPrefsStorage userPrefsStorage =
                    new JsonCapUserPrefsStorage(config.getCapUserPrefsFilePath());
            userCapPrefs = initPrefs(userPrefsStorage);
            seedu.address.storage.cap.CapStorage capLogStorage =
                    new JsonCapStorage(userCapPrefs.getCapLogFilePath());
            seedu.address.storage.cap.Storage capStorage =
                    new CapStorageManager(capLogStorage, userPrefsStorage);
            capModel = initModelManager(capStorage, userCapPrefs);
            capLogic = new LogicCapManager(capModel, capStorage);
            capUi = new UiCapManager(capLogic);
            Stage stages = MainApp.getPrimary();
            capUi.start(stages);
        }
    }

    /**
     * Load user's Quiz preference.
     * @param storage Quiz storage
     * @return UserPrefs
     */
    protected seedu.address.model.quiz.UserPrefs initPrefs(seedu.address.storage.quiz.UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        System.out.println("Using prefs file : " + prefsFilePath);

        seedu.address.model.quiz.UserPrefs initializedPrefs;
        try {
            Optional<seedu.address.model.quiz.UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new seedu.address.model.quiz.UserPrefs());
        } catch (DataConversionException e) {
            System.out.println("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new seedu.address.model.quiz.UserPrefs();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new seedu.address.model.quiz.UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            System.out.println("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    /**
     * Load user's Calendar preference.
     * @param storage Quiz storage
     * @return UserPrefs
     */
    protected seedu.address.model.calendar.UserPrefs initPrefs(
            seedu.address.storage.calendar.UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        System.out.println("Using prefs file : " + prefsFilePath);

        seedu.address.model.calendar.UserPrefs initializedPrefs;
        try {
            Optional<seedu.address.model.calendar.UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new seedu.address.model.calendar.UserPrefs());
        } catch (DataConversionException e) {
            System.out.println("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new seedu.address.model.calendar.UserPrefs();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new seedu.address.model.calendar.UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            System.out.println("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    /**
     * Load user's Cap preference.
     * @param storage Cap storage
     * @return UserPrefs
     */
    protected seedu.address.model.cap.UserPrefs initPrefs(seedu.address.storage.cap.UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        System.out.println("Using prefs file : " + prefsFilePath);

        seedu.address.model.cap.UserPrefs initializedPrefs;
        try {
            Optional<seedu.address.model.cap.UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new seedu.address.model.cap.UserPrefs());
        } catch (DataConversionException e) {
            System.out.println("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new seedu.address.model.cap.UserPrefs();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new seedu.address.model.cap.UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            System.out.println("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    /**
     * Load user's Finance preference.
     * @param storage Quiz storage
     * @return UserPrefs
     */
    protected seedu.address.model.finance.UserPrefs initPrefs(
            seedu.address.storage.finance.UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        System.out.println("Using prefs file : " + prefsFilePath);

        seedu.address.model.finance.UserPrefs initializedPrefs;
        try {
            Optional<seedu.address.model.finance.UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new seedu.address.model.finance.UserPrefs());
        } catch (DataConversionException e) {
            System.out.println("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new seedu.address.model.finance.UserPrefs();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new seedu.address.model.finance.UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            System.out.println("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s and {@code userPrefs}. <br>
     */
    private seedu.address.model.quiz.Model initModelManager(seedu.address.storage.quiz.Storage storage,
                                                            seedu.address.model.quiz.ReadOnlyUserPrefs userPrefs) {
        Optional<seedu.address.model.quiz.ReadOnlyAddressBook> addressBookOptional;
        seedu.address.model.quiz.ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                System.out.println("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional
                    .orElseGet(seedu.address.model.quiz.util.SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new seedu.address.model.quiz.AddressQuizBook();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressQuizBook();
        }

        return new ModelQuizManager(initialData, userPrefs);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s and {@code userPrefs}. <br>
     */
    private seedu.address.model.calendar.Model initModelManager(
            seedu.address.storage.calendar.Storage storage,
            seedu.address.model.calendar.ReadOnlyUserPrefs userPrefs) {
        Optional<seedu.address.model.calendar.ReadOnlyAddressBook> addressBookOptional;
        seedu.address.model.calendar.ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                System.out.println("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional
                    .orElseGet(seedu.address.model.calendar.util.SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s and {@code userPrefs}. <br>
     */
    private seedu.address.model.finance.Model initModelManager(seedu.address.storage.finance.Storage storage,
                                                            seedu.address.model.finance.ReadOnlyUserPrefs userPrefs) {
        Optional<seedu.address.model.finance.ReadOnlyAddressBook> addressBookOptional;
        seedu.address.model.finance.ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                System.out.println("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional
                    .orElseGet(seedu.address.model.finance.util.SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new seedu.address.model.finance.FinanceLog();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new FinanceLog();
        }
        return new ModelFinanceManager(initialData, userPrefs);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s and {@code userPrefs}. <br>
     */
    private seedu.address.model.cap.Model initModelManager(CapStorage storage,
                                                           seedu.address.model.cap.ReadOnlyUserPrefs userPrefs) {
        Optional<seedu.address.model.cap.ReadOnlyModulo> addressBookOptional;
        seedu.address.model.cap.ReadOnlyModulo initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                System.out.println("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional
                    .orElseGet(seedu.address.model.cap.util.SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new seedu.address.model.cap.CapLog();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new CapLog();
        }

        return new ModelCapManager(initialData, userPrefs);
    }

}
