package org.web3j.protocol.gaht;

import java.util.Arrays;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.admin.methods.response.PersonalSign;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.gaht.response.PersonalEcRecover;
import org.web3j.protocol.gaht.response.PersonalImportRawKey;

/**
 * JSON-RPC 2.0 factory implementation for Gaht.
 */
class JsonRpc2_0Gaht extends JsonRpc2_0Admin implements Gaht {

    public JsonRpc2_0Gaht(Web3jService web3jService) {
        super(web3jService);
    }
    
    @Override
    public Request<?, PersonalImportRawKey> personalImportRawKey(
            String keydata, String password) {
        return new Request<String, PersonalImportRawKey>(
                "personal_importRawKey",
                Arrays.asList(keydata, password),
                web3jService,
                PersonalImportRawKey.class);
    }

    @Override
    public Request<?, BooleanResponse> personalLockAccount(String accountId) {
        return new Request<String, BooleanResponse>(
                "personal_lockAccount",
                Arrays.asList(accountId),
                web3jService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, PersonalSign> personalSign(
            String message, String accountId, String password) {
        return new Request<String, PersonalSign>(
                "personal_sign",
                Arrays.asList(message,accountId,password),
                web3jService,
                PersonalSign.class);
    }

    @Override
    public Request<?, PersonalEcRecover> personalEcRecover(
            String hexMessage, String signedMessage) {
        return new Request<String, PersonalEcRecover>(
                "personal_ecRecover",
                Arrays.asList(hexMessage,signedMessage),
                web3jService,
                PersonalEcRecover.class);
    } 
    
}
