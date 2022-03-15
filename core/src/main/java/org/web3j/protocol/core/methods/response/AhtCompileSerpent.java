package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_compileSerpent.
 */
public class AhtCompileSerpent extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
