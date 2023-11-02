package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

/**
 * Duplicates a transaction identified using its displayed index from the address book.
 * Optional edits can be made to the duplicated transaction.
 * Timestamp will be set to the current time if not specified.
 */
public class DuplicateTransactionCommand extends Command {

    public static final String COMMAND_WORD = "duplicateTransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Duplicates the transaction identified by the index number with optional edits. "
        + "Timestamp will be set to the current time if not specified.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_COST + "COST] "
        + "[" + PREFIX_DESCRIPTION + "DETAILS] "
        + "[" + PREFIX_NAME + "PAYEE NAME] "
        + "[" + PREFIX_TIMESTAMP + "TIMESTAMP]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_COST + "10.00 "
        + PREFIX_DESCRIPTION + "Bought a book "
        + PREFIX_NAME + "John Doe "
        + PREFIX_TIMESTAMP + "2020-01-01 12:00";

    public static final String MESSAGE_DUPLICATE_TRANSACTION_SUCCESS = "New duplicated transaction added: %1$s";

    public static final String MESSAGE_TRANSACTION_NOT_RELEVANT =
        "The duplicated transaction does not affect your balances";

    private final Index targetIndex;
    private final EditTransactionDescriptor duplicateTransactionDescriptor;

    /**
     * @param targetIndex                    of the transaction in the filtered transaction list to duplicate
     * @param duplicateTransactionDescriptor details to edit the duplicated transaction with
     */
    public DuplicateTransactionCommand(Index targetIndex, EditTransactionDescriptor duplicateTransactionDescriptor) {
        this.targetIndex = targetIndex;
        this.duplicateTransactionDescriptor = duplicateTransactionDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownTransactionList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownTransactionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToDuplicate = lastShownTransactionList.get(targetIndex.getZeroBased());
        Transaction duplicateTransaction = EditTransactionCommand.createEditedTransaction(
            transactionToDuplicate, duplicateTransactionDescriptor);

        if (!duplicateTransaction.isRelevant()) {
            throw new CommandException(MESSAGE_TRANSACTION_NOT_RELEVANT);
        }

        model.addTransaction(duplicateTransaction);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(
            String.format(MESSAGE_DUPLICATE_TRANSACTION_SUCCESS, Messages.format(duplicateTransaction)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DuplicateTransactionCommand)) {
            return false;
        }

        DuplicateTransactionCommand otherDuplicateTransactionCommand = (DuplicateTransactionCommand) other;
        return targetIndex.equals(otherDuplicateTransactionCommand.targetIndex)
            && duplicateTransactionDescriptor.equals(otherDuplicateTransactionCommand.duplicateTransactionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .add("duplicateTransactionDescriptor", duplicateTransactionDescriptor)
            .toString();
    }
}
