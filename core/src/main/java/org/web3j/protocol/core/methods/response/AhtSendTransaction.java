package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_sendTransaction.
 */
public class AhtSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
