package com;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import javafx.scene.control.SelectionModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.ui.Button.*;

/**
 * Created by SOTRUDNIK on 14.06.2017.
 */
@Title("Автомастерская")
@Theme("valo")
public class MainUI extends UI {

    OrderingGrid orderingGrid = new OrderingGrid(this);
    CustomersGrid customersGrid = new CustomersGrid(this);


    CustomersForm customersForm = new CustomersForm(this);
    OrderingForm orderingForm = new OrderingForm(this);

    Button button = new Button("Ok");
    Window subWindow = new Window();

    Button buttonOr = new Button("Ok");
    Window subWindowOr = new Window();

    Label custLabel = new Label("Клиент");
    Label orderLabel = new Label("Заказ");

    Button addCustomerBtn = new Button("Добавить");
    Button addOrderingBtn = new Button("Добавить");

    Button buttonFilter = new Button("Да");
    Button buttonFilterSecond = new Button("Нет");
    Window subWindowFilter = new Window();
    Label label = new Label();


/*
    List<Ordering> list = new ArrayList<>();
    BeanItemContainer<Ordering> beanItemContainer = new BeanItemContainer<>(Ordering.class, list);
*/


    public MainUI() throws SQLException, ClassNotFoundException {  }





    @Override
    protected void init(VaadinRequest request) {



        custLabel.addStyleName(ValoTheme.LABEL_H2);
        orderLabel.addStyleName(ValoTheme.LABEL_H2);

        addCustomerBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addCustomerBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        addOrderingBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addOrderingBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);

        subWindow.setWidth("300");
        subWindow.setHeight("100");

        button.setWidth("70");
        button.setHeight("25");
        button.focus();
        button.setStyleName(ValoTheme.BUTTON_PRIMARY);
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        subContent.addComponent(new Label("Введены не все данные!!!"));
        subContent.addComponent(button);


        subWindow.center();
        subWindow.setModal(true);

        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                subWindow.close();
                customersForm.setVisible(true);
                //orderingForm.setVisible(false);
            }
        });

        VerticalLayout subContentOr = new VerticalLayout();
        subContentOr.setMargin(true);
        subWindowOr.setContent(subContentOr);

        subWindowOr.setWidth("300");
        subWindowOr.setHeight("100");

        buttonOr.setWidth("70");
        buttonOr.setHeight("25");
        buttonOr.focus();
        buttonOr.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonOr.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        subContentOr.addComponent(new Label("Введены не все данные!!!"));
        subContentOr.addComponent(buttonOr);


        subWindowOr.center();
        subWindowOr.setModal(true);

        buttonOr.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                subWindowOr.close();
                //customersForm.setVisible(false);
                orderingForm.setVisible(true);
            }
        });

        label.setWidth("30");
        label.setHeight("100");
        label.setVisible(true);

        VerticalLayout subContentFilter = new VerticalLayout();
        subContentFilter.setMargin(true);
        subWindowFilter.setContent(subContentFilter);

        subWindowFilter.setWidth("600");
        subWindowFilter.setHeight("100");

        buttonFilter.setWidth("70");
        buttonFilter.setHeight("25");
        buttonFilter.focus();
        buttonFilter.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonFilter.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        buttonFilterSecond.setWidth("70");
        buttonFilterSecond.setHeight("25");

        HorizontalLayout filterLayout = new HorizontalLayout(buttonFilter, label, buttonFilterSecond);

        subContentFilter.addComponent(new Label("Для этого клиента существуют заказы" +
                " поэтому удаление невозможно! Показать список заказов?"));
        subContentFilter.addComponents(filterLayout);



        subWindowFilter.center();
        subWindowFilter.setModal(true);

        buttonFilter.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                try {
                    orderingGrid.orderingList = updateOrderGrid(customersForm.lastName.getValue());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                subWindowFilter.close();

                orderingGrid.container = new BeanItemContainer<Ordering>(Ordering.class, orderingGrid.orderingList);

                drawOrderTable(orderingGrid.container);


            }
        });

        buttonFilterSecond.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                subWindowFilter.close();
                customersForm.setVisible(true);
                //orderingForm.setVisible(false);
            }
        });


        customersForm.setVisible(false);
        orderingForm.setVisible(false);





        addCustomerBtn.addClickListener((ClickListener) event -> {

            customersForm.changeCustomer.setVisible(false);
            customersForm.save.setVisible(true);

            customersForm.delete.setVisible(false);
            customersForm.clear();
            customersForm.setForm(new Customers());
            customersForm.setVisible(true);
        });

        addOrderingBtn.addClickListener((ClickListener) event -> {

            orderingForm.changeOrder.setVisible(false);
            orderingForm.save.setVisible(true);

            orderingForm.delete.setVisible(false);
            orderingForm.clear();
            orderingForm.setForm(new Ordering());
            orderingForm.setVisible(true);
        });







        //------------------------------------------------------------------------------------------------
        //  Layouts --------------------------------------------------------------------------------------

        final VerticalLayout verticalLayout = new VerticalLayout();

        Label label = new Label();
        label.setWidth("30");
        label.setHeight("100");
        label.setVisible(true);

        HorizontalLayout custPanel = new HorizontalLayout();
        custPanel.addComponents(custLabel, label, addCustomerBtn);
        custPanel.setComponentAlignment(addCustomerBtn, Alignment.MIDDLE_CENTER);
        custPanel.setComponentAlignment(custLabel, Alignment.MIDDLE_CENTER);
        addCustomerBtn.setWidth("50%");

        VerticalLayout vLayout = new VerticalLayout();
        vLayout.addComponent(custPanel);
        vLayout.setComponentAlignment(custPanel, Alignment.TOP_RIGHT);

        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponents(customersGrid, customersForm);
        layout.setSizeFull();
        customersGrid.setSizeFull();
        layout.setExpandRatio(customersGrid,1);

        Label label2 = new Label();
        label2.setWidth("30");
        label2.setHeight("100");
        label2.setVisible(true);

        HorizontalLayout orderPanel = new HorizontalLayout();
        orderPanel.addComponents(orderLabel, label2, addOrderingBtn);
        orderPanel.setComponentAlignment(addOrderingBtn, Alignment.MIDDLE_CENTER);
        orderPanel.setComponentAlignment(orderLabel, Alignment.MIDDLE_CENTER);
        addOrderingBtn.setWidth("50%");

        VerticalLayout vLayout2 = new VerticalLayout();
        vLayout2.addComponent(orderPanel);
        vLayout2.setComponentAlignment(orderPanel, Alignment.TOP_RIGHT);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(orderingGrid, orderingForm);
        horizontalLayout.setSizeFull();
        orderingGrid.setSizeFull();
        horizontalLayout.setExpandRatio(orderingGrid,1);

        verticalLayout.addComponents(vLayout, layout, vLayout2, horizontalLayout);

        setContent(verticalLayout);
    }

    public void drawGrid(){
        for (UI ui: VaadinSession.getCurrent().getUIs())
            ui.access(() -> {
// Redirect from the page
                ui.getPage().setLocation("/workshop.html");
            });
        getSession().close();
    }

    public List<Customers> updateCustGrid(String str) throws SQLException, ClassNotFoundException {
        return customersGrid.customersList = DAO.getFilterCustomers(str);
    }

    public List<Customers> updateCustGrid() throws SQLException, ClassNotFoundException {
        return customersGrid.customersList = DAO.getCustomers();
    }


    public void drawCustTable(BeanItemContainer container) {
        customersGrid.custGrid.setContainerDataSource(container);
    }

    public List<Ordering> updateOrderGrid(String str) throws SQLException, ClassNotFoundException {
        return orderingGrid.orderingList = DAO.getFilterOrdering(str);
    }

    public List<Ordering> updateOrderGrid() throws SQLException, ClassNotFoundException {
        return orderingGrid.orderingList = DAO.getOrdering();
    }

    public void drawOrderTable(BeanItemContainer container) {

        orderingGrid.orderGrid.setContainerDataSource(container);
    }


}