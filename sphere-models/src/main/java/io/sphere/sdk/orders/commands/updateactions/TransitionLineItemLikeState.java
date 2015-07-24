package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

abstract class TransitionLineItemLikeState extends UpdateAction<Order> {
    protected final long quantity;
    protected final Reference<State> fromState;
    protected final Reference<State> toState;
    @Nullable
    protected final ZonedDateTime actualTransitionDate;

    protected TransitionLineItemLikeState(final String action, final long quantity, @Nullable final ZonedDateTime actualTransitionDate, final Reference<State> toState, final Reference<State> fromState) {
        super(action);
        this.quantity = quantity;
        this.actualTransitionDate = actualTransitionDate;
        this.toState = toState;
        this.fromState = fromState;
    }

    public long getQuantity() {
        return quantity;
    }

    public Reference<State> getFromState() {
        return fromState;
    }

    public Reference<State> getToState() {
        return toState;
    }

    @Nullable
    public ZonedDateTime getActualTransitionDate() {
        return actualTransitionDate;
    }
}
