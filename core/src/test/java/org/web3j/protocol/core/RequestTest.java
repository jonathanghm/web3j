package org.web3j.protocol.core;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

import org.web3j.protocol.RequestTester;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.request.AhtFilter;
import org.web3j.protocol.core.methods.request.ShhFilter;
import org.web3j.protocol.core.methods.request.ShhPost;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

public class RequestTest extends RequestTester {

    private Web3j web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Web3jFactory.build(httpService);
    }

//    @Test
//    public void testWeb3ClientVersion() throws Exception {
//        web3j.web3ClientVersion().send();
//
//        verifyResult(
//                "{\"jsonrpc\":\"2.0\",\".ahtod\":\"web3_clientVersion\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testWeb3Sha3() throws Exception {
//        web3j.web3Sha3("0x68656c6c6f20776f726c64").send();
//
//        verifyResult(
//                "{\"jsonrpc\":\"2.0\",\".ahtod\":\"web3_sha3\","
//                        + "\"params\":[\"0x68656c6c6f20776f726c64\"],\"id\":1}");
//    }
//
//    @Test
//    public void testNetVersion() throws Exception {
//        web3j.netVersion().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"net_version\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testNetListening() throws Exception {
//        web3j.netListening().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"net_listening\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testNetPeerCount() throws Exception {
//        web3j.netPeerCount().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"net_peerCount\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtProtocolVersion() throws Exception {
//        web3j.ahtProtocolVersion().send();
//
//        verifyResult(
//                "{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_protocolVersion\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtSyncing() throws Exception {
//        web3j.ahtSyncing().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_syncing\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtCoinbase() throws Exception {
//        web3j.ahtCoinbase().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_coinbase\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtMining() throws Exception {
//        web3j.ahtMining().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_mining\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtHashrate() throws Exception {
//        web3j.ahtHashrate().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_hashrate\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGasPrice() throws Exception {
//        web3j.ahtGasPrice().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_gasPrice\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtAccounts() throws Exception {
//        web3j.ahtAccounts().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_accounts\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtBlockNumber() throws Exception {
//        web3j.ahtBlockNumber().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_blockNumber\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetBalance() throws Exception {
//        web3j.ahtGetBalance("0x407d73d8a49eeb85d32cf465507dd71d507100c1",
//                DefaultBlockParameterName.LATEST).send();
//
//        verifyResult(
//                "{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getBalance\","
//                        + "\"params\":[\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\"latest\"],"
//                        + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetStorageAt() throws Exception {
//        web3j.ahtGetStorageAt("0x295a70b2de5e3953354a6a8344e616ed314d7251", BigInteger.ZERO,
//                DefaultBlockParameterName.LATEST).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getStorageAt\","
//                + "\"params\":[\"0x295a70b2de5e3953354a6a8344e616ed314d7251\",\"0x0\",\"latest\"],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetTransactionCount() throws Exception {
//        web3j.ahtGetTransactionCount("0x407d73d8a49eeb85d32cf465507dd71d507100c1",
//                DefaultBlockParameterName.LATEST).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getTransactionCount\","
//                + "\"params\":[\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\"latest\"],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetBlockTransactionCountByHash() throws Exception {
//        web3j.ahtGetBlockTransactionCountByHash(
//                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();
//
//        //CHECKSTYLE:OFF
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getBlockTransactionCountByHash\",\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//    @Test
//    public void testAhtGetBlockTransactionCountByNumber() throws Exception {
//        web3j.ahtGetBlockTransactionCountByNumber(
//                DefaultBlockParameterNumber.valueOf(Numeric.toBigInt("0xe8"))).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getBlockTransactionCountByNumber\","
//                + "\"params\":[\"0xe8\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetUncleCountByBlockHash() throws Exception {
//        web3j.ahtGetUncleCountByBlockHash(
//                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();
//
//        //CHECKSTYLE:OFF
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getUncleCountByBlockHash\",\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//    @Test
//    public void testAhtGetUncleCountByBlockNumber() throws Exception {
//        web3j.ahtGetUncleCountByBlockNumber(
//                DefaultBlockParameterNumber.valueOf(Numeric.toBigInt("0xe8"))).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getUncleCountByBlockNumber\","
//                + "\"params\":[\"0xe8\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetCode() throws Exception {
//        web3j.ahtGetCode("0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b",
//                DefaultBlockParameterNumber.valueOf(Numeric.toBigInt("0x2"))).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getCode\","
//                + "\"params\":[\"0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b\",\"0x2\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtSign() throws Exception {
//        web3j.ahtSign("0x8a3106a3e50576d4b6794a0e74d3bb5f8c9acaab",
//                "0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_sign\","
//                + "\"params\":[\"0x8a3106a3e50576d4b6794a0e74d3bb5f8c9acaab\","
//                + "\"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470\"],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtSendTransaction() throws Exception {
//        web3j.ahtSendTransaction(new Transaction(
//                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
//                BigInteger.ONE,
//                Numeric.toBigInt("0x9184e72a000"),
//                Numeric.toBigInt("0x76c0"),
//                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
//                Numeric.toBigInt("0x9184e72a"),
//                "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb"
//                        + "970870f072445675058bb8eb970870f072445675")).send();
//
//        //CHECKSTYLE:OFF
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_sendTransaction\",\"params\":[{\"from\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"to\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"gas\":\"0x76c0\",\"gasPrice\":\"0x9184e72a000\",\"value\":\"0x9184e72a\",\"data\":\"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\",\"nonce\":\"0x1\"}],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//    @Test
//    public void testAhtSendRawTransaction() throws Exception {
//        web3j.ahtSendRawTransaction(
//                "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f"
//                        + "072445675058bb8eb970870f072445675").send();
//
//        //CHECKSTYLE:OFF
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_sendRawTransaction\",\"params\":[\"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\"],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//
//    @Test
//    public void testAhtCall() throws Exception {
//        web3j.ahtCall(Transaction.createAhtCallTransaction(
//                "0xa70e8dd61c5d32be8058bb8eb970870f07233155",
//                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
//                        "0x0"),
//                DefaultBlockParameterName.fromString("latest")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_call\","
//                + "\"params\":[{\"from\":\"0xa70e8dd61c5d32be8058bb8eb970870f07233155\","
//                + "\"to\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"data\":\"0x0\"},"
//                + "\"latest\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtEstimateGas() throws Exception {
//        web3j.ahtEstimateGas(
//                Transaction.createAhtCallTransaction(
//                        "0xa70e8dd61c5d32be8058bb8eb970870f07233155",
//                        "0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f", "0x0")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_estimateGas\","
//                + "\"params\":[{\"from\":\"0xa70e8dd61c5d32be8058bb8eb970870f07233155\","
//                + "\"to\":\"0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f\",\"data\":\"0x0\"}],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtEstimateGasContractCreation() throws Exception {
//        web3j.ahtEstimateGas(
//                Transaction.createContractTransaction(
//                        "0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f", BigInteger.ONE,
//                        BigInteger.TEN, "")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_estimateGas\","
//                + "\"params\":[{\"from\":\"0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f\","
//                + "\"gasPrice\":\"0xa\",\"data\":\"0x\",\"nonce\":\"0x1\"}],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetBlockByHash() throws Exception {
//        web3j.ahtGetBlockByHash(
//                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331", true).send();
//
//        verifyResult(
//                "{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getBlockByHash\",\"params\":["
//                        + "\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\""
//                        + ",true],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetBlockByNumber() throws Exception {
//        web3j.ahtGetBlockByNumber(
//                DefaultBlockParameterNumber.valueOf(Numeric.toBigInt("0x1b4")), true).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getBlockByNumber\","
//                + "\"params\":[\"0x1b4\",true],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetTransactionByHash() throws Exception {
//        web3j.ahtGetTransactionByHash(
//                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getTransactionByHash\",\"params\":["
//                + "\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetTransactionByBlockHashAndIndex() throws Exception {
//        web3j.ahtGetTransactionByBlockHashAndIndex(
//                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
//                BigInteger.ZERO).send();
//
//        //CHECKSTYLE:OFF
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getTransactionByBlockHashAndIndex\",\"params\":[\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\"0x0\"],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//    @Test
//    public void testAhtGetTransactionByBlockNumberAndIndex() throws Exception {
//        web3j.ahtGetTransactionByBlockNumberAndIndex(
//                DefaultBlockParameterNumber.valueOf(Numeric.toBigInt("0x29c")),
//                BigInteger.ZERO).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getTransactionByBlockNumberAndIndex\","
//                + "\"params\":[\"0x29c\",\"0x0\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetTransactionReceipt() throws Exception {
//        web3j.ahtGetTransactionReceipt(
//                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getTransactionReceipt\",\"params\":["
//                + "\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetUncleByBlockHashAndIndex() throws Exception {
//        web3j.ahtGetUncleByBlockHashAndIndex(
//                "0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
//                BigInteger.ZERO).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getUncleByBlockHashAndIndex\","
//                + "\"params\":["
//                + "\"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b\",\"0x0\"],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetUncleByBlockNumberAndIndex() throws Exception {
//        web3j.ahtGetUncleByBlockNumberAndIndex(
//                DefaultBlockParameterNumber.valueOf(Numeric.toBigInt("0x29c")),
//                BigInteger.ZERO).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getUncleByBlockNumberAndIndex\","
//                + "\"params\":[\"0x29c\",\"0x0\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetCompilers() throws Exception {
//        web3j.ahtGetCompilers().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getCompilers\","
//                + "\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtCompileSolidity() throws Exception {
//        web3j.ahtCompileSolidity(
//                "contract test { function multiply(uint a) returns(uint d) {   return a * 7;   } }")
//                .send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_compileSolidity\","
//                + "\"params\":[\"contract test { function multiply(uint a) returns(uint d) {"
//                + "   return a * 7;   } }\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtCompileLLL() throws Exception {
//        web3j.ahtCompileLLL("(returnlll (suicide (caller)))").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_compileLLL\","
//                + "\"params\":[\"(returnlll (suicide (caller)))\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtCompileSerpent() throws Exception {
//        web3j.ahtCompileSerpent("/* some serpent */").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_compileSerpent\","
//                + "\"params\":[\"/* some serpent */\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtNewFilter() throws Exception {
//        AhtFilter AhtFilter = new AhtFilter()
//                .addSingleTopic("0x12341234");
//
//        web3j.ahtNewFilter(AhtFilter).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_newFilter\","
//                + "\"params\":[{\"topics\":[\"0x12341234\"]}],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtNewBlockFilter() throws Exception {
//        web3j.ahtNewBlockFilter().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_newBlockFilter\","
//                + "\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtNewPendingTransactionFilter() throws Exception {
//        web3j.ahtNewPendingTransactionFilter().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_newPendingTransactionFilter\","
//                + "\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtUninstallFilter() throws Exception {
//        web3j.ahtUninstallFilter(Numeric.toBigInt("0xb")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_uninstallFilter\","
//                + "\"params\":[\"0x0b\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetFilterChanges() throws Exception {
//        web3j.ahtGetFilterChanges(Numeric.toBigInt("0x16")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getFilterChanges\","
//                + "\"params\":[\"0x16\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetFilterLogs() throws Exception {
//        web3j.ahtGetFilterLogs(Numeric.toBigInt("0x16")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getFilterLogs\","
//                + "\"params\":[\"0x16\"],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetLogs() throws Exception {
//        web3j.ahtGetLogs(new AhtFilter().addSingleTopic(
//                "0x000000000000000000000000a94f5374fce5edbc8e2a8697c15331677e6ebf0b"))
//                .send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getLogs\","
//                + "\"params\":[{\"topics\":["
//                + "\"0x000000000000000000000000a94f5374fce5edbc8e2a8697c15331677e6ebf0b\"]}],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetLogsWithNumericBlockRange() throws Exception {
//        web3j.ahtGetLogs(new AhtFilter(
//                DefaultBlockParameterNumber.valueOf(Numeric.toBigInt("0xe8")),
//                DefaultBlockParameterName.LATEST, ""))
//                .send();
//
//        verifyResult(
//                "{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getLogs\","
//                        + "\"params\":[{\"topics\":[],\"fromBlock\":\"0xe8\","
//                        + "\"toBlock\":\"latest\",\"address\":[\"\"]}],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtGetWork() throws Exception {
//        web3j.ahtGetWork().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_getWork\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testAhtSubmitWork() throws Exception {
//        web3j.ahtSubmitWork("0x0000000000000001",
//                "0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef",
//                "0xD1FE5700000000000000000000000000D1FE5700000000000000000000000000").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_submitWork\","
//                + "\"params\":[\"0x0000000000000001\","
//                + "\"0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef\","
//                + "\"0xD1FE5700000000000000000000000000D1FE5700000000000000000000000000\"],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testAhtSubmitHashRate() throws Exception {
//        web3j.ahtSubmitHashrate(
//                "0x0000000000000000000000000000000000000000000000000000000000500000",
//                "0x59daa26581d0acd1fce254fb7e85952f4c09d0915afd33d3886cd914bc7d283c").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"aht_submitHashrate\","
//                + "\"params\":["
//                + "\"0x0000000000000000000000000000000000000000000000000000000000500000\","
//                + "\"0x59daa26581d0acd1fce254fb7e85952f4c09d0915afd33d3886cd914bc7d283c\"],"
//                + "\"id\":1}");
//    }
//
//    @Test
//    public void testDbPutString() throws Exception {
//        web3j.dbPutString("testDB", "myKey", "myString").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"db_putString\","
//                + "\"params\":[\"testDB\",\"myKey\",\"myString\"],\"id\":1}");
//    }
//
//    @Test
//    public void testDbGetString() throws Exception {
//        web3j.dbGetString("testDB", "myKey").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"db_getString\","
//                + "\"params\":[\"testDB\",\"myKey\"],\"id\":1}");
//    }
//
//    @Test
//    public void testDbPutHex() throws Exception {
//        web3j.dbPutHex("testDB", "myKey", "0x68656c6c6f20776f726c64").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"db_putHex\","
//                + "\"params\":[\"testDB\",\"myKey\",\"0x68656c6c6f20776f726c64\"],\"id\":1}");
//    }
//
//    @Test
//    public void testDbGetHex() throws Exception {
//        web3j.dbGetHex("testDB", "myKey").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"db_getHex\","
//                + "\"params\":[\"testDB\",\"myKey\"],\"id\":1}");
//    }
//
//    @Test
//    public void testShhVersion() throws Exception {
//        web3j.shhVersion().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_version\","
//                + "\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testShhPost() throws Exception {
//        //CHECKSTYLE:OFF
//        web3j.shhPost(new ShhPost(
//                "0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1",
//                "0x3e245533f97284d442460f2998cd41858798ddf04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a0d4d661997d3940272b717b1",
//                Arrays.asList("0x776869737065722d636861742d636c69656e74", "0x4d5a695276454c39425154466b61693532"),
//                "0x7b2274797065223a226d6",
//                Numeric.toBigInt("0x64"),
//                Numeric.toBigInt("0x64"))).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_post\",\"params\":[{\"from\":\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\",\"to\":\"0x3e245533f97284d442460f2998cd41858798ddf04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a0d4d661997d3940272b717b1\",\"topics\":[\"0x776869737065722d636861742d636c69656e74\",\"0x4d5a695276454c39425154466b61693532\"],\"payload\":\"0x7b2274797065223a226d6\",\"priority\":\"0x64\",\"ttl\":\"0x64\"}],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//    @Test
//    public void testShhNewIdentity() throws Exception {
//        web3j.shhNewIdentity().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_newIdentity\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testShhHasIdentity() throws Exception {
//        //CHECKSTYLE:OFF
//        web3j.shhHasIdentity("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_hasIdentity\",\"params\":[\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//    @Test
//    public void testShhNewGroup() throws Exception {
//        web3j.shhNewGroup().send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_newGroup\",\"params\":[],\"id\":1}");
//    }
//
//    @Test
//    public void testShhAddToGroup() throws Exception {
//        //CHECKSTYLE:OFF
//        web3j.shhAddToGroup("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1").send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_addToGroup\",\"params\":[\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//    @Test
//    public void testShhNewFilter() throws Exception {
//        //CHECKSTYLE:OFF
//        web3j.shhNewFilter(
//                new ShhFilter("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1")
//                        .addSingleTopic("0x12341234bf4b564f")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_newFilter\",\"params\":[{\"topics\":[\"0x12341234bf4b564f\"],\"to\":\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"}],\"id\":1}");
//        //CHECKSTYLE:ON
//    }
//
//    @Test
//    public void testShhUninstallFilter() throws Exception {
//        web3j.shhUninstallFilter(Numeric.toBigInt("0x7")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_uninstallFilter\","
//                + "\"params\":[\"0x07\"],\"id\":1}");
//    }
//
//    @Test
//    public void testShhGetFilterChanges() throws Exception {
//        web3j.shhGetFilterChanges(Numeric.toBigInt("0x7")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_getFilterChanges\","
//                + "\"params\":[\"0x07\"],\"id\":1}");
//    }
//
//    @Test
//    public void testShhGetMessages() throws Exception {
//        web3j.shhGetMessages(Numeric.toBigInt("0x7")).send();
//
//        verifyResult("{\"jsonrpc\":\"2.0\",\".ahtod\":\"shh_getMessages\","
//                + "\"params\":[\"0x07\"],\"id\":1}");
//    }

}
