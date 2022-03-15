package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_protocolVersion.
 */
public class AhtProtocolVersion extends Response<String> {
    public String getProtocolVersion() {
        return getResult();
    }
}
