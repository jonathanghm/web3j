package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_submitHashrate.
 */
public class AhtSubmitHashrate extends Response<Boolean> {

    public boolean submissionSuccessful() {
        return getResult();
    }
}
