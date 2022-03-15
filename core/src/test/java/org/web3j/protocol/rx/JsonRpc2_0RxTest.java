package org.web3j.protocol.rx;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.AhtBlock;
import org.web3j.protocol.core.methods.response.AhtFilter;
import org.web3j.protocol.core.methods.response.AhtLog;
import org.web3j.protocol.core.methods.response.AhtUninstallFilter;
import org.web3j.utils.Numeric;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonRpc2_0RxTest {

    private final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

    private Web3j web3j;

    private Web3jService web3jService;

    @Before
    public void setUp() {
        web3jService = mock(Web3jService.class);
        web3j = Web3jFactory.build(
                web3jService, 1000, Executors.newSingleThreadScheduledExecutor());
    }

    @Test
    public void testReplayBlocksObservable() throws Exception {

        List<AhtBlock> ahtBlocks = Arrays.asList(createBlock(0), createBlock(1), createBlock(2));

        OngoingStubbing<AhtBlock> stubbing =
                when(web3jService.send(any(Request.class), eq(AhtBlock.class)));
        for (AhtBlock ahtBlock : ahtBlocks) {
            stubbing = stubbing.thenReturn(ahtBlock);
        }

        Observable<AhtBlock> observable = web3j.replayBlocksObservable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                new DefaultBlockParameterNumber(BigInteger.valueOf(2)),
                false);

        final CountDownLatch transactionLatch = new CountDownLatch(ahtBlocks.size());
        final CountDownLatch completedLatch = new CountDownLatch(1);

        final List<AhtBlock> results = new ArrayList<AhtBlock>(ahtBlocks.size());
        Subscription subscription = observable.subscribe(
                new Action1<AhtBlock>() {
                    @Override
                    public void call(AhtBlock result) {
                        results.add(result);
                        transactionLatch.countDown();
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        fail(throwable.getMessage());
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        completedLatch.countDown();
                    }
                });

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(ahtBlocks));

        subscription.unsubscribe();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isUnsubscribed());
    }

    @Test
    public void testReplayBlocksDescendingObservable() throws Exception {

        List<AhtBlock> ahtBlocks = Arrays.asList(createBlock(2), createBlock(1), createBlock(0));

        OngoingStubbing<AhtBlock> stubbing =
                when(web3jService.send(any(Request.class), eq(AhtBlock.class)));
        for (AhtBlock ahtBlock : ahtBlocks) {
            stubbing = stubbing.thenReturn(ahtBlock);
        }

        Observable<AhtBlock> observable = web3j.replayBlocksObservable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                new DefaultBlockParameterNumber(BigInteger.valueOf(2)),
                false, false);

        final CountDownLatch transactionLatch = new CountDownLatch(ahtBlocks.size());
        final CountDownLatch completedLatch = new CountDownLatch(1);

        final List<AhtBlock> results = new ArrayList<AhtBlock>(ahtBlocks.size());
        Subscription subscription = observable.subscribe(
                new Action1<AhtBlock>() {
                    @Override
                    public void call(AhtBlock result) {
                        results.add(result);
                        transactionLatch.countDown();
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        fail(throwable.getMessage());
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        completedLatch.countDown();
                    }
                });

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(ahtBlocks));

        subscription.unsubscribe();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isUnsubscribed());
    }

//    @Test
//    public void testCatchUpToLatestAndSubscribeToNewBlockObservable() throws Exception {
//        List<AhtBlock> expected = Arrays.asList(
//                createBlock(0), createBlock(1), createBlock(2),
//                createBlock(3), createBlock(4), createBlock(5),
//                createBlock(6));
//
//        List<AhtBlock> ahtBlocks = Arrays.asList(
//                expected.get(2),  // greatest block
//                expected.get(0), expected.get(1), expected.get(2),
//                expected.get(4), // greatest block
//                expected.get(3), expected.get(4),
//                expected.get(4),  // greatest block
//                expected.get(5),  // initial response from ahtGetFilterLogs call
//                expected.get(6)); // subsequent block from new block observable
//
//        OngoingStubbing<AhtBlock> stubbing =
//                when(web3jService.send(any(Request.class), eq(AhtBlock.class)));
//        for (AhtBlock ahtBlock : ahtBlocks) {
//            stubbing = stubbing.thenReturn(ahtBlock);
//        }
//
//        AhtFilter AhtFilter = objectMapper.readValue(
//                "{\n"
//                        + "  \"id\":1,\n"
//                        + "  \"jsonrpc\": \"2.0\",\n"
//                        + "  \"result\": \"0x1\"\n"
//                        + "}", AhtFilter.class);
//        AhtLog ahtLog = objectMapper.readValue(
//                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":["
//                        + "\"0x31c2342b1e0b8ffda1507fbffddf213c4b3c1e819ff6a84b943faabb0ebf2403\""
//                        + "]}",
//                AhtLog.class);
//        AhtUninstallFilter ahtUninstallFilter = objectMapper.readValue(
//                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}", AhtUninstallFilter.class);
//
//        when(web3jService.send(any(Request.class), eq(AhtFilter.class)))
//                .thenReturn(AhtFilter);
//        when(web3jService.send(any(Request.class), eq(AhtLog.class)))
//                .thenReturn(ahtLog);
//        when(web3jService.send(any(Request.class), eq(AhtUninstallFilter.class)))
//                .thenReturn(ahtUninstallFilter);
//
//        Observable<AhtBlock> observable = web3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
//                new DefaultBlockParameterNumber(BigInteger.ZERO),
//                false);
//
//        final CountDownLatch transactionLatch = new CountDownLatch(expected.size());
//        final CountDownLatch completedLatch = new CountDownLatch(1);
//
//        final List<AhtBlock> results = new ArrayList<AhtBlock>(expected.size());
//        Subscription subscription = observable.subscribe(
//                new Action1<AhtBlock>() {
//                    @Override
//                    public void call(AhtBlock result) {
//                        results.add(result);
//                        transactionLatch.countDown();
//                    }
//                },
//                new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        fail(throwable.getMessage());
//                    }
//                },
//                new Action0() {
//                    @Override
//                    public void call() {
//                        completedLatch.countDown();
//                    }
//                });
//
//        transactionLatch.await(1250, TimeUnit.MILLISECONDS);
//        assertThat(results, equalTo(expected));
//
//        subscription.unsubscribe();
//
//        completedLatch.await(1, TimeUnit.SECONDS);
//        assertTrue(subscription.isUnsubscribed());
//    }

    private AhtBlock createBlock(int number) {
        AhtBlock ahtBlock = new AhtBlock();
        AhtBlock.Block block = new AhtBlock.Block();
        block.setNumber(Numeric.encodeQuantity(BigInteger.valueOf(number)));

        ahtBlock.setResult(block);
        return ahtBlock;
    }
}
