package henrique.igor.storefront.service;

import henrique.igor.storefront.dto.StockStatusMessage;

public interface IProductChangeAvailabilityConsumer {

    void receive(final StockStatusMessage message);
}
