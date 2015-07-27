package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.queries.ChannelByIdFetch;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.*;

public class ChannelDeleteCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final Channel channel = execute(ChannelCreateCommand.of(ChannelDraft.of(randomKey())));

        execute(ChannelDeleteCommand.of(channel));

        assertThat(execute(ChannelByIdFetch.of(channel.getId()))).isNull();
    }
}