package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_mining.
 */
public class AhtMining extends Response<Boolean> {
    public boolean isMining() {
        return getResult();
    }
}
