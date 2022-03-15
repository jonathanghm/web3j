package org.web3j.protocol.gaht;

import org.web3j.protocol.Web3jService;

/**
 * web3j Gaht client factory.
 */
public class GahtFactory {

    public static Gaht build(Web3jService web3jService) {
        return new JsonRpc2_0Gaht(web3jService);
    }
}
