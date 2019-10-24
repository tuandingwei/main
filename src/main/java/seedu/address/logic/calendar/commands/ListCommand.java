package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.calendar.CalendarModel.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.calendar.CalendarModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute(CalendarModel calendarModel) {
        requireNonNull(calendarModel);
        calendarModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}