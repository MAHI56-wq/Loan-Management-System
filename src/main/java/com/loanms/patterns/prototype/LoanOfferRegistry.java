package com.loanms.patterns.prototype;

import java.util.HashMap;
import java.util.Map;

public class LoanOfferRegistry {
    private final Map<String, LoanOffer> offers = new HashMap<>();

    public LoanOfferRegistry() {
        offers.put("Personal", new LoanOffer("Personal", 12, 48, 1500));
        offers.put("Home", new LoanOffer("Home", 9, 240, 5000));
        offers.put("Business", new LoanOffer("Business", 14, 84, 3000));
    }

    public LoanOffer createCopy(String type) {
        LoanOffer offer = offers.get(type);

        if (offer == null) {
            throw new IllegalArgumentException("Unknown offer type");
        }

        return offer.copy();
    }

    public void register(String type, LoanOffer offer) {
        offers.put(type, offer);
    }
}
