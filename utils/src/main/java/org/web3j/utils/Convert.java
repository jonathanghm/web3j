package org.web3j.utils;

import java.math.BigDecimal;

/**
 * Bowhead unit conversion functions.
 */
public final class Convert {
    private Convert() { }

    public static BigDecimal fromCell(String number, Unit unit) {
        return fromCell(new BigDecimal(number), unit);
    }

    public static BigDecimal fromCell(BigDecimal number, Unit unit) {
        return number.divide(unit.getCellFactor());
    }

    public static BigDecimal toCell(String number, Unit unit) {
        return toCell(new BigDecimal(number), unit);
    }

    public static BigDecimal toCell(BigDecimal number, Unit unit) {
        return number.multiply(unit.getCellFactor());
    }

    public enum Unit {
        CELL("cell", 0),
        KCELL("kcell", 3),
        MCELL("mcell", 6),
        GCELL("gcell", 9),
        ORGAN("organ", 9),
        KORGAN("korgan", 12),
        MORGAN("morgan", 15),
        GORGAN("gorgan", 18),
        AHT("aht", 18),
        DEFAULT("morgan", 15),
        ;
        
        private String name;
        private BigDecimal cellFactor;

        Unit(String name, int factor) {
            this.name = name;
            this.cellFactor = BigDecimal.TEN.pow(factor);
        }

        public BigDecimal getCellFactor() {
            return cellFactor;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Unit fromString(String name) {
            if (name != null) {
                for (Unit unit : Unit.values()) {
                    if (name.equalsIgnoreCase(unit.name)) {
                        return unit;
                    }
                }
            }
            return Unit.valueOf(name);
        }
    }
}
