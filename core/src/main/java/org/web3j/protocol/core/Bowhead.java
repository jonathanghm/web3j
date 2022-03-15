package org.web3j.protocol.core;

import java.math.BigInteger;

import org.web3j.protocol.core.methods.request.ShhFilter;
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
import org.web3j.protocol.core.methods.response.AhtSign;
import org.web3j.protocol.core.methods.response.AhtSubmitHashrate;
import org.web3j.protocol.core.methods.response.AhtSubmitWork;
import org.web3j.protocol.core.methods.response.AhtSyncing;
import org.web3j.protocol.core.methods.response.AhtTransaction;
import org.web3j.protocol.core.methods.response.AhtUninstallFilter;
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

/**
 * Core Bowhead JSON-RPC API.
 */
public interface Bowhead {
    Request<?, Web3ClientVersion> web3ClientVersion();

    Request<?, Web3Sha3> web3Sha3(String data);

    Request<?, NetVersion> netVersion();

    Request<?, NetListening> netListening();

    Request<?, NetPeerCount> netPeerCount();

    Request<?, AhtProtocolVersion> ahtProtocolVersion();

    Request<?, AhtCoinbase> ahtCoinbase();

    Request<?, AhtSyncing> ahtSyncing();

    Request<?, AhtMining> ahtMining();

    Request<?, AhtHashrate> ahtHashrate();

    Request<?, AhtGasPrice> ahtGasPrice();

    Request<?, AhtAccounts> ahtAccounts();

    Request<?, AhtBlockNumber> ahtBlockNumber();

    Request<?, AhtGetBalance> ahtGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, AhtGetStorageAt> ahtGetStorageAt(
            String address, BigInteger position,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, AhtGetTransactionCount> ahtGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, AhtGetBlockTransactionCountByHash> ahtGetBlockTransactionCountByHash(
            String blockHash);

    Request<?, AhtGetBlockTransactionCountByNumber> ahtGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, AhtGetUncleCountByBlockHash> ahtGetUncleCountByBlockHash(String blockHash);

    Request<?, AhtGetUncleCountByBlockNumber> ahtGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, AhtGetCode> ahtGetCode(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, AhtSign> ahtSign(String address, String sha3HashOfDataToSign);

    Request<?, org.web3j.protocol.core.methods.response.AhtSendTransaction> ahtSendTransaction(
            org.web3j.protocol.core.methods.request.Transaction transaction);

    Request<?, org.web3j.protocol.core.methods.response.AhtSendTransaction> ahtSendRawTransaction(
            String signedTransactionData);

    Request<?, org.web3j.protocol.core.methods.response.AhtCall> ahtCall(
            org.web3j.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, AhtEstimateGas> ahtEstimateGas(
            org.web3j.protocol.core.methods.request.Transaction transaction);

    Request<?, AhtBlock> ahtGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, AhtBlock> ahtGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects);

    Request<?, AhtTransaction> ahtGetTransactionByHash(String transactionHash);

    Request<?, AhtTransaction> ahtGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, AhtTransaction> ahtGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, AhtGetTransactionReceipt> ahtGetTransactionReceipt(String transactionHash);

    Request<?, AhtBlock> ahtGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, AhtBlock> ahtGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, AhtGetCompilers> ahtGetCompilers();

    Request<?, AhtCompileLLL> ahtCompileLLL(String sourceCode);

    Request<?, AhtCompileSolidity> ahtCompileSolidity(String sourceCode);

    Request<?, AhtCompileSerpent> ahtCompileSerpent(String sourceCode);

    Request<?, AhtFilter> ahtNewFilter(org.web3j.protocol.core.methods.request.AhtFilter AhtFilter);

    Request<?, AhtFilter> ahtNewBlockFilter();

    Request<?, AhtFilter> ahtNewPendingTransactionFilter();

    Request<?, AhtUninstallFilter> ahtUninstallFilter(BigInteger filterId);

    Request<?, AhtLog> ahtGetFilterChanges(BigInteger filterId);

    Request<?, AhtLog> ahtGetFilterLogs(BigInteger filterId);

    Request<?, AhtLog> ahtGetLogs(org.web3j.protocol.core.methods.request.AhtFilter AhtFilter);

    Request<?, AhtGetWork> ahtGetWork();

    Request<?, AhtSubmitWork> ahtSubmitWork(String nonce, String headerPowHash, String mixDigest);

    Request<?, AhtSubmitHashrate> ahtSubmitHashrate(String hashrate, String clientId);

    Request<?, DbPutString> dbPutString(String databaseName, String keyName, String stringToStore);

    Request<?, DbGetString> dbGetString(String databaseName, String keyName);

    Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore);

    Request<?, DbGetHex> dbGetHex(String databaseName, String keyName);

    Request<?, org.web3j.protocol.core.methods.response.ShhPost> shhPost(
            org.web3j.protocol.core.methods.request.ShhPost shhPost);

    Request<?, ShhVersion> shhVersion();

    Request<?, ShhNewIdentity> shhNewIdentity();

    Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress);

    Request<?, ShhNewGroup> shhNewGroup();

    Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress);

    Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter);

    Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId);

    Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId);

    Request<?, ShhMessages> shhGetMessages(BigInteger filterId);
}
