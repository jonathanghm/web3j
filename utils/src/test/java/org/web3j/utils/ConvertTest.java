package org.web3j.utils;

import java.math.BigDecimal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ConvertTest {

    @Test
    public void testFromCell() {
        assertThat(Convert.fromCell("21000000000000", Convert.Unit.CELL),
                is(new BigDecimal("21000000000000")));
        assertThat(Convert.fromCell("21000000000000", Convert.Unit.KCELL),
                is(new BigDecimal("21000000000")));
        assertThat(Convert.fromCell("21000000000000", Convert.Unit.MCELL),
                is(new BigDecimal("21000000")));
        assertThat(Convert.fromCell("21000000000000", Convert.Unit.GCELL),
                is(new BigDecimal("21000")));
//        assertThat(Convert.fromCell("21000000000000", Convert.Unit.SZABO),
//                is(new BigDecimal("21")));
//        assertThat(Convert.fromCell("21000000000000", Convert.Unit.FINNEY),
//                is(new BigDecimal("0.021")));
        assertThat(Convert.fromCell("21000000000000", Convert.Unit.AHT),
                is(new BigDecimal("0.000021")));
//        assertThat(Convert.fromCell("21000000000000", Convert.Unit.KAHT),
//                is(new BigDecimal("0.000000021")));
//        assertThat(Convert.fromCell("21000000000000", Convert.Unit.MAHT),
//                is(new BigDecimal("0.000000000021")));
//        assertThat(Convert.fromCell("21000000000000", Convert.Unit.GAHT),
//                is(new BigDecimal("0.000000000000021")));
    }

    @Test
    public void testToCell() {
        assertThat(Convert.toCell("21", Convert.Unit.CELL), is(new BigDecimal("21")));
        assertThat(Convert.toCell("21", Convert.Unit.KCELL), is(new BigDecimal("21000")));
        assertThat(Convert.toCell("21", Convert.Unit.MCELL), is(new BigDecimal("21000000")));
        assertThat(Convert.toCell("21", Convert.Unit.GCELL), is(new BigDecimal("21000000000")));
//        assertThat(Convert.toCell("21", Convert.Unit.SZABO), is(new BigDecimal("21000000000000")));
//        assertThat(Convert.toCell("21", Convert.Unit.FINNEY),
//                is(new BigDecimal("21000000000000000")));
//        assertThat(Convert.toCell("21", Convert.Unit.AHT),
//                is(new BigDecimal("21000000000000000000")));
//        assertThat(Convert.toCell("21", Convert.Unit.KAHT),
//                is(new BigDecimal("21000000000000000000000")));
//        assertThat(Convert.toCell("21", Convert.Unit.MAHT),
//                is(new BigDecimal("21000000000000000000000000")));
//        assertThat(Convert.toCell("21", Convert.Unit.GAHT),
//                is(new BigDecimal("21000000000000000000000000000")));
    }

//    @Test
//    public void testUnit() {
//        assertThat(Convert.Unit.fromString("aht"), is(Convert.Unit.AHT));
//        assertThat(Convert.Unit.fromString("AHT"), is(Convert.Unit.AHT));
//        assertThat(Convert.Unit.fromString("wei"), is(Convert.Unit.CELL));
//    }
}
