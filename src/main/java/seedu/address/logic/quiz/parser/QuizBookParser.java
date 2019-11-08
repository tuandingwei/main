package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.quiz.commands.AddCommand;
import seedu.address.logic.quiz.commands.AddCommentCommand;
import seedu.address.logic.quiz.commands.ClearCommand;
import seedu.address.logic.quiz.commands.Command;
import seedu.address.logic.quiz.commands.DeleteCommand;
import seedu.address.logic.quiz.commands.DetailCommand;
import seedu.address.logic.quiz.commands.EditCommand;
import seedu.address.logic.quiz.commands.ExitCommand;
import seedu.address.logic.quiz.commands.FindCommand;
import seedu.address.logic.quiz.commands.HelpCommand;
import seedu.address.logic.quiz.commands.ListCommand;
import seedu.address.logic.quiz.commands.RedoCommand;
import seedu.address.logic.quiz.commands.SwitchCommand;
import seedu.address.logic.quiz.commands.UndoCommand;
import seedu.address.logic.quiz.commands.ViewAnswerCommand;
import seedu.address.logic.quiz.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class QuizBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddCommentCommand.COMMAND_WORD:
            return new AddCommentCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DetailCommand.COMMAND_WORD:
            return new DetailCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ViewAnswerCommand.COMMAND_WORD:
            return new ViewAnswerCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
