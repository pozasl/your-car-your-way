package com.ycyw.graphql.signaling;

import lombok.Builder;
import lombok.Data;

/**
 * Signal message for WebRTC's SDP
 */
@Data
@Builder
class SignalMessage {
    private String content;
    private String sessionId;
}