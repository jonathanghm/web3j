package org.web3j.tx;

import java.io.IOException;

import org.junit.Before;

import org.web3j.crypto.SampleKeys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.AhtGetTransactionCount;
import org.web3j.protocol.core.methods.response.AhtGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.AhtSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public abstract class ManagedTransactionTester {

    static final String ADDRESS = "0x3d6cb163f7c72d20b0fcd6baae5889329d138a4a";
    static final String TRANSACTION_HASH = "0xHASH";
    protected Web3j web3j;

    @Before
    public void setUp() throws Exception {
        web3j = mock(Web3j.class);
    }

    void prepareTransaction(TransactionReceipt transactionReceipt) throws IOException {
        prepareNonceRequest();
        prepareTransactionRequest();
        prepareTransactionReceipt(transactionReceipt);
    }

    @SuppressWarnings("unchecked")
    void prepareNonceRequest() throws IOException {
        AhtGetTransactionCount ahtGetTransactionCount = new AhtGetTransactionCount();
        ahtGetTransactionCount.setResult("0x1");

        Request<?, AhtGetTransactionCount> transactionCountRequest = mock(Request.class);
        when(transactionCountRequest.send())
                .thenReturn(ahtGetTransactionCount);
        when(web3j.ahtGetTransactionCount(SampleKeys.ADDRESS, DefaultBlockParameterName.PENDING))
                .thenReturn((Request) transactionCountRequest);
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionRequest() throws IOException {
        AhtSendTransaction ahtSendTransaction = new AhtSendTransaction();
        ahtSendTransaction.setResult(TRANSACTION_HASH);

        Request<?, AhtSendTransaction> rawTransactionRequest = mock(Request.class);
        when(rawTransactionRequest.send()).thenReturn(ahtSendTransaction);
        when(web3j.ahtSendRawTransaction(any(String.class)))
                .thenReturn((Request) rawTransactionRequest);
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionReceipt(TransactionReceipt transactionReceipt) throws IOException {
        AhtGetTransactionReceipt ahtGetTransactionReceipt = new AhtGetTransactionReceipt();
        ahtGetTransactionReceipt.setResult(transactionReceipt);

        Request<?, AhtGetTransactionReceipt> getTransactionReceiptRequest = mock(Request.class);
        when(getTransactionReceiptRequest.send())
                .thenReturn(ahtGetTransactionReceipt);
        when(web3j.ahtGetTransactionReceipt(TRANSACTION_HASH))
                .thenReturn((Request) getTransactionReceiptRequest);
    }
}
