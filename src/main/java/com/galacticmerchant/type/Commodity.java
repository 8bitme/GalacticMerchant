package com.galacticmerchant.type;

public class Commodity {
    private final String name;
    private final Currency currency;
    private final int value;

    public Commodity(String name, Currency currency, int value) {
        this.name = name;
        this.currency = currency;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commodity commodity = (Commodity) o;

        if (value != commodity.value) return false;
        if (name != null ? !name.equals(commodity.name) : commodity.name != null) return false;
        return currency != null ? currency.equals(commodity.currency) : commodity.currency == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + value;
        return result;
    }
}
