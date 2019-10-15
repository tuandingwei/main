//package seedu.address.logic.calendar.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.CommandResult;
//import seedu.address.logic.commands.HelpCommand;
//
//
//public class HelpCommandTest {
//    private CalendarModel calendarModel = new CalendarCalendarModelManager();
//    private CalendarModel expectedModel = new CalendarCalendarModelManager();
//
//    @Test
//    public void execute_help_success() {
//        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
//        assertCommandSuccess(new HelpCommand(), calendarModel, expectedCommandResult, expectedModel);
//    }
//}
