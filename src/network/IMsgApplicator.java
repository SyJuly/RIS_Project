package network;

import network.networkMessages.NetworkMsg;

public interface IMsgApplicator<TNetworkMsg extends NetworkMsg> {

    boolean shouldSendMessage();
    TNetworkMsg getMessage();
    void receive(TNetworkMsg networkMsg);
}
