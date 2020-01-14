package gameLWJGL.network.common;

import gameLWJGL.network.common.networkMessages.NetworkMsg;

public interface IMsgApplicator<TNetworkMsg extends NetworkMsg> {

    boolean shouldSendMessage();
    TNetworkMsg getMessage();
    TNetworkMsg getStartMessage();
    void receive(TNetworkMsg networkMsg);
}
