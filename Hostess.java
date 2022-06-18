package order;
import java.util.Random;

public class Hostess {
private Tables tables;
	
	public Hostess(int numTables) {
		this.tables = new Tables(numTables);
	}
	
	public Table seatCustomer(CustomerThread customerThread) {
		Table table = null;
		try {
			Resturant.addMessage("Hostess is trying to seat customer " + customerThread.getCustomerNumber());
			/* Here, customer is about to wait for a table... add him to waiting label*/
			Resturant.addCustomerToWaitingLabel(customerThread.getCustomerNumber());
			table = tables.getTable();
			
			WaiterThread waiter = Resturant.getWaiterFactory().getWaiter();
			waiter.setTable(table);
			table.seatTable(customerThread, waiter);
			
			// changes 
			Random rand = new Random();
			int randomOrder = rand.nextInt(2);
			Order order = new Order( randomOrder, customerThread.getCustomerNumber(), table, waiter);
			Resturant.cookFactory.appendNewOrder(order);
			waiter.setOrder(order);
			customerThread.setOrder(order);
			
			/* Here, customer is seated */
			Resturant.addCustomerToSeatedLabel(customerThread.getCustomerNumber());
			Resturant.addWaiterMessage("Customer " + customerThread.getCustomerNumber() + " is seated at table " + table.getTableNumber(), waiter.getWaiterNumber());
			
			Resturant.addWaiterMessage("Waiter " + waiter.getWaiterNumber() + " has taken an order for  " + order.getOrderText() +" from table "+ order.getTable().getTableNumber(), waiter.getWaiterNumber());
			
			Resturant.addMessage("Hostess seated customer " + customerThread.getCustomerNumber() + " at table " + table.getTableNumber() + " with waiter " + waiter.getWaiterNumber());
		
			
		} catch (InterruptedException ie) {
			System.out.println("HostessThread.seatCustomer():InterruptedException: " + ie.getMessage());
		}
		return table;
	}
	
	public void customerLeaving(CustomerThread customerThread) {
		Resturant.addCustomerToLeavingLabel(customerThread.getCustomerNumber());
		Resturant.addWaiterMessage("Customer " + customerThread.getCustomerNumber() + " is done eating and is leaving.", customerThread.getTable().getWaiterThread().getWaiterNumber());
		Resturant.addMessage("Customer " + customerThread.getCustomerNumber() + " is done eating and is leaving.");
		customerThread.getTable().getWaiterThread().returnTable(customerThread.getTable());
		tables.returnTable(customerThread.getTable());
	}

}


