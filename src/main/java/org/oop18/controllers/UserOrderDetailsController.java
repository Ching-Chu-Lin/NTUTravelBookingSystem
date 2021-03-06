package org.oop18.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.oop18.entities.Order;
import org.oop18.entities.Product;
import org.oop18.entities.User;
import org.oop18.exceptions.UpdateException;
import org.oop18.models.OrderAdapter;
import org.oop18.models.OrderAdapterFactory;
import org.oop18.models.ProductAdapter;
import org.oop18.models.ProductAdapterFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * The user order details controller is used to help user check the details
 * of the order he has made or modify the order under several rules.
 *
 * @author - Haribo
 */
public class UserOrderDetailsController implements Initializable {
    private ProductAdapterFactory productAdapterFactory;
    private ProductAdapter productAdapter;
    private OrderAdapterFactory orderAdapterFactory;
    private OrderAdapter orderAdapter;
    private Product product;
    private User currentUser;
    private Order order;

    @FXML
    private TextField productTitleTextField;
    @FXML
    private TextField adultCountTextField;
    @FXML
    private TextField childrenCountTextField;
    @FXML
    private TextField productStartDateTextField;
    @FXML
    private TextField productEndDateTextField;


    public UserOrderDetailsController(ProductAdapterFactory productAdapterFactory, OrderAdapterFactory orderAdapterFactory, Order selectedUserOrder, User currentUser) {
        this.productAdapterFactory = productAdapterFactory;
        this.productAdapter = productAdapterFactory.create();
        this.orderAdapterFactory = orderAdapterFactory;
        this.orderAdapter = orderAdapterFactory.create();
        this.currentUser = currentUser;
        this.order = selectedUserOrder;
    }

    /**
     * Initialize the specific order information.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.product = productAdapter.queryProduct(order.getProductId());
            productTitleTextField.setText(product.getTitle());
            adultCountTextField.setText(String.valueOf(order.getAdultCount()));
            childrenCountTextField.setText(String.valueOf(order.getChildrenCount()));
            productStartDateTextField.setText(String.valueOf(product.getStartDate()));
            productEndDateTextField.setText(String.valueOf(product.getEndDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the modifying order event. It will allow user to modify his order if
     * the order format is correct, the quota (maximum headcount) of the product is enough
     * and there are at least 10 days left before the start date of the trip; otherwise it
     * will pop up an alert box to restrict the user to modify it.
     *
     * @param event
     */
    public void saveOrderHandler(Event event) {
        try {
            Integer adultCount = Integer.valueOf(adultCountTextField.getText());
            Integer childrenCount = Integer.valueOf(childrenCountTextField.getText());
            Integer totalPrice = (adultCount + childrenCount) * product.getPrice();

            order.setUserId(currentUser.getId());
            order.setProductId(product.getId());
            order.setAdultCount(adultCount);
            order.setChildrenCount(childrenCount);
            order.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            order.setTotalPrice(totalPrice);

            order = orderAdapter.updateOrder(order);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } catch (UpdateException e) {
            loadErrorBoxView((ActionEvent) event, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadErrorBoxView(ActionEvent event, String errorMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ErrorBox.fxml"));

            ErrorBoxController errorBoxController = new ErrorBoxController(errorMessage);
            loader.setController(errorBoxController);

            Parent ErrorBoxParent = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Error");
            stage.setScene(new Scene(ErrorBoxParent));
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
