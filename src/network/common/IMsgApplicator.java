package network.common;

import network.common.networkMessages.NetworkMsg;

public interface IMsgApplicator<TNetworkMsg extends NetworkMsg> {

    boolean shouldSendMessage();
    TNetworkMsg getMessage();
    void receive(TNetworkMsg networkMsg);
}