package network;

import network.networkMessages.NetworkMsg;

public interface IMsgRecipient<TNetworkMsg extends NetworkMsg> {

    TNetworkMsg send();
    void receive(TNetworkMsg networkMsg);
}
