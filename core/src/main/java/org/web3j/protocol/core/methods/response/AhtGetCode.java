package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_getCode.
 */
public class AhtGetCode extends Response<String> {
    public String getCode() {
        return getResult();
    }
}
