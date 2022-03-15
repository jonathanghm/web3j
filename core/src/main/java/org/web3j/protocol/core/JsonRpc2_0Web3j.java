package org.web3j.protocol.core;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;

import rx.Observable;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.request.ShhFilter;
import org.web3j.protocol.core.methods.request.ShhPost;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.AhtFilter;
import org.web3j.protocol.core.methods.response.DbGetHex;
import org.web3j.protocol.core.methods.response.DbGetString;
import org.web3j.protocol.core.methods.response.DbPutHex;
import org.web3j.protocol.core.methods.response.DbPutString;
import org.web3j.protocol.core.methods.response.AhtAccounts;
import org.web3j.protocol.core.methods.response.AhtBlock;
import org.web3j.protocol.core.methods.response.AhtBlockNumber;
import org.web3j.protocol.core.methods.response.AhtCoinbase;
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
import org.web3j.protocol.core.methods.response.ShhUninstallFilter;
import org.web3j.protocol.core.methods.response.ShhVersion;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.protocol.rx.JsonRpc2_0Rx;
import org.web3j.utils.Async;
import org.web3j.utils.Numeric;

/**
 * JSON-RPC 2.0 factory implementation.
 */
public class JsonRpc2_0Web3j implements Web3j {

    public static final int DEFAULT_BLOCK_TIME = 15 * 1000;

    protected final Web3jService web3jService;
    private final JsonRpc2_0Rx web3jRx;
    private final long blockTime;

    public JsonRpc2_0Web3j(Web3jService web3jService) {
        this(web3jService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService());
    }

    public JsonRpc2_0Web3j(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        this.web3jService = web3jService;
        this.web3jRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
    }

    @Override
    public Request<?, Web3ClientVersion> web3ClientVersion() {
        return new Request<String, Web3ClientVersion>(
                "web3_clientVersion",
                Collections.<String>emptyList(),
                web3jService,
                Web3ClientVersion.class);
    }

    @Override
    public Request<?, Web3Sha3> web3Sha3(String data) {
        return new Request<String, Web3Sha3>(
                "web3_sha3",
                Arrays.asList(data),
                web3jService,
                Web3Sha3.class);
    }

    @Override
    public Request<?, NetVersion> netVersion() {
        return new Request<String, NetVersion>(
                "net_version",
                Collections.<String>emptyList(),
                web3jService,
                NetVersion.class);
    }

    @Override
    public Request<?, NetListening> netListening() {
        return new Request<String, NetListening>(
                "net_listening",
                Collections.<String>emptyList(),
                web3jService,
                NetListening.class);
    }

    @Override
    public Request<?, NetPeerCount> netPeerCount() {
        return new Request<String, NetPeerCount>(
                "net_peerCount",
                Collections.<String>emptyList(),
                web3jService,
                NetPeerCount.class);
    }

    @Override
    public Request<?, AhtProtocolVersion> ahtProtocolVersion() {
        return new Request<String, AhtProtocolVersion>(
                "aht_protocolVersion",
                Collections.<String>emptyList(),
                web3jService,
                AhtProtocolVersion.class);
    }

    @Override
    public Request<?, AhtCoinbase> ahtCoinbase() {
        return new Request<String, AhtCoinbase>(
                "aht_coinbase",
                Collections.<String>emptyList(),
                web3jService,
                AhtCoinbase.class);
    }

    @Override
    public Request<?, AhtSyncing> ahtSyncing() {
        return new Request<String, AhtSyncing>(
                "aht_syncing",
                Collections.<String>emptyList(),
                web3jService,
                AhtSyncing.class);
    }

    @Override
    public Request<?, AhtMining> ahtMining() {
        return new Request<String, AhtMining>(
                "aht_mining",
                Collections.<String>emptyList(),
                web3jService,
                AhtMining.class);
    }

    @Override
    public Request<?, AhtHashrate> ahtHashrate() {
        return new Request<String, AhtHashrate>(
                "aht_hashrate",
                Collections.<String>emptyList(),
                web3jService,
                AhtHashrate.class);
    }

    @Override
    public Request<?, AhtGasPrice> ahtGasPrice() {
        return new Request<String, AhtGasPrice>(
                "aht_gasPrice",
                Collections.<String>emptyList(),
                web3jService,
                AhtGasPrice.class);
    }

    @Override
    public Request<?, AhtAccounts> ahtAccounts() {
        return new Request<String, AhtAccounts>(
                "aht_accounts",
                Collections.<String>emptyList(),
                web3jService,
                AhtAccounts.class);
    }

    @Override
    public Request<?, AhtBlockNumber> ahtBlockNumber() {
        return new Request<String, AhtBlockNumber>(
                "aht_blockNumber",
                Collections.<String>emptyList(),
                web3jService,
                AhtBlockNumber.class);
    }

    @Override
    public Request<?, AhtGetBalance> ahtGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<String, AhtGetBalance>(
                "aht_getBalance",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                AhtGetBalance.class);
    }

    @Override
    public Request<?, AhtGetStorageAt> ahtGetStorageAt(
            String address, BigInteger position, DefaultBlockParameter defaultBlockParameter) {
        return new Request<String, AhtGetStorageAt>(
                "aht_getStorageAt",
                Arrays.asList(
                        address,
                        Numeric.encodeQuantity(position),
                        defaultBlockParameter.getValue()),
                web3jService,
                AhtGetStorageAt.class);
    }

    @Override
    public Request<?, AhtGetTransactionCount> ahtGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<String, AhtGetTransactionCount>(
                "aht_getTransactionCount",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                AhtGetTransactionCount.class);
    }

    @Override
    public Request<?, AhtGetBlockTransactionCountByHash> ahtGetBlockTransactionCountByHash(
            String blockHash) {
        return new Request<String, AhtGetBlockTransactionCountByHash>(
                "aht_getBlockTransactionCountByHash",
                Arrays.asList(blockHash),
                web3jService,
                AhtGetBlockTransactionCountByHash.class);
    }

    @Override
    public Request<?, AhtGetBlockTransactionCountByNumber> ahtGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<String, AhtGetBlockTransactionCountByNumber>(
                "aht_getBlockTransactionCountByNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                web3jService,
                AhtGetBlockTransactionCountByNumber.class);
    }

    @Override
    public Request<?, AhtGetUncleCountByBlockHash> ahtGetUncleCountByBlockHash(String blockHash) {
        return new Request<String, AhtGetUncleCountByBlockHash>(
                "aht_getUncleCountByBlockHash",
                Arrays.asList(blockHash),
                web3jService,
                AhtGetUncleCountByBlockHash.class);
    }

    @Override
    public Request<?, AhtGetUncleCountByBlockNumber> ahtGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<String, AhtGetUncleCountByBlockNumber>(
                "aht_getUncleCountByBlockNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                web3jService,
                AhtGetUncleCountByBlockNumber.class);
    }

    @Override
    public Request<?, AhtGetCode> ahtGetCode(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<String, AhtGetCode>(
                "aht_getCode",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                AhtGetCode.class);
    }

    @Override
    public Request<?, AhtSign> ahtSign(String address, String sha3HashOfDataToSign) {
        return new Request<String, AhtSign>(
                "aht_sign",
                Arrays.asList(address, sha3HashOfDataToSign),
                web3jService,
                AhtSign.class);
    }

    @Override
    public Request<?, org.web3j.protocol.core.methods.response.AhtSendTransaction>
            ahtSendTransaction(
            Transaction transaction) {
        return new Request<Transaction, AhtSendTransaction>(
                "aht_sendTransaction",
                Arrays.asList(transaction),
                web3jService,
                org.web3j.protocol.core.methods.response.AhtSendTransaction.class);
    }

    @Override
    public Request<?, org.web3j.protocol.core.methods.response.AhtSendTransaction>
            ahtSendRawTransaction(
            String signedTransactionData) {
        return new Request<String, AhtSendTransaction>(
                "aht_sendRawTransaction",
                Arrays.asList(signedTransactionData),
                web3jService,
                org.web3j.protocol.core.methods.response.AhtSendTransaction.class);
    }

    @Override
    public Request<?, org.web3j.protocol.core.methods.response.AhtCall> ahtCall(
            Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "aht_call",
                Arrays.asList(transaction, defaultBlockParameter),
                web3jService,
                org.web3j.protocol.core.methods.response.AhtCall.class);
    }

    @Override
    public Request<?, AhtEstimateGas> ahtEstimateGas(Transaction transaction) {
        return new Request<Transaction, AhtEstimateGas>(
                "aht_estimateGas",
                Arrays.asList(transaction),
                web3jService,
                AhtEstimateGas.class);
    }

    @Override
    public Request<?, AhtBlock> ahtGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<Object, AhtBlock>(
                "aht_getBlockByHash",
                Arrays.<Object>asList(
                        blockHash,
                        returnFullTransactionObjects),
                web3jService,
                AhtBlock.class);
    }

    @Override
    public Request<?, AhtBlock> ahtGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects) {
        return new Request<Object, AhtBlock>(
                "aht_getBlockByNumber",
                Arrays.<Object>asList(
                        defaultBlockParameter.getValue(),
                        returnFullTransactionObjects),
                web3jService,
                AhtBlock.class);
    }

    @Override
    public Request<?, AhtTransaction> ahtGetTransactionByHash(String transactionHash) {
        return new Request<String, AhtTransaction>(
                "aht_getTransactionByHash",
                Arrays.asList(transactionHash),
                web3jService,
                AhtTransaction.class);
    }

    @Override
    public Request<?, AhtTransaction> ahtGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<String, AhtTransaction>(
                "aht_getTransactionByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                AhtTransaction.class);
    }

    @Override
    public Request<?, AhtTransaction> ahtGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) {
        return new Request<String, AhtTransaction>(
                "aht_getTransactionByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                AhtTransaction.class);
    }

    @Override
    public Request<?, AhtGetTransactionReceipt> ahtGetTransactionReceipt(String transactionHash) {
        return new Request<String, AhtGetTransactionReceipt>(
                "aht_getTransactionReceipt",
                Arrays.asList(transactionHash),
                web3jService,
                AhtGetTransactionReceipt.class);
    }

    @Override
    public Request<?, AhtBlock> ahtGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<String, AhtBlock>(
                "aht_getUncleByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                AhtBlock.class);
    }

    @Override
    public Request<?, AhtBlock> ahtGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger uncleIndex) {
        return new Request<String, AhtBlock>(
                "aht_getUncleByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(uncleIndex)),
                web3jService,
                AhtBlock.class);
    }

    @Override
    public Request<?, AhtGetCompilers> ahtGetCompilers() {
        return new Request<String, AhtGetCompilers>(
                "aht_getCompilers",
                Collections.<String>emptyList(),
                web3jService,
                AhtGetCompilers.class);
    }

    @Override
    public Request<?, AhtCompileLLL> ahtCompileLLL(String sourceCode) {
        return new Request<String, AhtCompileLLL>(
                "aht_compileLLL",
                Arrays.asList(sourceCode),
                web3jService,
                AhtCompileLLL.class);
    }

    @Override
    public Request<?, AhtCompileSolidity> ahtCompileSolidity(String sourceCode) {
        return new Request<String, AhtCompileSolidity>(
                "aht_compileSolidity",
                Arrays.asList(sourceCode),
                web3jService,
                AhtCompileSolidity.class);
    }

    @Override
    public Request<?, AhtCompileSerpent> ahtCompileSerpent(String sourceCode) {
        return new Request<String, AhtCompileSerpent>(
                "aht_compileSerpent",
                Arrays.asList(sourceCode),
                web3jService,
                AhtCompileSerpent.class);
    }


    @Override
    public Request<?, AhtFilter> ahtNewFilter(
            org.web3j.protocol.core.methods.request.AhtFilter AhtFilter) {
        return new Request<org.web3j.protocol.core.methods.request.AhtFilter, org.web3j.protocol.core.methods.response.AhtFilter>(
                "aht_newFilter",
                Arrays.asList(AhtFilter),
                web3jService,
                org.web3j.protocol.core.methods.response.AhtFilter.class);
    }

    @Override
    public Request<?, AhtFilter> ahtNewBlockFilter() {
        return new Request<String, AhtFilter>(
                "aht_newBlockFilter",
                Collections.<String>emptyList(),
                web3jService,
                AhtFilter.class);
    }

    @Override
    public Request<?, AhtFilter> ahtNewPendingTransactionFilter() {
        return new Request<String, AhtFilter>(
                "aht_newPendingTransactionFilter",
                Collections.<String>emptyList(),
                web3jService,
                AhtFilter.class);
    }

    @Override
    public Request<?, AhtUninstallFilter> ahtUninstallFilter(BigInteger filterId) {
        return new Request<String, AhtUninstallFilter>(
                "aht_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                AhtUninstallFilter.class);
    }

    @Override
    public Request<?, AhtLog> ahtGetFilterChanges(BigInteger filterId) {
        return new Request<String, AhtLog>(
                "aht_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                AhtLog.class);
    }

    @Override
    public Request<?, AhtLog> ahtGetFilterLogs(BigInteger filterId) {
        return new Request<String, AhtLog>(
                "aht_getFilterLogs",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                AhtLog.class);
    }

    @Override
    public Request<?, AhtLog> ahtGetLogs(
            org.web3j.protocol.core.methods.request.AhtFilter AhtFilter) {
        return new Request<org.web3j.protocol.core.methods.request.AhtFilter, AhtLog>(
                "aht_getLogs",
                Arrays.asList(AhtFilter),
                web3jService,
                AhtLog.class);
    }

    @Override
    public Request<?, AhtGetWork> ahtGetWork() {
        return new Request<String, AhtGetWork>(
                "aht_getWork",
                Collections.<String>emptyList(),
                web3jService,
                AhtGetWork.class);
    }

    @Override
    public Request<?, AhtSubmitWork> ahtSubmitWork(
            String nonce, String headerPowHash, String mixDigest) {
        return new Request<String, AhtSubmitWork>(
                "aht_submitWork",
                Arrays.asList(nonce, headerPowHash, mixDigest),
                web3jService,
                AhtSubmitWork.class);
    }

    @Override
    public Request<?, AhtSubmitHashrate> ahtSubmitHashrate(String hashrate, String clientId) {
        return new Request<String, AhtSubmitHashrate>(
                "aht_submitHashrate",
                Arrays.asList(hashrate, clientId),
                web3jService,
                AhtSubmitHashrate.class);
    }

    @Override
    public Request<?, DbPutString> dbPutString(
            String databaseName, String keyName, String stringToStore) {
        return new Request<String, DbPutString>(
                "db_putString",
                Arrays.asList(databaseName, keyName, stringToStore),
                web3jService,
                DbPutString.class);
    }

    @Override
    public Request<?, DbGetString> dbGetString(String databaseName, String keyName) {
        return new Request<String, DbGetString>(
                "db_getString",
                Arrays.asList(databaseName, keyName),
                web3jService,
                DbGetString.class);
    }

    @Override
    public Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore) {
        return new Request<String, DbPutHex>(
                "db_putHex",
                Arrays.asList(databaseName, keyName, dataToStore),
                web3jService,
                DbPutHex.class);
    }

    @Override
    public Request<?, DbGetHex> dbGetHex(String databaseName, String keyName) {
        return new Request<String, DbGetHex>(
                "db_getHex",
                Arrays.asList(databaseName, keyName),
                web3jService,
                DbGetHex.class);
    }

    @Override
    public Request<?, org.web3j.protocol.core.methods.response.ShhPost> shhPost(ShhPost shhPost) {
        return new Request<ShhPost, org.web3j.protocol.core.methods.response.ShhPost>(
                "shh_post",
                Arrays.asList(shhPost),
                web3jService,
                org.web3j.protocol.core.methods.response.ShhPost.class);
    }

    @Override
    public Request<?, ShhVersion> shhVersion() {
        return new Request<String, ShhVersion>(
                "shh_version",
                Collections.<String>emptyList(),
                web3jService,
                ShhVersion.class);
    }

    @Override
    public Request<?, ShhNewIdentity> shhNewIdentity() {
        return new Request<String, ShhNewIdentity>(
                "shh_newIdentity",
                Collections.<String>emptyList(),
                web3jService,
                ShhNewIdentity.class);
    }

    @Override
    public Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress) {
        return new Request<String, ShhHasIdentity>(
                "shh_hasIdentity",
                Arrays.asList(identityAddress),
                web3jService,
                ShhHasIdentity.class);
    }

    @Override
    public Request<?, ShhNewGroup> shhNewGroup() {
        return new Request<String, ShhNewGroup>(
                "shh_newGroup",
                Collections.<String>emptyList(),
                web3jService,
                ShhNewGroup.class);
    }

    @Override
    public Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress) {
        return new Request<String, ShhAddToGroup>(
                "shh_addToGroup",
                Arrays.asList(identityAddress),
                web3jService,
                ShhAddToGroup.class);
    }

    @Override
    public Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter) {
        return new Request<ShhFilter, ShhNewFilter>(
                "shh_newFilter",
                Arrays.asList(shhFilter),
                web3jService,
                ShhNewFilter.class);
    }

    @Override
    public Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId) {
        return new Request<String, ShhUninstallFilter>(
                "shh_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhUninstallFilter.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId) {
        return new Request<String, ShhMessages>(
                "shh_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhMessages.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetMessages(BigInteger filterId) {
        return new Request<String, ShhMessages>(
                "shh_getMessages",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhMessages.class);
    }


    @Override
    public Observable<String> ahtBlockHashObservable() {
        return web3jRx.ahtBlockHashObservable(blockTime);
    }

    @Override
    public Observable<String> ahtPendingTransactionHashObservable() {
        return web3jRx.ahtPendingTransactionHashObservable(blockTime);
    }

    @Override
    public Observable<Log> ahtLogObservable(
            org.web3j.protocol.core.methods.request.AhtFilter AhtFilter) {
        return web3jRx.ahtLogObservable(AhtFilter, blockTime);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            transactionObservable() {
        return web3jRx.transactionObservable(blockTime);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            pendingTransactionObservable() {
        return web3jRx.pendingTransactionObservable(blockTime);
    }

    @Override
    public Observable<AhtBlock> blockObservable(boolean fullTransactionObjects) {
        return web3jRx.blockObservable(fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<AhtBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return web3jRx.replayBlocksObservable(startBlock, endBlock, fullTransactionObjects);
    }

    @Override
    public Observable<AhtBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {
        return web3jRx.replayBlocksObservable(startBlock, endBlock,
                fullTransactionObjects, ascending);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            replayTransactionsObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return web3jRx.replayTransactionsObservable(startBlock, endBlock);
    }

    @Override
    public Observable<AhtBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Observable<AhtBlock> onCompleteObservable) {
        return web3jRx.catchUpToLatestBlockObservable(
                startBlock, fullTransactionObjects, onCompleteObservable);
    }

    @Override
    public Observable<AhtBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.catchUpToLatestBlockObservable(startBlock, fullTransactionObjects);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            catchUpToLatestTransactionObservable(DefaultBlockParameter startBlock) {
        return web3jRx.catchUpToLatestTransactionObservable(startBlock);
    }

    @Override
    public Observable<AhtBlock> catchUpToLatestAndSubscribeToNewBlocksObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.catchUpToLatestAndSubscribeToNewBlocksObservable(
                startBlock, fullTransactionObjects, blockTime);
    }



    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            catchUpToLatestAndSubscribeToNewTransactionsObservable(
            DefaultBlockParameter startBlock) {
        return web3jRx.catchUpToLatestAndSubscribeToNewTransactionsObservable(
                startBlock, blockTime);
    }
}
