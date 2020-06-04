package org.oop18.models;

import javafx.scene.image.Image;
import org.oop18.entities.Product;
import org.oop18.entities.TravelCode;
import org.oop18.exceptions.QueryException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author - Haribo
 */
public class StubProductAdapter implements ProductAdapter {
    @Override
    public Product queryProduct(Integer id) throws QueryException {
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new Product(id, randomId, "title "+randomId, "productKey "+randomId, randomId,
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), randomId, randomId);
    }

    @Override
    public List<Product> queryProducts(TravelCode travelCode, Timestamp startDate) throws QueryException {
        List<Product> productList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            Random rand = new Random();
            Integer randomId = rand.nextInt(50);
            randomId += 1;
            Product product = new Product(i, randomId, "title "+randomId, "productKey "+randomId, randomId,
                    new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), randomId, randomId);
            productList.add(product);
        }

        if (travelCode != null) {
            productList = productList.stream()
                                .filter((Product product) -> (product.getTravelCodeId().equals(travelCode.getId())))
                                .collect(Collectors.toList());
        }
        if (startDate != null) {
            productList = productList.stream()
                                .filter((Product product) -> (product.getStartDate().equals(startDate)))
                                .collect(Collectors.toList());
        }

        return productList;
    }


    @Override
    public List<TravelCode> queryTravelCodes() throws QueryException {
        List<TravelCode> travelCodeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            Integer randomId = rand.nextInt(50);
            randomId += 1;
            TravelCode travelCode = new TravelCode(randomId, randomId, "travel code name "+randomId);
            travelCodeList.add(travelCode);
        }
        return travelCodeList;
    }

    @Override
    public TravelCode queryTravelCode(String travelCodeName) throws QueryException {
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        TravelCode travelCode = new TravelCode(randomId, randomId, travelCodeName);
        return travelCode;
    }
}