package seedu.address.storage.cap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.person.Semester;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "capmodulelog")
class JsonSerializableCapLog {

    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate person(s).";

    private final List<JsonAdaptedSemester> semesters = new ArrayList<>();
    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCapLog(@JsonProperty("Semester") List<JsonAdaptedSemester> semesters) {
        this.semesters.addAll(semesters);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableCapLog(ReadOnlyCapLog source) {
        semesters.addAll(source.getSemesterList()
                .stream().map(JsonAdaptedSemester::new).collect(Collectors.toList()));
        modules.addAll(source.getModuleList()
                .stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CapLog toModelType() throws IllegalValueException {
        CapLog capLog = new CapLog();
        for (JsonAdaptedSemester jsonAdaptedSemester : semesters) {
            Semester semester = jsonAdaptedSemester.toModelType();
            if (capLog.hasSemester(semester)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            capLog.addSemester(semester);
        }
        return capLog;
    }

}