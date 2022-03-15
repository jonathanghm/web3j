package org.web3j.protocol.core.methods.response;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;

import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.Response;

/**
 * Transaction object returned by:
 * <ul>
 * <li>aht_getTransactionByHash</li>
 * <li>aht_getTransactionByBlockHashAndIndex</li>
 * <li>aht_getTransactionByBlockNumberAndIndex</li>
 * </ul>
 *
 * <p>This differs slightly from the request {@link AhtSendTransaction} Transaction object.</p>
 *
 * <p>See
 * <a href="https://github.com/bowhead/wiki/wiki/JSON-RPC#aht_gettransactionbyhash">docs</a>
 * for further details.</p>
 */
public class AhtTransaction extends Response<Transaction> {

    public Transaction getTransaction() {
        return getResult();
    }

    public static class ResponseDeserialiser extends JsonDeserializer<Transaction> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Transaction deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Transaction.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
