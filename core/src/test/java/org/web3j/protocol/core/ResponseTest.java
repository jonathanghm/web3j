package org.web3j.protocol.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import org.web3j.protocol.ResponseTester;
import org.web3j.protocol.core.methods.response.AbiDefinition;
import org.web3j.protocol.core.methods.response.AhtFilter;
import org.web3j.protocol.core.methods.response.DbGetHex;
import org.web3j.protocol.core.methods.response.DbGetString;
import org.web3j.protocol.core.methods.response.DbPutHex;
import org.web3j.protocol.core.methods.response.DbPutString;
import org.web3j.protocol.core.methods.response.AhtAccounts;
import org.web3j.protocol.core.methods.response.AhtBlock;
import org.web3j.protocol.core.methods.response.AhtBlockNumber;
import org.web3j.protocol.core.methods.response.AhtCall;
import org.web3j.protocol.core.methods.response.AhtCompileLLL;
import org.web3j.protocol.core.methods.response.AhtCompileSerpent;
import org.web3j.protocol.core.methods.response.AhtCompileSolidity;
import org.web3j.protocol.core.methods.response.AhtEstimateGas;
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
import org.web3j.protocol.core.methods.response.AhtGetWork;
import org.web3j.protocol.core.methods.response.AhtHashrate;
import org.web3j.protocol.core.methods.response.AhtLog;
import org.web3j.protocol.core.methods.response.AhtMining;
import org.web3j.protocol.core.methods.response.AhtProtocolVersion;
import org.web3j.protocol.core.methods.response.AhtSendRawTransaction;
import org.web3j.protocol.core.methods.response.AhtSendTransaction;
import org.web3j.protocol.core.methods.response.AhtSign;
import org.web3j.protocol.core.methods.response.AhtSubmitHashrate;
import org.web3j.protocol.core.methods.response.AhtSubmitWork;
import org.web3j.protocol.core.methods.response.AhtSyncing;
import org.web3j.protocol.core.methods.response.AhtTransaction;
import org.web3j.protocol.core.methods.response.AhtUninstallFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.NetListening;
import org.web3j.protocol.core.methods.response.NetPeerCount;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.ShhAddToGroup;
import org.web3j.protocol.core.methods.response.ShhHasIdentity;
import org.web3j.protocol.core.methods.response.ShhMessages;
import org.web3j.protocol.core.methods.response.ShhNewFilter;
import org.web3j.protocol.core.methods.response.ShhNewGroup;
import org.web3j.protocol.core.methods.response.ShhNewIdentity;
import org.web3j.protocol.core.methods.response.ShhPost;
import org.web3j.protocol.core.methods.response.ShhUninstallFilter;
import org.web3j.protocol.core.methods.response.ShhVersion;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Core Protocol Response tests.
 */
public class ResponseTest extends ResponseTester {

    @Test
    public void testErrorResponse() {
        buildResponse(
                "{"
                        + "  \"jsonrpc\":\"2.0\","
                        + "  \"id\":1,"
                        + "  \"error\":{"
                        + "    \"code\":-32602,"
                        + "    \"message\":\"Invalid address length, expected 40 got 64 bytes\","
                        + "    \"data\":null"
                        + "  }"
                        + "}"
        );

        AhtBlock ahtBlock = deserialiseResponse(AhtBlock.class);
        assertTrue(ahtBlock.hasError());
        assertThat(ahtBlock.getError(), equalTo(
                new Response.Error(-32602, "Invalid address length, expected 40 got 64 bytes")));
    }

    @Test
    public void testWeb3ClientVersion() {
        buildResponse(
                "{\n"
                        + "  \"id\":67,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": \"Mist/v0.9.3/darwin/go1.4.1\"\n"
                        + "}"
        );

        Web3ClientVersion web3ClientVersion = deserialiseResponse(Web3ClientVersion.class);
        assertThat(web3ClientVersion.getWeb3ClientVersion(), is("Mist/v0.9.3/darwin/go1.4.1"));
    }

    @Test
    public void testWeb3Sha3() throws IOException {
        buildResponse(
                "{\n"
                        + "  \"id\":64,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": "
                        + "\"0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad\"\n"
                        + "}"
        );

        Web3Sha3 web3Sha3 = deserialiseResponse(Web3Sha3.class);
        assertThat(web3Sha3.getResult(),
                is("0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad"));
    }

    @Test
    public void testNetVersion() throws IOException {
        buildResponse(
                "{\n"
                        + "  \"id\":67,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"59\"\n"
                        + "}"
        );

        NetVersion netVersion = deserialiseResponse(NetVersion.class);
        assertThat(netVersion.getNetVersion(), is("59"));
    }

    @Test
    public void testNetListening() throws IOException {
        buildResponse(
                "{\n"
                        + "  \"id\":67,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\":true\n"
                        + "}"
        );

        NetListening netListening = deserialiseResponse(NetListening.class);
        assertThat(netListening.isListening(), is(true));
    }

    @Test
    public void testNetPeerCount() throws IOException {
        buildResponse(
                "{\n"
                        + "  \"id\":74,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x2\"\n"
                        + "}"
        );

        NetPeerCount netPeerCount = deserialiseResponse(NetPeerCount.class);
        assertThat(netPeerCount.getQuantity(), equalTo(BigInteger.valueOf(2L)));
    }

    @Test
    public void testAhtProtocolVersion() throws IOException {
        buildResponse(
                "{\n"
                        + "  \"id\":67,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"54\"\n"
                        + "}"
        );

        AhtProtocolVersion ahtProtocolVersion = deserialiseResponse(AhtProtocolVersion.class);
        assertThat(ahtProtocolVersion.getProtocolVersion(), is("54"));
    }

    @Test
    public void testAhtSyncingInProgress() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": {\n"
                        + "  \"startingBlock\": \"0x384\",\n"
                        + "  \"currentBlock\": \"0x386\",\n"
                        + "  \"highestBlock\": \"0x454\"\n"
                        + "  }\n"
                        + "}"
        );


        // Response received from Gaht node
        // "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":{\"currentBlock\":\"0x117a\",
        // \"highestBlock\":\"0x21dab4\",\"knownStates\":\"0x0\",\"pulledStates\":\"0x0\",
        // \"startingBlock\":\"0xa51\"}}"

        AhtSyncing ahtSyncing = deserialiseResponse(AhtSyncing.class);

        assertThat(ahtSyncing.getResult(),
                CoreMatchers.<AhtSyncing.Result>equalTo(
                        new AhtSyncing.Syncing("0x384", "0x386", "0x454", null, null)));
    }

    @Test
    public void testAhtSyncing() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": false\n"
                        + "}"
        );

        AhtSyncing ahtSyncing = deserialiseResponse(AhtSyncing.class);
        assertThat(ahtSyncing.isSyncing(), is(false));
    }

    @Test
    public void testAhtMining() {
        buildResponse(
                "{\n"
                        + "  \"id\":71,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        AhtMining ahtMining = deserialiseResponse(AhtMining.class);
        assertThat(ahtMining.isMining(), is(true));
    }

    @Test
    public void testAhtHashrate() {
        buildResponse(
                "{\n"
                        + "  \"id\":71,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x38a\"\n"
                        + "}"
        );

        AhtHashrate ahtHashrate = deserialiseResponse(AhtHashrate.class);
        assertThat(ahtHashrate.getHashrate(), equalTo(BigInteger.valueOf(906L)));
    }

    @Test
    public void testAhtGasPrice() {
        buildResponse(
                "{\n"
                        + "  \"id\":73,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x9184e72a000\"\n"
                        + "}"
        );

        AhtGasPrice ahtGasPrice = deserialiseResponse(AhtGasPrice.class);
        assertThat(ahtGasPrice.getGasPrice(), equalTo(BigInteger.valueOf(10000000000000L)));
    }

    @Test
    public void testAhtAccounts() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": [\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\"]\n"
                        + "}"
        );

        AhtAccounts ahtAccounts = deserialiseResponse(AhtAccounts.class);
        assertThat(ahtAccounts.getAccounts(),
                equalTo(Arrays.asList("0x407d73d8a49eeb85d32cf465507dd71d507100c1")));
    }

    @Test
    public void testAhtBlockNumber() {
        buildResponse(
                "{\n"
                        + "  \"id\":83,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x4b7\"\n"
                        + "}"
        );

        AhtBlockNumber ahtBlockNumber = deserialiseResponse(AhtBlockNumber.class);
        assertThat(ahtBlockNumber.getBlockNumber(), equalTo(BigInteger.valueOf(1207L)));
    }

    @Test
    public void testAhtGetBalance() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x234c8a3397aab58\"\n"
                        + "}"
        );

        AhtGetBalance ahtGetBalance = deserialiseResponse(AhtGetBalance.class);
        assertThat(ahtGetBalance.getBalance(), equalTo(BigInteger.valueOf(158972490234375000L)));
    }

    @Test
    public void testAhtStorageAt() {
        buildResponse(
                "{\n"
                        + "    \"jsonrpc\":\"2.0\","
                        + "    \"id\":1,"
                        + "    \"result\":"
                        + "\"0x000000000000000000000000000000000000000000000000000000000000162e\""
                        + "}"
        );

        AhtGetStorageAt ahtGetStorageAt = deserialiseResponse(AhtGetStorageAt.class);
        assertThat(ahtGetStorageAt.getResult(),
                is("0x000000000000000000000000000000000000000000000000000000000000162e"));
    }

    @Test
    public void testAhtGetTransactionCount() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}"
        );

        AhtGetTransactionCount ahtGetTransactionCount =
                deserialiseResponse((AhtGetTransactionCount.class));
        assertThat(ahtGetTransactionCount.getTransactionCount(), equalTo(BigInteger.valueOf(1L)));
    }

    @Test
    public void testAhtGetBlockTransactionCountByHash() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0xb\"\n"
                        + "}"
        );

        AhtGetBlockTransactionCountByHash ahtGetBlockTransactionCountByHash =
                deserialiseResponse(AhtGetBlockTransactionCountByHash.class);
        assertThat(ahtGetBlockTransactionCountByHash.getTransactionCount(),
                equalTo(BigInteger.valueOf(11)));
    }

    @Test
    public void testAhtGetBlockTransactionCountByNumber() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0xa\"\n"
                        + "}"
        );

        AhtGetBlockTransactionCountByNumber ahtGetBlockTransactionCountByNumber =
                deserialiseResponse(AhtGetBlockTransactionCountByNumber.class);
        assertThat(ahtGetBlockTransactionCountByNumber.getTransactionCount(),
                equalTo(BigInteger.valueOf(10)));
    }

    @Test
    public void testAhtGetUncleCountByBlockHash() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}"
        );

        AhtGetUncleCountByBlockHash ahtGetUncleCountByBlockHash = deserialiseResponse(
                AhtGetUncleCountByBlockHash.class);
        assertThat(ahtGetUncleCountByBlockHash.getUncleCount(),
                equalTo(BigInteger.valueOf(1)));
    }

    @Test
    public void testAhtGetUncleCountByBlockNumber() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}"
        );

        AhtGetUncleCountByBlockNumber ahtGetUncleCountByBlockNumber = deserialiseResponse(
                AhtGetUncleCountByBlockNumber.class);
        assertThat(ahtGetUncleCountByBlockNumber.getUncleCount(),
                equalTo(BigInteger.valueOf(1)));
    }

    @Test
    public void testGetCode() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x600160008035811a818181146012578301005b601b60013560255"
                        + "65b8060005260206000f25b600060078202905091905056\"\n"
                        + "}"
        );

        AhtGetCode ahtGetCode = deserialiseResponse(AhtGetCode.class);
        assertThat(ahtGetCode.getCode(),
                is("0x600160008035811a818181146012578301005b601b60013560255"
                        + "65b8060005260206000f25b600060078202905091905056"));
    }

    @Test
    public void testAhtSign() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": "
                        + "\"0xbd685c98ec39490f50d15c67ba2a8e9b5b1d6d7601fca80b295e7d717446bd8b712"
                        + "7ea4871e996cdc8cae7690408b4e800f60ddac49d2ad34180e68f1da0aaf001\"\n"
                        + "}"
        );

        AhtSign ahtSign = deserialiseResponse(AhtSign.class);
        assertThat(ahtSign.getSignature(),
                is("0xbd685c98ec39490f50d15c67ba2a8e9b5b1d6d7601fca80b295e7d717446bd8b7127ea4871e9"
                        + "96cdc8cae7690408b4e800f60ddac49d2ad34180e68f1da0aaf001"));
    }

    @Test
    public void testAhtSendTransaction() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": "
                        + "\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\"\n"
                        + "}"
        );

        AhtSendTransaction ahtSendTransaction = deserialiseResponse(AhtSendTransaction.class);
        assertThat(ahtSendTransaction.getTransactionHash(),
                is("0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331"));
    }

    @Test
    public void testAhtSendRawTransaction() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": "
                        + "\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\"\n"
                        + "}"
        );

        AhtSendRawTransaction ahtSendRawTransaction =
                deserialiseResponse(AhtSendRawTransaction.class);
        assertThat(ahtSendRawTransaction.getTransactionHash(),
                is("0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331"));
    }

    @Test
    public void testAhtCall() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x\"\n"
                        + "}"
        );

        AhtCall ahtCall = deserialiseResponse(AhtCall.class);
        assertThat(ahtCall.getValue(), is("0x"));
    }

    @Test
    public void testAhtEstimateGas() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x5208\"\n"
                        + "}"
        );

        AhtEstimateGas ahtEstimateGas = deserialiseResponse(AhtEstimateGas.class);
        assertThat(ahtEstimateGas.getAmountUsed(), equalTo(BigInteger.valueOf(21000)));
    }

    @Test
    public void testAhtBlockTransactionHashes() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "\"id\":1,\n"
                        + "\"jsonrpc\":\"2.0\",\n"
                        + "\"result\": {\n"
                        + "    \"number\": \"0x1b4\",\n"
                        + "    \"hash\": \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n"
                        + "    \"parentHash\": \"0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5\",\n"
                        + "    \"nonce\": \"0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2\",\n"
                        + "    \"sha3Uncles\": \"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\n"
                        + "    \"logsBloom\": \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n"
                        + "    \"transactionsRoot\": \"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\n"
                        + "    \"stateRoot\": \"0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff\",\n"
                        + "    \"receiptsRoot\": \"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\n"
                        + "    \"author\": \"0x1a95ad5ccdb0677af951810c6ddf4935afe4e5a6\",\n"
                        + "    \"miner\": \"0x4e65fda2159562a496f9f3522f89122a3088497a\",\n"
                        + "    \"mixHash\": \"0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b\",\n"
                        + "    \"difficulty\": \"0x027f07\",\n"
                        + "    \"totalDifficulty\":  \"0x027f07\",\n"
                        + "    \"extraData\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "    \"size\":  \"0x027f07\",\n"
                        + "    \"gasLimit\": \"0x9f759\",\n"
                        + "    \"gasUsed\": \"0x9f759\",\n"
                        + "    \"timestamp\": \"0x54e34e8e\",\n"
                        + "    \"transactions\": ["
                        + "        \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n"
                        + "        \"0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1df\"\n"
                        + "    ], \n"
                        + "    \"uncles\": [\n"
                        + "       \"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\n"
                        + "       \"0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff\"\n"
                        + "    ],\n"
                        + "    \"sealFields\": [\n"
                        + "       \"0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b\",\n"
                        + "       \"0x39a3eb432fbef1fc\"\n"
                        + "    ]\n"
                        + "  }\n"
                        + "}"
        );
        //CHECKSTYLE:ON

        AhtBlock ahtBlock = deserialiseResponse(AhtBlock.class);
        AhtBlock.Block block = new AhtBlock.Block(
                "0x1b4",
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                "0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5",
                "0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2",
                "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
                "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff",
                "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
                "0x1a95ad5ccdb0677af951810c6ddf4935afe4e5a6",
                "0x4e65fda2159562a496f9f3522f89122a3088497a",
                "0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b",
                "0x027f07",
                "0x027f07",
                "0x0000000000000000000000000000000000000000000000000000000000000000",
                "0x027f07",
                "0x9f759",
                "0x9f759",
                "0x54e34e8e",
                Arrays.<AhtBlock.TransactionResult>asList(
                        new AhtBlock.TransactionHash(
                                "0xe670ec64341771606e55d6b4ca35a1a6b"
                                        + "75ee3d5145a99d05921026d1527331"),
                        new AhtBlock.TransactionHash(
                                "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1df")
                ),
                Arrays.asList(
                        "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
                        "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff"
                ),
                Arrays.asList(
                        "0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b",
                        "0x39a3eb432fbef1fc"
                )
        );
        assertThat(ahtBlock.getBlock(), equalTo(block));
    }

    @Test
    public void testAhtBlockFullTransactionsParity() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "\"id\":1,\n"
                        + "\"jsonrpc\":\"2.0\",\n"
                        + "\"result\": {\n"
                        + "    \"number\": \"0x1b4\",\n"
                        + "    \"hash\": \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n"
                        + "    \"parentHash\": \"0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5\",\n"
                        + "    \"nonce\": \"0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2\",\n"
                        + "    \"sha3Uncles\": \"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\n"
                        + "    \"logsBloom\": \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n"
                        + "    \"transactionsRoot\": \"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\n"
                        + "    \"stateRoot\": \"0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff\",\n"
                        + "    \"receiptsRoot\": \"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\n"
                        + "    \"author\": \"0x1a95ad5ccdb0677af951810c6ddf4935afe4e5a6\",\n"
                        + "    \"miner\": \"0x4e65fda2159562a496f9f3522f89122a3088497a\",\n"
                        + "    \"mixHash\": \"0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b\",\n"
                        + "    \"difficulty\": \"0x027f07\",\n"
                        + "    \"totalDifficulty\":  \"0x027f07\",\n"
                        + "    \"extraData\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "    \"size\":  \"0x027f07\",\n"
                        + "    \"gasLimit\": \"0x9f759\",\n"
                        + "    \"gasUsed\": \"0x9f759\",\n"
                        + "    \"timestamp\": \"0x54e34e8e\",\n"
                        + "    \"transactions\": [{"
                        + "        \"hash\":\"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b\",\n"
                        + "        \"nonce\":\"0x\",\n"
                        + "        \"blockHash\": \"0xbeab0aa2411b7ab17f30a99d3cb9c6ef2fc5426d6ad6fd9e2a26a6aed1d1055b\",\n"
                        + "        \"blockNumber\": \"0x15df\",\n"
                        + "        \"transactionIndex\":  \"0x1\",\n"
                        + "        \"from\":\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"to\":\"0x85h43d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"value\":\"0x7f110\",\n"
                        + "        \"gas\": \"0x7f110\",\n"
                        + "        \"gasPrice\":\"0x09184e72a000\",\n"
                        + "        \"input\":\"0x603880600c6000396000f300603880600c6000396000f3603880600c6000396000f360\","
                        + "        \"creates\":null,\n"
                        + "        \"publicKey\":\"0x6614d7d7bfe989295821985de0439e868b26ff05f98ae0da0ce5bccc24ea368a083b785323c9fcb405dd4c10a2c95d93312a1b2d68beb24ab4ea7c3c2f7c455b\",\n"
                        + "        \"raw\":\"0xf8cd83103a048504a817c800830e57e0945927c5cc723c4486f93bf90bad3be8831139499e80b864140f8dd300000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000c03905df347aa6490d5a98fbb8d8e49520000000000000000000000000000000000000000000000000000000057d56ee61ba0f115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dca04a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62\",\n"
                        + "        \"r\":\"0xf115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dc\",\n"
                        + "        \"s\":\"0x4a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62\",\n"
                        + "        \"v\":0\n"
                        + "    }], \n"
                        + "    \"uncles\": [\n"
                        + "       \"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\n"
                        + "       \"0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff\"\n"
                        + "    ],\n"
                        + "    \"sealFields\": [\n"
                        + "       \"0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b\",\n"
                        + "       \"0x39a3eb432fbef1fc\"\n"
                        + "    ]\n"
                        + "  }\n"
                        + "}"
        );
        //CHECKSTYLE:ON

        AhtBlock ahtBlock = deserialiseResponse(AhtBlock.class);
        AhtBlock.Block block = new AhtBlock.Block(
                "0x1b4",
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                "0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5",
                "0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2",
                "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
                "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff",
                "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
                "0x1a95ad5ccdb0677af951810c6ddf4935afe4e5a6",
                "0x4e65fda2159562a496f9f3522f89122a3088497a",
                "0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b",
                "0x027f07",
                "0x027f07",
                "0x0000000000000000000000000000000000000000000000000000000000000000",
                "0x027f07",
                "0x9f759",
                "0x9f759",
                "0x54e34e8e",
                //CHECKSTYLE:OFF
                Arrays.<AhtBlock.TransactionResult>asList(new AhtBlock.TransactionObject(
                                "0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
                                "0x",
                                "0xbeab0aa2411b7ab17f30a99d3cb9c6ef2fc5426d6ad6fd9e2a26a6aed1d1055b",
                                "0x15df",
                                "0x1",
                                "0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                                "0x85h43d8a49eeb85d32cf465507dd71d507100c1",
                                "0x7f110",
                                "0x7f110",
                                "0x09184e72a000",
                                "0x603880600c6000396000f300603880600c6000396000f3603880600c6000396000f360",
                                null,
                                "0x6614d7d7bfe989295821985de0439e868b26ff05f98ae0da0ce5bccc24ea368a083b785323c9fcb405dd4c10a2c95d93312a1b2d68beb24ab4ea7c3c2f7c455b",
                                "0xf8cd83103a048504a817c800830e57e0945927c5cc723c4486f93bf90bad3be8831139499e80b864140f8dd300000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000c03905df347aa6490d5a98fbb8d8e49520000000000000000000000000000000000000000000000000000000057d56ee61ba0f115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dca04a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62",
                                "0xf115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dc",
                                "0x4a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62",
                                (byte) 0
                        )
                ),
                //CHECKSTYLE:ON
                Arrays.asList(
                        "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
                        "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff"
                ),
                Arrays.asList(
                        "0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b",
                        "0x39a3eb432fbef1fc"
                )
        );
        assertThat(ahtBlock.getBlock(), equalTo(block));
    }

    // Remove once Gaht & Parity return the same v value in transactions
    @Test
    public void testAhtBlockFullTransactionsGaht() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "\"id\":1,\n"
                        + "\"jsonrpc\":\"2.0\",\n"
                        + "\"result\": {\n"
                        + "    \"number\": \"0x1b4\",\n"
                        + "    \"hash\": \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n"
                        + "    \"parentHash\": \"0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5\",\n"
                        + "    \"nonce\": \"0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2\",\n"
                        + "    \"sha3Uncles\": \"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\n"
                        + "    \"logsBloom\": \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n"
                        + "    \"transactionsRoot\": \"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\n"
                        + "    \"stateRoot\": \"0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff\",\n"
                        + "    \"receiptsRoot\": \"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\n"
                        + "    \"author\": \"0x1a95ad5ccdb0677af951810c6ddf4935afe4e5a6\",\n"
                        + "    \"miner\": \"0x4e65fda2159562a496f9f3522f89122a3088497a\",\n"
                        + "    \"mixHash\": \"0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b\",\n"
                        + "    \"difficulty\": \"0x027f07\",\n"
                        + "    \"totalDifficulty\":  \"0x027f07\",\n"
                        + "    \"extraData\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "    \"size\":  \"0x027f07\",\n"
                        + "    \"gasLimit\": \"0x9f759\",\n"
                        + "    \"gasUsed\": \"0x9f759\",\n"
                        + "    \"timestamp\": \"0x54e34e8e\",\n"
                        + "    \"transactions\": [{"
                        + "        \"hash\":\"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b\",\n"
                        + "        \"nonce\":\"0x\",\n"
                        + "        \"blockHash\": \"0xbeab0aa2411b7ab17f30a99d3cb9c6ef2fc5426d6ad6fd9e2a26a6aed1d1055b\",\n"
                        + "        \"blockNumber\": \"0x15df\",\n"
                        + "        \"transactionIndex\":  \"0x1\",\n"
                        + "        \"from\":\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"to\":\"0x85h43d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"value\":\"0x7f110\",\n"
                        + "        \"gas\": \"0x7f110\",\n"
                        + "        \"gasPrice\":\"0x09184e72a000\",\n"
                        + "        \"input\":\"0x603880600c6000396000f300603880600c6000396000f3603880600c6000396000f360\","
                        + "        \"creates\":null,\n"
                        + "        \"publicKey\":\"0x6614d7d7bfe989295821985de0439e868b26ff05f98ae0da0ce5bccc24ea368a083b785323c9fcb405dd4c10a2c95d93312a1b2d68beb24ab4ea7c3c2f7c455b\",\n"
                        + "        \"raw\":\"0xf8cd83103a048504a817c800830e57e0945927c5cc723c4486f93bf90bad3be8831139499e80b864140f8dd300000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000c03905df347aa6490d5a98fbb8d8e49520000000000000000000000000000000000000000000000000000000057d56ee61ba0f115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dca04a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62\",\n"
                        + "        \"r\":\"0xf115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dc\",\n"
                        + "        \"s\":\"0x4a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62\",\n"
                        + "        \"v\":\"0x9d\"\n"
                        + "    }], \n"
                        + "    \"uncles\": [\n"
                        + "       \"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\n"
                        + "       \"0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff\"\n"
                        + "    ],\n"
                        + "    \"sealFields\": [\n"
                        + "       \"0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b\",\n"
                        + "       \"0x39a3eb432fbef1fc\"\n"
                        + "    ]\n"
                        + "  }\n"
                        + "}"
        );
        //CHECKSTYLE:ON

        AhtBlock ahtBlock = deserialiseResponse(AhtBlock.class);
        AhtBlock.Block block = new AhtBlock.Block(
                "0x1b4",
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                "0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5",
                "0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2",
                "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
                "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff",
                "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
                "0x1a95ad5ccdb0677af951810c6ddf4935afe4e5a6",
                "0x4e65fda2159562a496f9f3522f89122a3088497a",
                "0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b",
                "0x027f07",
                "0x027f07",
                "0x0000000000000000000000000000000000000000000000000000000000000000",
                "0x027f07",
                "0x9f759",
                "0x9f759",
                "0x54e34e8e",
                //CHECKSTYLE:OFF
                Arrays.<AhtBlock.TransactionResult>asList(new AhtBlock.TransactionObject(
                                "0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
                                "0x",
                                "0xbeab0aa2411b7ab17f30a99d3cb9c6ef2fc5426d6ad6fd9e2a26a6aed1d1055b",
                                "0x15df",
                                "0x1",
                                "0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                                "0x85h43d8a49eeb85d32cf465507dd71d507100c1",
                                "0x7f110",
                                "0x7f110",
                                "0x09184e72a000",
                                "0x603880600c6000396000f300603880600c6000396000f3603880600c6000396000f360",
                                null,
                                "0x6614d7d7bfe989295821985de0439e868b26ff05f98ae0da0ce5bccc24ea368a083b785323c9fcb405dd4c10a2c95d93312a1b2d68beb24ab4ea7c3c2f7c455b",
                                "0xf8cd83103a048504a817c800830e57e0945927c5cc723c4486f93bf90bad3be8831139499e80b864140f8dd300000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000c03905df347aa6490d5a98fbb8d8e49520000000000000000000000000000000000000000000000000000000057d56ee61ba0f115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dca04a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62",
                                "0xf115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dc",
                                "0x4a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62",
                                0x9d
                        )
                ),
                //CHECKSTYLE:ON
                Arrays.asList(
                        "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
                        "0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff"
                ),
                Arrays.asList(
                        "0x57919c4e72e79ad7705a26e7ecd5a08ff546ac4fa37882e9cc57be87a3dab26b",
                        "0x39a3eb432fbef1fc"
                )
        );
        assertThat(ahtBlock.getBlock(), equalTo(block));
    }

    @Test
    public void testAhtBlockNull() {
        buildResponse(
                "{\n"
                        + "  \"result\": null\n"
                        + "}"
        );

        AhtBlock ahtBlock = deserialiseResponse(AhtBlock.class);
        assertNull(ahtBlock.getBlock());
    }

    @Test
    public void testAhtTransaction() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "    \"id\":1,\n"
                        + "    \"jsonrpc\":\"2.0\",\n"
                        + "    \"result\": {\n"
                        + "        \"hash\":\"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b\",\n"
                        + "        \"nonce\":\"0x\",\n"
                        + "        \"blockHash\": \"0xbeab0aa2411b7ab17f30a99d3cb9c6ef2fc5426d6ad6fd9e2a26a6aed1d1055b\",\n"
                        + "        \"blockNumber\": \"0x15df\",\n"
                        + "        \"transactionIndex\":  \"0x1\",\n"
                        + "        \"from\":\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"to\":\"0x85h43d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"value\":\"0x7f110\",\n"
                        + "        \"gas\": \"0x7f110\",\n"
                        + "        \"gasPrice\":\"0x09184e72a000\",\n"
                        + "        \"input\":\"0x603880600c6000396000f300603880600c6000396000f3603880600c6000396000f360\",\n"
                        + "        \"creates\":null,\n"
                        + "        \"publicKey\":\"0x6614d7d7bfe989295821985de0439e868b26ff05f98ae0da0ce5bccc24ea368a083b785323c9fcb405dd4c10a2c95d93312a1b2d68beb24ab4ea7c3c2f7c455b\",\n"
                        + "        \"raw\":\"0xf8cd83103a048504a817c800830e57e0945927c5cc723c4486f93bf90bad3be8831139499e80b864140f8dd300000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000c03905df347aa6490d5a98fbb8d8e49520000000000000000000000000000000000000000000000000000000057d56ee61ba0f115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dca04a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62\",\n"
                        + "        \"r\":\"0xf115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dc\",\n"
                        + "        \"s\":\"0x4a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62\",\n"
                        + "        \"v\":0\n"
                        + "  }\n"
                        + "}"
        );
        Transaction transaction = new Transaction(
                "0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
                "0x",
                "0xbeab0aa2411b7ab17f30a99d3cb9c6ef2fc5426d6ad6fd9e2a26a6aed1d1055b",
                "0x15df",
                "0x1",
                "0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                "0x85h43d8a49eeb85d32cf465507dd71d507100c1",
                "0x7f110",
                "0x7f110",
                "0x09184e72a000",
                "0x603880600c6000396000f300603880600c6000396000f3603880600c6000396000f360",
                null,
                "0x6614d7d7bfe989295821985de0439e868b26ff05f98ae0da0ce5bccc24ea368a083b785323c9fcb405dd4c10a2c95d93312a1b2d68beb24ab4ea7c3c2f7c455b",
                "0xf8cd83103a048504a817c800830e57e0945927c5cc723c4486f93bf90bad3be8831139499e80b864140f8dd300000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000c03905df347aa6490d5a98fbb8d8e49520000000000000000000000000000000000000000000000000000000057d56ee61ba0f115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dca04a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62",
                "0xf115cc4d7516dd430046504e1c888198e0323e8ded016d755f89c226ba3481dc",
                "0x4a2ae8ee49f1100b5c0202b37ed8bacf4caeddebde6b7f77e12e7a55893e9f62",
                (byte) 0
        );
        //CHECKSTYLE:ON

        AhtTransaction ahtTransaction = deserialiseResponse(AhtTransaction.class);
        assertThat(ahtTransaction.getTransaction(), equalTo(transaction));
    }

    @Test
    public void testAhtTransactionNull() {
        buildResponse(
                "{\n"
                        + "  \"result\": null\n"
                        + "}"
        );

        AhtTransaction ahtTransaction = deserialiseResponse(AhtTransaction.class);
        assertNull(ahtTransaction.getTransaction());
    }

    @Test
    public void testeAhtGetTransactionReceiptBeforeByzantium() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "    \"id\":1,\n"
                        + "    \"jsonrpc\":\"2.0\",\n"
                        + "    \"result\": {\n"
                        + "        \"transactionHash\": \"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\",\n"
                        + "        \"transactionIndex\":  \"0x1\",\n"
                        + "        \"blockHash\": \"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b\",\n"
                        + "        \"blockNumber\": \"0xb\",\n"
                        + "        \"cumulativeGasUsed\": \"0x33bc\",\n"
                        + "        \"gasUsed\": \"0x4dc\",\n"
                        + "        \"contractAddress\": \"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\n"
                        + "        \"root\": \"9307ba10e41ecf3d40507fc908655fe72fc129d46f6d99baf7605d0e29184911\",\n"
                        + "        \"from\":\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"to\":\"0x85h43d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"logs\": [{\n"
                        + "            \"removed\": false,\n"
                        + "            \"logIndex\": \"0x1\",\n"
                        + "            \"transactionIndex\": \"0x0\",\n"
                        + "            \"transactionHash\": \"0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf\",\n"
                        + "            \"blockHash\": \"0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d\",\n"
                        + "            \"blockNumber\":\"0x1b4\",\n"
                        + "            \"address\": \"0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d\",\n"
                        + "            \"data\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "            \"type\":\"mined\",\n"
                        + "            \"topics\": [\"0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5\"]"
                        + "        }],\n"
                        + "        \"logsBloom\":\"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\"\n"
                        + "  }\n"
                        + "}"
        );

        TransactionReceipt transactionReceipt =
                new TransactionReceipt(
                        "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238",
                        "0x1",
                        "0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
                        "0xb",
                        "0x33bc",
                        "0x4dc",
                        "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                        "9307ba10e41ecf3d40507fc908655fe72fc129d46f6d99baf7605d0e29184911",
                        null,
                        "0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                        "0x85h43d8a49eeb85d32cf465507dd71d507100c1",
                        Arrays.asList(
                                new Log(
                                        false,
                                        "0x1",
                                        "0x0",
                                        "0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf",
                                        "0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d",
                                        "0x1b4",
                                        "0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d",
                                        "0x0000000000000000000000000000000000000000000000000000000000000000",
                                        "mined",
                                        Arrays.asList(
                                                "0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5"
                                        )
                                )
                        ),
                        "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"

                );
        //CHECKSTYLE:ON

        AhtGetTransactionReceipt ahtGetTransactionReceipt = deserialiseResponse(
                AhtGetTransactionReceipt.class);
        assertThat(ahtGetTransactionReceipt.getTransactionReceipt(),
                equalTo(transactionReceipt));
    }

    @Test
    public void testeAhtGetTransactionReceiptAfterByzantium() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "    \"id\":1,\n"
                        + "    \"jsonrpc\":\"2.0\",\n"
                        + "    \"result\": {\n"
                        + "        \"transactionHash\": \"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\",\n"
                        + "        \"transactionIndex\":  \"0x1\",\n"
                        + "        \"blockHash\": \"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b\",\n"
                        + "        \"blockNumber\": \"0xb\",\n"
                        + "        \"cumulativeGasUsed\": \"0x33bc\",\n"
                        + "        \"gasUsed\": \"0x4dc\",\n"
                        + "        \"contractAddress\": \"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\n"
                        + "        \"status\": \"0x1\",\n"
                        + "        \"from\":\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"to\":\"0x85h43d8a49eeb85d32cf465507dd71d507100c1\",\n"
                        + "        \"logs\": [{\n"
                        + "            \"removed\": false,\n"
                        + "            \"logIndex\": \"0x1\",\n"
                        + "            \"transactionIndex\": \"0x0\",\n"
                        + "            \"transactionHash\": \"0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf\",\n"
                        + "            \"blockHash\": \"0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d\",\n"
                        + "            \"blockNumber\":\"0x1b4\",\n"
                        + "            \"address\": \"0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d\",\n"
                        + "            \"data\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "            \"type\":\"mined\",\n"
                        + "            \"topics\": [\"0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5\"]"
                        + "        }],\n"
                        + "        \"logsBloom\":\"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\"\n"
                        + "  }\n"
                        + "}"
        );

        TransactionReceipt transactionReceipt =
                new TransactionReceipt(
                        "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238",
                        "0x1",
                        "0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
                        "0xb",
                        "0x33bc",
                        "0x4dc",
                        "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                        null,
                        "0x1",
                        "0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                        "0x85h43d8a49eeb85d32cf465507dd71d507100c1",
                        Arrays.asList(
                                new Log(
                                        false,
                                        "0x1",
                                        "0x0",
                                        "0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf",
                                        "0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d",
                                        "0x1b4",
                                        "0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d",
                                        "0x0000000000000000000000000000000000000000000000000000000000000000",
                                        "mined",
                                        Arrays.asList(
                                                "0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5"
                                        )
                                )
                        ),
                        "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"

                );
        //CHECKSTYLE:ON

        AhtGetTransactionReceipt ahtGetTransactionReceipt = deserialiseResponse(
                AhtGetTransactionReceipt.class);
        assertThat(ahtGetTransactionReceipt.getTransactionReceipt(),
                equalTo(transactionReceipt));
    }

    @Test
    public void testAhtGetCompilers() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": [\"solidity\", \"lll\", \"serpent\"]\n"
                        + "}"
        );

        AhtGetCompilers ahtGetCompilers = deserialiseResponse(AhtGetCompilers.class);
        assertThat(ahtGetCompilers.getCompilers(), equalTo(Arrays.asList(
                "solidity", "lll", "serpent"
        )));
    }

    @Test
    public void testAhtCompileSolidity() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": {\n"
                        + "    \"test\": {\n"
                        + "      \"code\": \"0x605280600c6000396000f3006000357c010000000000000000000000000000000000000000000000000000000090048063c6888fa114602e57005b60376004356041565b8060005260206000f35b6000600782029050604d565b91905056\",\n"
                        + "      \"info\": {\n"
                        + "        \"source\": \"contract test {\\n\\tfunction multiply(uint a) returns(uint d) {\\n\\t\\treturn a * 7;\\n\\t}\\n}\\n\",\n"
                        + "        \"language\": \"Solidity\",\n"
                        + "        \"languageVersion\": \"0\",\n"
                        + "        \"compilerVersion\": \"0.8.2\",\n"
                        + "        \"compilerOptions\": \"--bin --abi --userdoc --devdoc --add-std --optimize -o /var/folders/3m/_6gnl12n1tj_5kf7sc3d72dw0000gn/T/solc498936951\",\n"
                        + "        \"abiDefinition\": [\n"
                        + "          {\n"
                        + "            \"constant\": false,\n"
                        + "            \"inputs\": [\n"
                        + "              {\n"
                        + "                \"name\": \"a\",\n"
                        + "                \"type\": \"uint256\"\n"
                        + "              }\n"
                        + "            ],\n"
                        + "            \"name\": \"multiply\",\n"
                        + "            \"outputs\": [\n"
                        + "              {\n"
                        + "                \"name\": \"d\",\n"
                        + "                \"type\": \"uint256\"\n"
                        + "              }\n"
                        + "            ],\n"
                        + "            \"type\": \"function\",\n"
                        + "            \"payable\": false\n"
                        + "          }\n"
                        + "        ],\n"
                        + "        \"userDoc\": {\n"
                        + "          \"methods\": {}\n"
                        + "        },\n"
                        + "        \"developerDoc\": {\n"
                        + "          \"methods\": {}\n"
                        + "        }\n"
                        + "      }\n"
                        + "    }\n"
                        + "    }"
                        + "  }\n"
                        + "}"
        );
        //CHECKSTYLE:OFF

        Map<String, AhtCompileSolidity.Code> compiledSolidity =
                new HashMap<String, AhtCompileSolidity.Code>(1);
        compiledSolidity.put("test", new AhtCompileSolidity.Code(
                //CHECKSTYLE:OFF
                "0x605280600c6000396000f3006000357c010000000000000000000000000000000000000000000000000000000090048063c6888fa114602e57005b60376004356041565b8060005260206000f35b6000600782029050604d565b91905056",
                //CHECKSTYLE:ON
                new AhtCompileSolidity.SolidityInfo(
                        "contract test {\n\tfunction multiply(uint a) returns(uint d) {\n"
                                + "\t\treturn a * 7;\n\t}\n}\n",
                        "Solidity",
                        "0",
                        "0.8.2",
                        "--bin --abi --userdoc --devdoc --add-std --optimize -o "
                                + "/var/folders/3m/_6gnl12n1tj_5kf7sc3d72dw0000gn/T/solc498936951",
                        Arrays.asList(new AbiDefinition(
                                false,
                                Arrays.asList(new AbiDefinition.NamedType("a", "uint256")),
                                "multiply",
                                Arrays.asList(new AbiDefinition.NamedType("d", "uint256")),
                                "function",
                                false
                        )),
                        new AhtCompileSolidity.Documentation(),
                        new AhtCompileSolidity.Documentation()
                )
        ));

        AhtCompileSolidity ahtCompileSolidity = deserialiseResponse(AhtCompileSolidity.class);
        assertThat(ahtCompileSolidity.getCompiledSolidity(), equalTo(compiledSolidity));
    }

    @Test
    public void testAhtCompileLLL() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x603880600c6000396000f3006001600060e060020a60003504806"
                        + "3c6888fa114601857005b6021600435602b565b8060005260206000f35b600081600702"
                        + "905091905056\"\n"
                        + "}"
        );

        AhtCompileLLL ahtCompileLLL = deserialiseResponse(AhtCompileLLL.class);
        assertThat(ahtCompileLLL.getCompiledSourceCode(),
                is("0x603880600c6000396000f3006001600060e060020a600035048063c6888fa114601857005b60"
                        + "21600435602b565b8060005260206000f35b600081600702905091905056"));
    }

    @Test
    public void testAhtCompileSerpent() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x603880600c6000396000f3006001600060e060020a60003504806"
                        + "3c6888fa114601857005b6021600435602b565b8060005260206000f35b600081600702"
                        + "905091905056\"\n"
                        + "}"
        );

        AhtCompileSerpent ahtCompileSerpent = deserialiseResponse(AhtCompileSerpent.class);
        assertThat(ahtCompileSerpent.getCompiledSourceCode(),
                is("0x603880600c6000396000f3006001600060e060020a600035048063c6888fa114601857005b60"
                        + "21600435602b565b8060005260206000f35b600081600702905091905056"));
    }

    @Test
    public void testahtFilter() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}"
        );

        AhtFilter AhtFilter = deserialiseResponse(AhtFilter.class);
        assertThat(AhtFilter.getFilterId(), is(BigInteger.valueOf(1)));
    }

    @Test
    public void testAhtUninstallFilter() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        AhtUninstallFilter ahtUninstallFilter = deserialiseResponse(AhtUninstallFilter.class);
        assertThat(ahtUninstallFilter.isUninstalled(), is(true));
    }

    @Test
    public void testAhtLog() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "    \"id\":1,\n"
                        + "    \"jsonrpc\":\"2.0\",\n"
                        + "    \"result\": [{\n"
                        + "        \"removed\": false,\n"
                        + "        \"logIndex\": \"0x1\",\n"
                        + "        \"transactionIndex\": \"0x0\",\n"
                        + "        \"transactionHash\": \"0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf\",\n"
                        + "        \"blockHash\": \"0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d\",\n"
                        + "        \"blockNumber\":\"0x1b4\",\n"
                        + "        \"address\": \"0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d\",\n"
                        + "        \"data\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "        \"type\":\"mined\",\n"
                        + "        \"topics\": [\"0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5\"]"
                        + "    }]"
                        + "}"
        );
        //CHECKSTYLE:ON

        List<AhtLog.LogResult> logs = Collections.<AhtLog.LogResult>singletonList(
                new AhtLog.LogObject(
                        false,
                        "0x1",
                        "0x0",
                        "0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf",
                        "0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d",
                        "0x1b4",
                        "0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d",
                        "0x0000000000000000000000000000000000000000000000000000000000000000",
                        "mined",
                        Collections.singletonList(
                                "0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5"
                        )
                )
        );

        AhtLog ahtLog = deserialiseResponse(AhtLog.class);
        assertThat(ahtLog.getLogs(), is(logs));
    }

    @Test
    public void testAhtGetWork() {
        //CHECKSTYLE:OFF
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": [\n"
                        + "      \"0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef\",\n"
                        + "      \"0x5EED00000000000000000000000000005EED0000000000000000000000000000\",\n"
                        + "      \"0xd1ff1c01710000000000000000000000d1ff1c01710000000000000000000000\"\n"
                        + "    ]\n"
                        + "}"
        );
        //CHECKSTYLE:ON

        AhtGetWork ahtGetWork = deserialiseResponse(AhtGetWork.class);
        assertThat(ahtGetWork.getCurrentBlockHeaderPowHash(),
                is("0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef"));
        assertThat(ahtGetWork.getSeedHashForDag(),
                is("0x5EED00000000000000000000000000005EED0000000000000000000000000000"));
        assertThat(ahtGetWork.getBoundaryCondition(),
                is("0xd1ff1c01710000000000000000000000d1ff1c01710000000000000000000000"));
    }

    @Test
    public void testAhtSubmitWork() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        AhtSubmitWork ahtSubmitWork = deserialiseResponse(AhtSubmitWork.class);
        assertThat(ahtSubmitWork.solutionValid(), is(true));
    }

    @Test
    public void testAhtSubmitHashrate() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        AhtSubmitHashrate ahtSubmitHashrate = deserialiseResponse(AhtSubmitHashrate.class);
        assertThat(ahtSubmitHashrate.submissionSuccessful(), is(true));
    }

    @Test
    public void testDbPutString() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        DbPutString dbPutString = deserialiseResponse(DbPutString.class);
        assertThat(dbPutString.valueStored(), is(true));
    }

    @Test
    public void testDbGetString() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": \"myString\"\n"
                        + "}"
        );

        DbGetString dbGetString = deserialiseResponse(DbGetString.class);
        assertThat(dbGetString.getStoredValue(), is("myString"));
    }

    @Test
    public void testDbPutHex() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        DbPutHex dbPutHex = deserialiseResponse(DbPutHex.class);
        assertThat(dbPutHex.valueStored(), is(true));
    }

    @Test
    public void testDbGetHex() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": \"0x68656c6c6f20776f726c64\"\n"
                        + "}"
        );

        DbGetHex dbGetHex = deserialiseResponse(DbGetHex.class);
        assertThat(dbGetHex.getStoredValue(), is("0x68656c6c6f20776f726c64"));
    }

    @Test
    public void testSshVersion() {
        buildResponse(
                "{\n"
                        + "  \"id\":67,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"2\"\n"
                        + "}"
        );

        ShhVersion shhVersion = deserialiseResponse(ShhVersion.class);
        assertThat(shhVersion.getVersion(), is("2"));
    }

    @Test
    public void testSshPost() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        ShhPost shhPost = deserialiseResponse(ShhPost.class);
        assertThat(shhPost.messageSent(), is(true));
    }

    @Test
    public void testSshNewIdentity() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": "
                        + "\"0xc931d93e97ab07fe42d923478ba2465f283f440fd6cabea4dd7a2c807108f651b713"
                        + "5d1d6ca9007d5b68aa497e4619ac10aa3b27726e1863c1fd9b570d99bbaf\"\n"
                        + "}"
        );

        ShhNewIdentity shhNewIdentity = deserialiseResponse(ShhNewIdentity.class);
        assertThat(shhNewIdentity.getAddress(),
                is("0xc931d93e97ab07fe42d923478ba2465f283f440fd6cabea4dd7a2c807108f651b7135d1d6ca9"
                        + "007d5b68aa497e4619ac10aa3b27726e1863c1fd9b570d99bbaf"));
    }

    @Test
    public void testSshHasIdentity() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        ShhHasIdentity shhHasIdentity = deserialiseResponse(ShhHasIdentity.class);
        assertThat(shhHasIdentity.hasPrivateKeyForIdentity(), is(true));
    }

    @Test
    public void testSshNewGroup() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": "
                        + "\"0xc65f283f440fd6cabea4dd7a2c807108f651b7135d1d6ca90931d93e97ab07fe42d9"
                        + "23478ba2407d5b68aa497e4619ac10aa3b27726e1863c1fd9b570d99bbaf\"\n"
                        + "}"
        );

        ShhNewGroup shhNewGroup = deserialiseResponse(ShhNewGroup.class);
        assertThat(shhNewGroup.getAddress(),
                is("0xc65f283f440fd6cabea4dd7a2c807108f651b7135d1d6ca90931d93e97ab07fe42d923478ba24"
                        + "07d5b68aa497e4619ac10aa3b27726e1863c1fd9b570d99bbaf"));
    }

    @Test
    public void testSshAddToGroup() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        ShhAddToGroup shhAddToGroup = deserialiseResponse(ShhAddToGroup.class);
        assertThat(shhAddToGroup.addedToGroup(), is(true));
    }

    @Test
    public void testSshNewFilter() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": \"0x7\"\n"
                        + "}"
        );

        ShhNewFilter shhNewFilter = deserialiseResponse(ShhNewFilter.class);
        assertThat(shhNewFilter.getFilterId(), is(BigInteger.valueOf(7)));
    }

    @Test
    public void testSshUninstallFilter() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": true\n"
                        + "}"
        );

        ShhUninstallFilter shhUninstallFilter = deserialiseResponse(ShhUninstallFilter.class);
        assertThat(shhUninstallFilter.isUninstalled(), is(true));
    }

    @Test
    public void testSshMessages() {
        buildResponse(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"result\": [{\n"
                        + "    \"hash\": \"0x33eb2da77bf3527e28f8bf493650b1879b08c4f2a362beae4ba2f"
                        + "71bafcd91f9\",\n"
                        + "    \"from\": \"0x3ec052fc33...\",\n"
                        + "    \"to\": \"0x87gdf76g8d7fgdfg...\",\n"
                        + "    \"expiry\": \"0x54caa50a\",\n"
                        + "    \"ttl\": \"0x64\",\n"
                        + "    \"sent\": \"0x54ca9ea2\",\n"
                        + "    \"topics\": [\"0x6578616d\"],\n"
                        + "    \"payload\": \"0x7b2274797065223a226d657373616765222c2263686...\",\n"
                        + "    \"workProved\": \"0x0\"\n"
                        + "    }]\n"
                        + "}"
        );

        List<ShhMessages.SshMessage> messages = Arrays.asList(
                new ShhMessages.SshMessage(
                        "0x33eb2da77bf3527e28f8bf493650b1879b08c4f2a362beae4ba2f71bafcd91f9",
                        "0x3ec052fc33...",
                        "0x87gdf76g8d7fgdfg...",
                        "0x54caa50a",
                        "0x64",
                        "0x54ca9ea2",
                        Arrays.asList("0x6578616d"),
                        "0x7b2274797065223a226d657373616765222c2263686...",
                        "0x0"
                )
        );

        ShhMessages shhMessages = deserialiseResponse(ShhMessages.class);
        assertThat(shhMessages.getMessages(), equalTo(messages));
    }
}
