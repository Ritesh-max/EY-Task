package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.afob.execution.ExecutionClient.ExecutionException;
import org.afob.prices.PriceListener;

import java.math.BigDecimal;

public class LimitOrderAgent implements PriceListener {

    private final ExecutionClient executionClient;
    private String targetProductId;
    private BigDecimal limitPrice;
    private int amount;

    public LimitOrderAgent(final ExecutionClient ec) {
        this.executionClient = ec;
    }

    public void setTargetProductId(String productId) {
        this.targetProductId = productId;
    }

    public void setLimitPrice(BigDecimal limitPrice) {
        this.limitPrice = limitPrice;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void priceTick(String productId, BigDecimal price) {
        if (targetProductId == null || limitPrice == null || amount <= 0) {
            System.out.println("Target product, limit price, or amount not set.");
            return;
        }

        if (productId.equals(targetProductId)) {
            try {
                if (price.compareTo(limitPrice) >= 0) {
                    // If price is equal to or greater than the limit price: Buy
                    executionClient.buy(productId, amount);
                    System.out.println("Buy order placed for " + productId + " at price " + price);
                } else {
                    // If price is below the limit price: Sell
                    executionClient.sell(productId, amount);
                    System.out.println("Sell order placed for " + productId + " at price " + price);
                }
            } catch (ExecutionException e) {
                System.err.println("Order execution failed: " + e.getMessage());
            }
        }
    }
}
