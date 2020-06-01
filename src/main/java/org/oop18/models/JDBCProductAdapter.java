package org.oop18.models;

import org.oop18.entities.Product;
import org.oop18.entities.TravelCode;
import org.oop18.exceptions.QueryException;

import java.sql.Timestamp;
import java.util.List;


public class JDBCProductAdapter implements ProductAdapter {
    @Override
    public List<Product> queryProducts(TravelCode travelCode, Timestamp startDate) throws QueryException {
        return null;
    }
}
