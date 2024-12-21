package com.ycyw.graphql.publisher;

import org.reactivestreams.Publisher;

import com.ycyw.graphql.generated.types.LiveMessage;

public interface LiveMessagePublisher {
   void next(LiveMessage message);
   Publisher<LiveMessage> getLiveMessagePublisher(String accountId);
}
