package org.web3j.protocol.core.methods.response;

import java.util.List;

import org.web3j.protocol.core.Response;

/**
 * aht_getCompilers.
 */
public class AhtGetCompilers extends Response<List<String>> {
    public List<String> getCompilers() {
        return getResult();
    }
}
