package org.web3j.ens;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.web3j.ens.NameHash.nameHash;
import static org.web3j.ens.NameHash.normalise;

public class NameHashTest {

//    @Test
//    public void testNameHash() {
//        assertThat(nameHash(""),
//                is("0x0000000000000000000000000000000000000000000000000000000000000000"));
//
//        assertThat(nameHash("aht"),
//                is("0x93cdeb708b7545dc668eb9280176169d1c33cfd8ed6f04690a0bcc88a93fc4ae"));
//
//        assertThat(nameHash("foo.aht"),
//                is("0xde9b09fd7c5f901e23a3f19fecc54828e9c848539801e86591bd9801b019f84f"));
//    }

    @Test
    public void testNormalise() {
        assertThat(normalise("foo"), is("foo"));
        assertThat(normalise("foo.bar.baz.aht"), is("foo.bar.baz.aht"));
        assertThat(normalise("fOo.aht"), is("foo.aht"));
        assertThat(normalise("foo-bar.aht"), is("foo-bar.aht"));
    }

    @Test
    public void testNormaliseInvalid() {
        testInvalidName("foo..bar");
        testInvalidName("ba\\u007Fr.aht");
        testInvalidName("-baz.aht-");
        testInvalidName("foo_bar.aht");
    }

    private void testInvalidName(String ensName) {
        try {
            normalise(ensName);
            fail("Name should not be valid");
        } catch (EnsResolutionException e) {
            // expected
        }
    }
}
