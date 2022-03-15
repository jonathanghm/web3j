package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_sendRawTransaction.
 */
public class AhtSendRawTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
