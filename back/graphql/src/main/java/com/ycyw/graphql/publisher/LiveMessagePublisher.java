package com.ycyw.graphql.publisher;

import org.reactivestreams.Publisher;

import com.ycyw.graphql.generated.types.LiveMessage;
/**
 * Live message publisher
 */
public interface LiveMessagePublisher {
   /**
    * Publish next message
    * @param message
    */
   void next(LiveMessage message);
   
   /**
    * Get publisher for messages to a user's id
    * 
    * @param accountId
    * @return
    */
   Publisher<LiveMessage> getLiveMessagePublisher(String accountId);
}
