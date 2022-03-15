package org.web3j.protocol.core;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.AhtAccounts;
import org.web3j.protocol.core.methods.response.AhtBlock;
import org.web3j.protocol.core.methods.response.AhtBlockNumber;
import org.web3j.protocol.core.methods.response.AhtCall;
import org.web3j.protocol.core.methods.response.AhtCoinbase;
import org.web3j.protocol.core.methods.response.AhtCompileLLL;
import org.web3j.protocol.core.methods.response.AhtCompileSerpent;
import org.web3j.protocol.core.methods.response.AhtCompileSolidity;
import org.web3j.protocol.core.methods.response.AhtEstimateGas;
import org.web3j.protocol.core.methods.response.AhtFilter;
import org.web3j.protocol.core.methods.response.AhtGasPrice;
import org.web3j.protocol.core.methods.response.AhtGetBalance;
import org.web3j.protocol.core.methods.response.AhtGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.AhtGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.AhtGetCode;
import org.web3j.protocol.core.methods.response.AhtGetCompilers;
import org.web3j.protocol.core.methods.response.AhtGetStorageAt;
import org.web3j.protocol.core.methods.response.AhtGetTransactionCount;
import org.web3j.protocol.core.methods.response.AhtGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.AhtGetUncleCountByBlockHash;
import org.web3j.protocol.core.methods.response.AhtGetUncleCountByBlockNumber;
import org.web3j.protocol.core.methods.response.AhtHashrate;
import org.web3j.protocol.core.methods.response.AhtLog;
import org.web3j.protocol.core.methods.response.AhtMining;
import org.web3j.protocol.core.methods.response.AhtProtocolVersion;
import org.web3j.protocol.core.methods.response.AhtSendTransaction;
import org.web3j.protocol.core.methods.response.AhtSyncing;
import org.web3j.protocol.core.methods.response.AhtTransaction;
import org.web3j.protocol.core.methods.response.AhtUninstallFilter;
import org.web3j.protocol.core.methods.response.NetListening;
import org.web3j.protocol.core.methods.response.NetPeerCount;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.ShhNewGroup;
import org.web3j.protocol.core.methods.response.ShhNewIdentity;
import org.web3j.protocol.core.methods.response.ShhVersion;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.protocol.http.HttpService;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JSON-RPC 2.0 Integration Tests.
 */
public class CoreIT {

    private Web3j web3j;

    private IntegrationTestConfig config = new TestnetConfig();

    public CoreIT() { }

    @Before
    public void setUp() {
        this.web3j = Web3jFactory.build(new HttpService());
    }

    @Test
    public void testWeb3ClientVersion() throws Exception {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("Bowhead client version: " + clientVersion);
        assertFalse(clientVersion.isEmpty());
    }

    @Test
    public void testWeb3Sha3() throws Exception {
        Web3Sha3 web3Sha3 = web3j.web3Sha3("0x68656c6c6f20776f726c64").send();
        assertThat(web3Sha3.getResult(),
                is("0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad"));
    }

    @Test
    public void testNetVersion() throws Exception {
        NetVersion netVersion = web3j.netVersion().send();
        assertFalse(netVersion.getNetVersion().isEmpty());
    }

    @Test
    public void testNetListening() throws Exception {
        NetListening netListening = web3j.netListening().send();
        assertTrue(netListening.isListening());
    }

    @Test
    public void testNetPeerCount() throws Exception {
        NetPeerCount netPeerCount = web3j.netPeerCount().send();
        assertTrue(netPeerCount.getQuantity().signum() == 1);
    }

    @Test
    public void testAhtProtocolVersion() throws Exception {
        AhtProtocolVersion ahtProtocolVersion = web3j.ahtProtocolVersion().send();
        assertFalse(ahtProtocolVersion.getProtocolVersion().isEmpty());
    }

    @Test
    public void testAhtSyncing() throws Exception {
        AhtSyncing ahtSyncing = web3j.ahtSyncing().send();
        assertNotNull(ahtSyncing.getResult());
    }

    @Test
    public void testAhtCoinbase() throws Exception {
        AhtCoinbase ahtCoinbase = web3j.ahtCoinbase().send();
        assertNotNull(ahtCoinbase.getAddress());
    }

    @Test
    public void testAhtMining() throws Exception {
        AhtMining ahtMining = web3j.ahtMining().send();
        assertNotNull(ahtMining.getResult());
    }

    @Test
    public void testAhtHashrate() throws Exception {
        AhtHashrate ahtHashrate = web3j.ahtHashrate().send();
        assertThat(ahtHashrate.getHashrate(), is(BigInteger.ZERO));
    }

    @Test
    public void testAhtGasPrice() throws Exception {
        AhtGasPrice ahtGasPrice = web3j.ahtGasPrice().send();
        assertTrue(ahtGasPrice.getGasPrice().signum() == 1);
    }

    @Test
    public void testAhtAccounts() throws Exception {
        AhtAccounts ahtAccounts = web3j.ahtAccounts().send();
        assertNotNull(ahtAccounts.getAccounts());
    }

    @Test
    public void testAhtBlockNumber() throws Exception {
        AhtBlockNumber ahtBlockNumber = web3j.ahtBlockNumber().send();
        assertTrue(ahtBlockNumber.getBlockNumber().signum() == 1);
    }

    @Test
    public void testAhtGetBalance() throws Exception {
        AhtGetBalance ahtGetBalance = web3j.ahtGetBalance(
                config.validAccount(), DefaultBlockParameterName.valueOf("latest")).send();
        assertTrue(ahtGetBalance.getBalance().signum() == 1);
    }

    @Test
    public void testAhtGetStorageAt() throws Exception {
        AhtGetStorageAt ahtGetStorageAt = web3j.ahtGetStorageAt(
                config.validContractAddress(),
                BigInteger.valueOf(0),
                DefaultBlockParameterName.valueOf("latest")).send();
        assertThat(ahtGetStorageAt.getData(), is(config.validContractAddressPositionZero()));
    }

    @Test
    public void testAhtGetTransactionCount() throws Exception {
        AhtGetTransactionCount ahtGetTransactionCount = web3j.ahtGetTransactionCount(
                config.validAccount(),
                DefaultBlockParameterName.valueOf("latest")).send();
        assertTrue(ahtGetTransactionCount.getTransactionCount().signum() == 1);
    }

    @Test
    public void testAhtGetBlockTransactionCountByHash() throws Exception {
        AhtGetBlockTransactionCountByHash ahtGetBlockTransactionCountByHash =
                web3j.ahtGetBlockTransactionCountByHash(
                        config.validBlockHash()).send();
        assertThat(ahtGetBlockTransactionCountByHash.getTransactionCount(),
                equalTo(config.validBlockTransactionCount()));
    }

    @Test
    public void testAhtGetBlockTransactionCountByNumber() throws Exception {
        AhtGetBlockTransactionCountByNumber ahtGetBlockTransactionCountByNumber =
                web3j.ahtGetBlockTransactionCountByNumber(
                        DefaultBlockParameterNumber.valueOf(config.validBlock())).send();
        assertThat(ahtGetBlockTransactionCountByNumber.getTransactionCount(),
                equalTo(config.validBlockTransactionCount()));
    }

    @Test
    public void testAhtGetUncleCountByBlockHash() throws Exception {
        AhtGetUncleCountByBlockHash ahtGetUncleCountByBlockHash =
                web3j.ahtGetUncleCountByBlockHash(config.validBlockHash()).send();
        assertThat(ahtGetUncleCountByBlockHash.getUncleCount(),
                equalTo(config.validBlockUncleCount()));
    }

    @Test
    public void testAhtGetUncleCountByBlockNumber() throws Exception {
        AhtGetUncleCountByBlockNumber ahtGetUncleCountByBlockNumber =
                web3j.ahtGetUncleCountByBlockNumber(
                        DefaultBlockParameterName.valueOf("latest")).send();
        assertThat(ahtGetUncleCountByBlockNumber.getUncleCount(),
                equalTo(config.validBlockUncleCount()));
    }

    @Test
    public void testAhtGetCode() throws Exception {
        AhtGetCode ahtGetCode = web3j.ahtGetCode(config.validContractAddress(),
                DefaultBlockParameterNumber.valueOf(config.validBlock())).send();
        assertThat(ahtGetCode.getCode(), is(config.validContractCode()));
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testAhtSign() throws Exception {
        // AhtSign ahtSign = web3j.ahtSign();
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testAhtSendTransaction() throws Exception {
        AhtSendTransaction ahtSendTransaction = web3j.ahtSendTransaction(
                config.buildTransaction()).send();
        assertFalse(ahtSendTransaction.getTransactionHash().isEmpty());
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testAhtSendRawTransaction() throws Exception {

    }

    @Test
    public void testAhtCall() throws Exception {
        AhtCall ahtCall = web3j.ahtCall(config.buildTransaction(),
                DefaultBlockParameterName.valueOf("latest")).send();

        assertThat(DefaultBlockParameterName.LATEST.getValue(), is("latest"));
        assertThat(ahtCall.getValue(), is("0x"));
    }

    @Test
    public void testAhtEstimateGas() throws Exception {
        AhtEstimateGas ahtEstimateGas = web3j.ahtEstimateGas(config.buildTransaction())
                .send();
        assertTrue(ahtEstimateGas.getAmountUsed().signum() == 1);
    }

    @Test
    public void testAhtGetBlockByHashReturnHashObjects() throws Exception {
        AhtBlock ahtBlock = web3j.ahtGetBlockByHash(config.validBlockHash(), false)
                .send();

        AhtBlock.Block block = ahtBlock.getBlock();
        assertNotNull(ahtBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                is(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testAhtGetBlockByHashReturnFullTransactionObjects() throws Exception {
        AhtBlock ahtBlock = web3j.ahtGetBlockByHash(config.validBlockHash(), true)
                .send();

        AhtBlock.Block block = ahtBlock.getBlock();
        assertNotNull(ahtBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testAhtGetBlockByNumberReturnHashObjects() throws Exception {
        AhtBlock ahtBlock = web3j.ahtGetBlockByNumber(
                DefaultBlockParameterNumber.valueOf(config.validBlock()), false).send();

        AhtBlock.Block block = ahtBlock.getBlock();
        assertNotNull(ahtBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testAhtGetBlockByNumberReturnTransactionObjects() throws Exception {
        AhtBlock ahtBlock = web3j.ahtGetBlockByNumber(
                DefaultBlockParameterNumber.valueOf(config.validBlock()), true).send();

        AhtBlock.Block block = ahtBlock.getBlock();
        assertNotNull(ahtBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testAhtGetTransactionByHash() throws Exception {
        AhtTransaction ahtTransaction = web3j.ahtGetTransactionByHash(
                config.validTransactionHash()).send();
        assertNotNull(ahtTransaction.getTransaction());
        Transaction transaction = ahtTransaction.getTransaction();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
    }

    @Test
    public void testAhtGetTransactionByBlockHashAndIndex() throws Exception {
        BigInteger index = BigInteger.ONE;

        AhtTransaction ahtTransaction = web3j.ahtGetTransactionByBlockHashAndIndex(
                config.validBlockHash(), index).send();
        assertNotNull(ahtTransaction.getTransaction());
        Transaction transaction = ahtTransaction.getTransaction();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
        assertThat(transaction.getTransactionIndex(), equalTo(index));
    }

    @Test
    public void testAhtGetTransactionByBlockNumberAndIndex() throws Exception {
        BigInteger index = BigInteger.ONE;

        AhtTransaction ahtTransaction = web3j.ahtGetTransactionByBlockNumberAndIndex(
                DefaultBlockParameterNumber.valueOf(config.validBlock()), index).send();
        Transaction transaction = ahtTransaction.getTransaction();
        assertNotNull(transaction);
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
        assertThat(transaction.getTransactionIndex(), equalTo(index));
    }

    @Test
    public void testAhtGetTransactionReceipt() throws Exception {
        AhtGetTransactionReceipt ahtGetTransactionReceipt = web3j.ahtGetTransactionReceipt(
                config.validTransactionHash()).send();
        TransactionReceipt transactionReceipt =
                ahtGetTransactionReceipt.getTransactionReceipt();
        assertNotNull(transactionReceipt);
        assertThat(transactionReceipt.getTransactionHash(), is(config.validTransactionHash()));
    }

    @Test
    public void testAhtGetUncleByBlockHashAndIndex() throws Exception {
        AhtBlock ahtBlock = web3j.ahtGetUncleByBlockHashAndIndex(
                config.validUncleBlockHash(), BigInteger.ZERO).send();
        assertNotNull(ahtBlock.getBlock());
    }

    @Test
    public void testAhtGetUncleByBlockNumberAndIndex() throws Exception {
        AhtBlock ahtBlock = web3j.ahtGetUncleByBlockNumberAndIndex(
                DefaultBlockParameterNumber.valueOf(config.validUncleBlock()), BigInteger.ZERO)
                .send();
        assertNotNull(ahtBlock.getBlock());
    }

    @Test
    public void testAhtGetCompilers() throws Exception {
        AhtGetCompilers ahtGetCompilers = web3j.ahtGetCompilers().send();
        assertNotNull(ahtGetCompilers.getCompilers());
    }

    @Ignore  // The .ahtod aht_compileLLL does not exist/is not available
    @Test
    public void testAhtCompileLLL() throws Exception {
        AhtCompileLLL ahtCompileLLL = web3j.ahtCompileLLL(
                "(returnlll (suicide (caller)))").send();
        assertFalse(ahtCompileLLL.getCompiledSourceCode().isEmpty());
    }

    @Test
    public void testAhtCompileSolidity() throws Exception {
        String sourceCode = "pragma solidity ^0.4.0;"
                + "\ncontract test { function multiply(uint a) returns(uint d) {"
                + "   return a * 7;   } }"
                + "\ncontract test2 { function multiply2(uint a) returns(uint d) {"
                + "   return a * 7;   } }";
        AhtCompileSolidity ahtCompileSolidity = web3j.ahtCompileSolidity(sourceCode)
                .send();
        assertNotNull(ahtCompileSolidity.getCompiledSolidity());
        assertThat(
                ahtCompileSolidity.getCompiledSolidity().get("test2").getInfo().getSource(),
                is(sourceCode));
    }

    @Ignore  // The .ahtod aht_compileSerpent does not exist/is not available
    @Test
    public void testAhtCompileSerpent() throws Exception {
        AhtCompileSerpent ahtCompileSerpent = web3j.ahtCompileSerpent(
                "/* some serpent */").send();
        assertFalse(ahtCompileSerpent.getCompiledSourceCode().isEmpty());
    }

    @Test
    public void testFiltersByFilterId() throws Exception {
        org.web3j.protocol.core.methods.request.AhtFilter AhtFilter =
                new org.web3j.protocol.core.methods.request.AhtFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                config.validContractAddress());

        String eventSignature = config.encodedEvent();
        AhtFilter.addSingleTopic(eventSignature);

        // aht_newFilter
        AhtFilter ahtNewFilter = web3j.ahtNewFilter(AhtFilter).send();
        BigInteger filterId = ahtNewFilter.getFilterId();

        // aht_getFilterLogs
        AhtLog ahtFilterLogs = web3j.ahtGetFilterLogs(filterId).send();
        List<AhtLog.LogResult> filterLogs = ahtFilterLogs.getLogs();
        assertFalse(filterLogs.isEmpty());

        // aht_getFilterChanges - nothing will have changed in this interval
        AhtLog ahtLog = web3j.ahtGetFilterChanges(filterId).send();
        assertTrue(ahtLog.getLogs().isEmpty());

        // aht_uninstallFilter
        AhtUninstallFilter ahtUninstallFilter = web3j.ahtUninstallFilter(filterId).send();
        assertTrue(ahtUninstallFilter.isUninstalled());
    }

    @Test
    public void testAhtNewBlockFilter() throws Exception {
        AhtFilter ahtNewBlockFilter = web3j.ahtNewBlockFilter().send();
        assertNotNull(ahtNewBlockFilter.getFilterId());
    }

    @Test
    public void testAhtNewPendingTransactionFilter() throws Exception {
        AhtFilter ahtNewPendingTransactionFilter =
                web3j.ahtNewPendingTransactionFilter().send();
        assertNotNull(ahtNewPendingTransactionFilter.getFilterId());
    }

    @Test
    public void testAhtGetLogs() throws Exception {
        org.web3j.protocol.core.methods.request.AhtFilter AhtFilter =
                new org.web3j.protocol.core.methods.request.AhtFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                config.validContractAddress()
        );

        AhtFilter.addSingleTopic(config.encodedEvent());

        AhtLog ahtLog = web3j.ahtGetLogs(AhtFilter).send();
        List<AhtLog.LogResult> logs = ahtLog.getLogs();
        assertFalse(logs.isEmpty());
    }

    // @Test
    // public void testAhtGetWork() throws Exception {
    //     AhtGetWork ahtGetWork = requestFactory.ahtGetWork();
    //     assertNotNull(ahtGetWork.getResult());
    // }

    @Test
    public void testAhtSubmitWork() throws Exception {

    }

    @Test
    public void testAhtSubmitHashrate() throws Exception {
    
    }

    @Test
    public void testDbPutString() throws Exception {
    
    }

    @Test
    public void testDbGetString() throws Exception {
    
    }

    @Test
    public void testDbPutHex() throws Exception {
    
    }

    @Test
    public void testDbGetHex() throws Exception {
    
    }

    @Test
    public void testShhPost() throws Exception {
    
    }

    @Ignore // The .ahtod shh_version does not exist/is not available
    @Test
    public void testShhVersion() throws Exception {
        ShhVersion shhVersion = web3j.shhVersion().send();
        assertNotNull(shhVersion.getVersion());
    }

    @Ignore  // The .ahtod shh_newIdentity does not exist/is not available
    @Test
    public void testShhNewIdentity() throws Exception {
        ShhNewIdentity shhNewIdentity = web3j.shhNewIdentity().send();
        assertNotNull(shhNewIdentity.getAddress());
    }

    @Test
    public void testShhHasIdentity() throws Exception {
    
    }

    @Ignore  // The .ahtod shh_newIdentity does not exist/is not available
    @Test
    public void testShhNewGroup() throws Exception {
        ShhNewGroup shhNewGroup = web3j.shhNewGroup().send();
        assertNotNull(shhNewGroup.getAddress());
    }

    @Ignore  // The .ahtod shh_addToGroup does not exist/is not available
    @Test
    public void testShhAddToGroup() throws Exception {

    }

    @Test
    public void testShhNewFilter() throws Exception {
    
    }

    @Test
    public void testShhUninstallFilter() throws Exception {
    
    }

    @Test
    public void testShhGetFilterChanges() throws Exception {
    
    }

    @Test
    public void testShhGetMessages() throws Exception {
    
    }
}
