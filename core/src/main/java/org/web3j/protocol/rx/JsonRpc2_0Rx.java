package org.web3j.protocol.rx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.filters.BlockFilter;
import org.web3j.protocol.core.filters.Callback;
import org.web3j.protocol.core.filters.LogFilter;
import org.web3j.protocol.core.filters.PendingTransactionFilter;
import org.web3j.protocol.core.methods.request.AhtFilter;
import org.web3j.protocol.core.methods.response.AhtBlock;
import org.web3j.protocol.core.methods.response.AhtTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Observables;

/**
 * web3j reactive API implementation.
 */
public class JsonRpc2_0Rx {

    private final Web3j web3j;
    private final ScheduledExecutorService scheduledExecutorService;
    private final Scheduler scheduler;

    public JsonRpc2_0Rx(Web3j web3j, ScheduledExecutorService scheduledExecutorService) {
        this.web3j = web3j;
        this.scheduledExecutorService = scheduledExecutorService;
        this.scheduler = Schedulers.from(scheduledExecutorService);
    }

    public Observable<String> ahtBlockHashObservable(final long pollingInterval) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                BlockFilter blockFilter = new BlockFilter(
                        web3j, new Callback<String>() {
                            @Override
                            public void onEvent(final String value) {
                                subscriber.onNext(value);
                            }
                        });
                JsonRpc2_0Rx.this.run(blockFilter, subscriber, pollingInterval);
            }
        });
    }

    public Observable<String> ahtPendingTransactionHashObservable(final long pollingInterval) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                PendingTransactionFilter pendingTransactionFilter = new PendingTransactionFilter(
                        web3j, new Callback<String>() {
                            @Override
                            public void onEvent(final String value) {
                                subscriber.onNext(value);
                            }
                        });
                JsonRpc2_0Rx.this.run(pendingTransactionFilter, subscriber, pollingInterval);
            }
        });
    }

    public Observable<Log> ahtLogObservable(
            final AhtFilter AhtFilter,
            final long pollingInterval) {
        return Observable.create(new Observable.OnSubscribe<Log>() {
            @Override
            public void call(final Subscriber<? super Log> subscriber) {
                LogFilter logFilter = new LogFilter(
                        web3j, new Callback<Log>() {
                            @Override
                            public void onEvent(final Log t) {
                                subscriber.onNext(t);
                            }
                        }, AhtFilter);

                JsonRpc2_0Rx.this.run(logFilter, subscriber, pollingInterval);
            }
        });
    }

    private <T> void run(
            final org.web3j.protocol.core.filters.Filter<T> filter,
            Subscriber<? super T> subscriber,
            final long pollingInterval) {

        filter.run(scheduledExecutorService, pollingInterval);
        subscriber.add(Subscriptions.create(new Action0() {
            @Override
            public void call() {
                filter.cancel();
            }
        }));
    }

    public Observable<Transaction>  transactionObservable(final long pollingInterval) {
        return blockObservable(true, pollingInterval)
                .flatMapIterable(new Func1<AhtBlock, Iterable<? extends Transaction>>() {
                    @Override
                    public Iterable<? extends Transaction> call(final AhtBlock ahtBlock) {
                        return JsonRpc2_0Rx.this.toTransactions(ahtBlock);
                    }
                });
    }

    public Observable<Transaction> pendingTransactionObservable(final long pollingInterval) {
        return ahtPendingTransactionHashObservable(pollingInterval)
                .flatMap(new Func1<String, Observable<AhtTransaction>>() {
                    @Override
                    public Observable<AhtTransaction> call(final String transactionHash) {
                        return web3j.ahtGetTransactionByHash(transactionHash).observable();
                    }
                })
                .map(new Func1<AhtTransaction, Transaction>() {
                    @Override
                    public Transaction call(final AhtTransaction ahtTransaction) {
                        return ahtTransaction.getTransaction();
                    }
                });
    }

    public Observable<AhtBlock> blockObservable(
            final boolean fullTransactionObjects, final long pollingInterval) {
        return this.ahtBlockHashObservable(pollingInterval)
                .flatMap(new Func1<String, Observable<? extends AhtBlock>>() {
                    @Override
                    public Observable<? extends AhtBlock> call(final String blockHash) {
                        return web3j.ahtGetBlockByHash(blockHash,
                                fullTransactionObjects).observable();
                    }
                });
    }

    public Observable<AhtBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return replayBlocksObservable(startBlock, endBlock, fullTransactionObjects, true);
    }

    public Observable<AhtBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {
        // We use a scheduler to ensure this Observable runs asynchronously for users to be
        // consistent with the other Observables
        return replayBlocksObservableSync(startBlock, endBlock, fullTransactionObjects, ascending)
                .subscribeOn(scheduler);
    }

    private Observable<AhtBlock> replayBlocksObservableSync(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            final boolean fullTransactionObjects) {
        return replayBlocksObservableSync(startBlock, endBlock, fullTransactionObjects, true);
    }

    private Observable<AhtBlock> replayBlocksObservableSync(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            final boolean fullTransactionObjects, boolean ascending) {

        BigInteger startBlockNumber = null;
        BigInteger endBlockNumber = null;
        try {
            startBlockNumber = getBlockNumber(startBlock);
            endBlockNumber = getBlockNumber(endBlock);
        } catch (IOException e) {
            Observable.error(e);
        }

        if (ascending) {
            return Observables.range(startBlockNumber, endBlockNumber)
                    .flatMap(new Func1<BigInteger, Observable<? extends AhtBlock>>() {
                        @Override
                        public Observable<? extends AhtBlock> call(BigInteger i) {
                            return web3j.ahtGetBlockByNumber(
                                    new DefaultBlockParameterNumber(i),
                                    fullTransactionObjects).observable();
                        }
                    });
        } else {
            return Observables.range(startBlockNumber, endBlockNumber, false)
                    .flatMap(new Func1<BigInteger, Observable<? extends AhtBlock>>() {
                        @Override
                        public Observable<? extends AhtBlock> call(BigInteger i) {
                            return web3j.ahtGetBlockByNumber(
                                    new DefaultBlockParameterNumber(i),
                                    fullTransactionObjects).observable();
                        }
                    });
        }


    }

    public Observable<Transaction> replayTransactionsObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return replayBlocksObservable(startBlock, endBlock, true)
                .flatMapIterable(new Func1<AhtBlock, Iterable<? extends Transaction>>() {
                    @Override
                    public Iterable<? extends Transaction> call(AhtBlock ahtBlock) {
                        return toTransactions(ahtBlock);
                    }
                });
    }

    public Observable<AhtBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Observable<AhtBlock> onCompleteObservable) {
        // We use a scheduler to ensure this Observable runs asynchronously for users to be
        // consistent with the other Observables
        return catchUpToLatestBlockObservableSync(
                startBlock, fullTransactionObjects, onCompleteObservable)
                .subscribeOn(scheduler);
    }

    public Observable<AhtBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return catchUpToLatestBlockObservable(
                startBlock, fullTransactionObjects, Observable.<AhtBlock>empty());
    }

    private Observable<AhtBlock> catchUpToLatestBlockObservableSync(
            DefaultBlockParameter startBlock, final boolean fullTransactionObjects,
            final Observable<AhtBlock> onCompleteObservable) {

        BigInteger startBlockNumber;
        final BigInteger latestBlockNumber;
        try {
            startBlockNumber = getBlockNumber(startBlock);
            latestBlockNumber = getLatestBlockNumber();
        } catch (IOException e) {
            return Observable.error(e);
        }

        if (startBlockNumber.compareTo(latestBlockNumber) > -1) {
            return onCompleteObservable;
        } else {
            return Observable.concat(
                    replayBlocksObservableSync(
                            new DefaultBlockParameterNumber(startBlockNumber),
                            new DefaultBlockParameterNumber(latestBlockNumber),
                            fullTransactionObjects),
                    Observable.defer(new Func0<Observable<AhtBlock>>() {
                        @Override
                        public Observable<AhtBlock> call() {
                            return JsonRpc2_0Rx.this.catchUpToLatestBlockObservableSync(
                                    new DefaultBlockParameterNumber(
                                            latestBlockNumber.add(BigInteger.ONE)),
                                    fullTransactionObjects,
                                    onCompleteObservable);
                        }
                    }));
        }
    }

    public Observable<Transaction> catchUpToLatestTransactionObservable(
            DefaultBlockParameter startBlock) {
        return catchUpToLatestBlockObservable(
                startBlock, true, Observable.<AhtBlock>empty())
                .flatMapIterable(new Func1<AhtBlock, Iterable<? extends Transaction>>() {
                    @Override
                    public Iterable<? extends Transaction> call(AhtBlock ahtBlock) {
                        return toTransactions(ahtBlock);
                    }
                });
    }

    public Observable<AhtBlock> catchUpToLatestAndSubscribeToNewBlocksObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            long pollingInterval) {

        return catchUpToLatestBlockObservable(
                startBlock, fullTransactionObjects,
                blockObservable(fullTransactionObjects, pollingInterval));
    }

    public Observable<Transaction> catchUpToLatestAndSubscribeToNewTransactionsObservable(
            DefaultBlockParameter startBlock, long pollingInterval) {
        return catchUpToLatestAndSubscribeToNewBlocksObservable(
                startBlock, true, pollingInterval)
                .flatMapIterable(new Func1<AhtBlock, Iterable<? extends Transaction>>() {
                    @Override
                    public Iterable<? extends Transaction> call(AhtBlock ahtBlock) {
                        return toTransactions(ahtBlock);
                    }
                });
    }

    private BigInteger getLatestBlockNumber() throws IOException {
        return getBlockNumber(DefaultBlockParameterName.LATEST);
    }

    private BigInteger getBlockNumber(
            DefaultBlockParameter defaultBlockParameter) throws IOException {
        if (defaultBlockParameter instanceof DefaultBlockParameterNumber) {
            return ((DefaultBlockParameterNumber) defaultBlockParameter).getBlockNumber();
        } else {
            AhtBlock latestAhtBlock = web3j.ahtGetBlockByNumber(
                    defaultBlockParameter, false).send();
            return latestAhtBlock.getBlock().getNumber();
        }
    }

    private static List<Transaction> toTransactions(AhtBlock ahtBlock) {
        // If you ever see an exception thrown here, it's probably due to an incomplete chain in
        // Gaht/Parity. You should resync to solve.
        List<AhtBlock.TransactionResult> transactionResults = ahtBlock.getBlock().getTransactions();
        List<Transaction> transactions = new ArrayList<Transaction>(transactionResults.size());

        for (AhtBlock.TransactionResult transactionResult : transactionResults) {
            transactions.add((Transaction) transactionResult.get());
        }
        return transactions;
    }
}
