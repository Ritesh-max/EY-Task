package org.afob;

import java.math.BigDecimal;

import org.afob.execution.ExecutionClient;
import org.afob.limit.LimitOrderAgent;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
ExecutionClient ec = new ExecutionClient();
        
        LimitOrderAgent agent = new LimitOrderAgent(ec);
        
        agent.setTargetProductId("AAPL");
        agent.setLimitPrice(new BigDecimal("150.00"));
        agent.setAmount(10);  // Ensure the amount is set
        
        // Simulate a price update for buying
        agent.priceTick("AAPL", new BigDecimal("155.00")); // This should trigger a buy order
        
        // Simulate a price update for selling
        agent.priceTick("AAPL", new BigDecimal("145.00")); 
	}
	
}
