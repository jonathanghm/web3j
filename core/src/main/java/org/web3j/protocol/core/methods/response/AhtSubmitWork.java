package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_submitWork.
 */
public class AhtSubmitWork extends Response<Boolean> {

    public boolean solutionValid() {
        return getResult();
    }
}
