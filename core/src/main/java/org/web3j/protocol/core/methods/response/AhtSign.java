package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_sign.
 */
public class AhtSign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}
