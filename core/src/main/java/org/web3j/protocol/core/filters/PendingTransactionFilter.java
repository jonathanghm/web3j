package org.web3j.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.AhtFilter;
import org.web3j.protocol.core.methods.response.AhtLog;

/**
 * Handler for working with transaction filter requests.
 */
public class PendingTransactionFilter extends Filter<String> {

    public PendingTransactionFilter(Web3j web3j, Callback<String> callback) {
        super(web3j, callback);
    }

    @Override
    AhtFilter sendRequest() throws IOException {
        return web3j.ahtNewPendingTransactionFilter().send();
    }

    @Override
    void process(List<AhtLog.LogResult> logResults) {
        for (AhtLog.LogResult logResult : logResults) {
            if (logResult instanceof AhtLog.Hash) {
                String blockHash = ((AhtLog.Hash) logResult).get();
                callback.onEvent(blockHash);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + ", required Hash");
            }
        }
    }

    /**
     * Since the pending transaction filter does not support historic filters,
     * the filterId is ignored and an empty optional is returned
     * @param filterId
     * Id of the filter for which the historic log should be retrieved
     * @return null
     */
    @Override
    protected Request<?, AhtLog> getFilterLogs(BigInteger filterId) {
        return null;
    }
}

