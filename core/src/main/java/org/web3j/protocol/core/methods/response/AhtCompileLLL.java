package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_compileLLL.
 */
public class AhtCompileLLL extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
