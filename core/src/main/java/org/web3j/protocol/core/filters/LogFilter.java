package org.web3j.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.AhtLog;
import org.web3j.protocol.core.methods.response.Log;

/**
 * Log filter handler.
 */
public class LogFilter extends Filter<Log> {

    private final org.web3j.protocol.core.methods.request.AhtFilter AhtFilter;

    public LogFilter(
            Web3j web3j, Callback<Log> callback,
            org.web3j.protocol.core.methods.request.AhtFilter AhtFilter) {
        super(web3j, callback);
        this.AhtFilter = AhtFilter;
    }


    @Override
    org.web3j.protocol.core.methods.response.AhtFilter sendRequest() throws IOException {
        return web3j.ahtNewFilter(AhtFilter).send();
    }

    @Override
    void process(List<AhtLog.LogResult> logResults) {
        for (AhtLog.LogResult logResult : logResults) {
            if (logResult instanceof AhtLog.LogObject) {
                Log log = ((AhtLog.LogObject) logResult).get();
                callback.onEvent(log);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + " required LogObject");
            }
        }
    }

    @Override
    protected Request<?, AhtLog> getFilterLogs(BigInteger filterId) {
        return web3j.ahtGetFilterLogs(filterId);
    }
}
