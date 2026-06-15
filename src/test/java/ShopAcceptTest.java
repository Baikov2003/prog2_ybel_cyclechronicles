import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cyclechronicles.Order;
import cyclechronicles.Shop;
import cyclechronicles.Type;
import org.junit.jupiter.api.Test;

class ShopAcceptTest {

    private static Order order(Type type, String customer) {
        Order order = mock(Order.class);

        when(order.getBicycleType()).thenReturn(type);
        when(order.getCustomer()).thenReturn(customer);

        return order;
    }

    @Test
    void acceptsRaceBikeWhenQueueIsEmpty() {
        Shop shop = new Shop();

        Order order = order(Type.RACE, "Alice");

        assertTrue(shop.accept(order));
    }

    @Test
    void acceptsSingleSpeedBikeWhenQueueIsEmpty() {
        Shop shop = new Shop();

        Order order = order(Type.SINGLE_SPEED, "Bob");

        assertTrue(shop.accept(order));
    }

    @Test
    void acceptsFixieBikeWhenQueueIsEmpty() {
        Shop shop = new Shop();

        Order order = order(Type.FIXIE, "Charlie");

        assertTrue(shop.accept(order));
    }

    @Test
    void rejectsGravelBike() {
        Shop shop = new Shop();

        Order order = order(Type.GRAVEL, "Alice");

        assertFalse(shop.accept(order));
    }

    @Test
    void rejectsEBike() {
        Shop shop = new Shop();

        Order order = order(Type.EBIKE, "Alice");

        assertFalse(shop.accept(order));
    }

    @Test
    void rejectsOrderWhenCustomerAlreadyHasPendingOrder() {
        Shop shop = new Shop();

        Order firstOrder = order(Type.RACE, "Alice");
        Order secondOrder = order(Type.FIXIE, "Alice");

        assertTrue(shop.accept(firstOrder));
        assertFalse(shop.accept(secondOrder));
    }

    @Test
    void acceptsOrderWhenFourOrdersAreAlreadyPending() {
        Shop shop = new Shop();

        assertTrue(shop.accept(order(Type.RACE, "Customer 1")));
        assertTrue(shop.accept(order(Type.SINGLE_SPEED, "Customer 2")));
        assertTrue(shop.accept(order(Type.FIXIE, "Customer 3")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 4")));

        Order fifthOrder = order(Type.SINGLE_SPEED, "Customer 5");

        assertTrue(shop.accept(fifthOrder));
    }

    @Test
    void rejectsOrderWhenFiveOrdersAreAlreadyPending() {
        Shop shop = new Shop();

        assertTrue(shop.accept(order(Type.RACE, "Customer 1")));
        assertTrue(shop.accept(order(Type.SINGLE_SPEED, "Customer 2")));
        assertTrue(shop.accept(order(Type.FIXIE, "Customer 3")));
        assertTrue(shop.accept(order(Type.RACE, "Customer 4")));
        assertTrue(shop.accept(order(Type.SINGLE_SPEED, "Customer 5")));

        Order sixthOrder = order(Type.FIXIE, "Customer 6");

        assertFalse(shop.accept(sixthOrder));
    }
}
