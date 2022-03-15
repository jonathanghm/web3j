package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_getStorageAt.
 */
public class AhtGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
