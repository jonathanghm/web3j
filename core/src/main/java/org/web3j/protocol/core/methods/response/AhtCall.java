package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_call.
 */
public class AhtCall extends Response<String> {
    public String getValue() {
        return getResult();
    }
}
