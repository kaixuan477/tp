package seedu.sellsavvy.testutil;

import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.commands.ordercommands.AddOrderCommand;
import seedu.sellsavvy.model.order.Order;

/**
 * A utility class for Order.
 */
public class OrderUtil {

    /**
     * Returns an add order command string for adding {@code order}
     * under person in index {@code index}.
     */
    public static String getAddOrderCommand(Index index, Order order) {
        return AddOrderCommand.COMMAND_WORD + " "
                + getIndexDetails(index)
                + getOrderDetails(order);
    }

    /**
     * Returns the part of command string for the given {@code order}'s details.
     */
    public static String getOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ITEM + order.getItem().fullDescription + " ");
        sb.append(PREFIX_DATE + order.getDate().value + " ");
        sb.append(PREFIX_COUNT + order.getCount().value);
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code index}'s details.
     */
    public static String getIndexDetails(Index index) {
        return index.getOneBased() + " ";
    }
}