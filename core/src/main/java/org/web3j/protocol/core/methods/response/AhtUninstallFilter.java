package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * aht_uninstallFilter.
 */
public class AhtUninstallFilter extends Response<Boolean> {
    public boolean isUninstalled() {
        return getResult();
    }
}
