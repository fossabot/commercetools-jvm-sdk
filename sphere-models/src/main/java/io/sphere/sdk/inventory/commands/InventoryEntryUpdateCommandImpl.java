package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.models.Versioned;

import java.util.List;


final class InventoryEntryUpdateCommandImpl extends UpdateCommandDslImpl<InventoryEntry, InventoryEntryUpdateCommand> implements InventoryEntryUpdateCommand {
    InventoryEntryUpdateCommandImpl(final Versioned<InventoryEntry> versioned, final List<? extends UpdateAction<InventoryEntry>> updateActions) {
        super(versioned, updateActions, InventoryEntryEndpoint.ENDPOINT, InventoryEntryUpdateCommandImpl::new);
    }

    InventoryEntryUpdateCommandImpl(final UpdateCommandDslBuilder<InventoryEntry, InventoryEntryUpdateCommand> builder) {
        super(builder);
    }
}