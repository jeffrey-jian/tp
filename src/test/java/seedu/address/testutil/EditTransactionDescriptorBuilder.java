package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Timestamp;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.portion.Portion;

/**
 * A utility class to help with building EditTransactionDescriptor objects.
 */
public class EditTransactionDescriptorBuilder {

    private EditTransactionDescriptor descriptor;

    public EditTransactionDescriptorBuilder() {
        descriptor = new EditTransactionDescriptor();
    }

    public EditTransactionDescriptorBuilder(EditTransactionDescriptor descriptor) {
        this.descriptor = new EditTransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTransactionDescriptor} with fields containing {@code transaction}'s details
     */
    public EditTransactionDescriptorBuilder(Transaction transaction) {
        descriptor = new EditTransactionDescriptor();
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDescription(transaction.getDescription());
        descriptor.setPayeeName(transaction.getPayeeName());
        descriptor.setPortions(transaction.getPortions());
        descriptor.setTimestamp(transaction.getTimestamp());
    }

    /**
     * Returns an {@code EditTransactionDescriptor} with fields {@code payeeName} and {@code portions} set to null.
     * This is used for v1.2 testing {@code EditTransactionCommand} without {@code payeeName} and {@code portions}.
     */
    public EditTransactionDescriptorBuilder withoutPayeeNameAndPortions() {
        descriptor.setPayeeName(null);
        descriptor.setPortions(null);
        return this;
    }

    /**
     * Returns an {@code EditTransactionDescriptor} with fields {@code timestamp} set to null.
     * This is required for testing the parser, as the parser does not parse the timestamp.
     */
    public EditTransactionDescriptorBuilder withoutTimestamp() {
        descriptor.setTimestamp(null);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code PayeeName} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withPayeeName(String payeeName) {
        descriptor.setPayeeName(new Name(payeeName));
        return this;
    }

    /**
     * Parses the {@code portions} into a {@code Set<Portion>}
     * and set it to the {@code EditTransactionDescriptor}.
     * Arguments should be in pairs of {@code name} and {@code weight}.
     */
    public EditTransactionDescriptorBuilder withPortions(String... portions) {
        if (portions.length % 2 != 0) {
            throw new IllegalArgumentException("Arguments should be in pairs of name and weight");
        }
        Set<Portion> portionSet = new HashSet<>();
        for (int i = 0; i < portions.length; i += 2) {
            portionSet.add(new PortionBuilder().withName(portions[i]).withWeight(portions[i + 1]).build());
        }
        descriptor.setPortions(portionSet);
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withTimestamp(String timestamp) {
        descriptor.setTimestamp(new Timestamp(timestamp));
        return this;
    }

    public EditTransactionDescriptor build() {
        return descriptor;
    }

}