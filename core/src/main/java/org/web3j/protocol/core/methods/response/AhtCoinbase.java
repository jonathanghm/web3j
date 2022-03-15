package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_coinbase.
 */
public class AhtCoinbase extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}
