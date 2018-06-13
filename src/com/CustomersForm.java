package com;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOTRUDNIK on 14.06.2017.
 */
public class CustomersForm extends FormLayout {

    public TextField firstName = new TextField("Имя");
    public TextField lastName = new TextField("Фамилия");
    public TextField patronymic = new TextField("Отчество");
    public TextField phone = new TextField("Телефон");


    public Button save = new Button("Ok");
    public Button delete = new Button("Удалить");
    public Button changeCustomer = new Button("Изменить");
    public Button cancel = new Button("Отменить");

    private Customers customers = new Customers();
    private MainUI mainUI;
    Integer integer = customers.getId_cus();

    Label label = new Label();

    RegexpValidator regexpValidator = new RegexpValidator("[a-яА-Я]", false,
            "Только буквы");
    RegexpValidator regVal = new RegexpValidator("[0-9]", false,
            "Только цифры");




    public CustomersForm(MainUI mainUI) throws SQLException, ClassNotFoundException {
        this.mainUI = mainUI;

        setSizeUndefined();

        label.setWidth("30");
        label.setHeight("100");
        label.setVisible(true);
        HorizontalLayout buttons = new HorizontalLayout(save, changeCustomer, label, cancel, delete);
        HorizontalLayout button = new HorizontalLayout(delete);
        addComponents(firstName, lastName, patronymic, phone, buttons, button);





        firstName.setMaxLength(45);
        lastName.setMaxLength(45);
        patronymic.setMaxLength(70);
        phone.setMaxLength(45);

        firstName.addValidator(regexpValidator);
        lastName.addValidator(regexpValidator);
        patronymic.addValidator(regexpValidator);
        phone.addValidator(regVal);




        firstName.addListener((Listener) click -> {
            firstName.setValidationVisible(true);
            if (!firstName.isValid()) {
                Notification.show("Введены неверные данные!!!");
                firstName.clear();
            }
        });

        lastName.addListener((Listener) click -> {
            lastName.setValidationVisible(true);
            if (!lastName.isValid()) {
                Notification.show("Введены неверные данные!!!");
                lastName.clear();
            }
        });

        patronymic.addListener((Listener) click -> {
            patronymic.setValidationVisible(true);
            if (!patronymic.isValid()) {
                Notification.show("Введены неверные данные!!!");
                patronymic.clear();
            }
        });

        phone.addListener((Listener) click -> {
            phone.setValidationVisible(true);
            if (!phone.isValid()) {
                Notification.show("Введены неверные данные!!!");
                phone.clear();
            }
        });





        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        changeCustomer.setStyleName(ValoTheme.BUTTON_PRIMARY);
        changeCustomer.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        delete.setStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        delete.setVisible(false);
        changeCustomer.setVisible(false);

        //setVisible(true);


        save.addClickListener((Button.ClickListener) (Button.ClickEvent event) -> {
            //changeOrder.setVisible(false);
            //save.setVisible(true);
            try {
                this.save();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }) ;

        cancel.addClickListener((Button.ClickListener) (Button.ClickEvent event) -> {
            this.clear();
            setVisible(false);
            mainUI.customersGrid.custGrid.deselectAll();
        }) ;




        changeCustomer.addClickListener((Button.ClickListener) (Button.ClickEvent event) -> {

            if ((firstName.getValue()).isEmpty() || (lastName.getValue()).isEmpty()
                    || (patronymic.getValue()).isEmpty() || (phone.getValue()).isEmpty()) {
                mainUI.addWindow(mainUI.subWindow);
                return;

            }

            try {
                this.save();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {

                    this.delete(mainUI.customersGrid.custGrid.getSelectedRow());


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            //mainUI.drawGrid();
/*
            try {
                mainUI.customersGrid.customersList = mainUI.updateCustGrid();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            mainUI.customersGrid.container = new BeanItemContainer<Customers>(Customers.class, mainUI.customersGrid.customersList);

            mainUI.drawCustTable(mainUI.customersGrid.container);
*/
        }) ;


        mainUI.customersGrid.custGrid.addSelectionListener((SelectionEvent.SelectionListener) event -> {

            if (mainUI.customersGrid.custGrid.getSelectedRow() == null){
                setVisible(false);
            } else {
                this.clear();
                this.select(mainUI.customersGrid.custGrid.getSelectedRow());
                setVisible(true);
                delete.setVisible(true);
                changeCustomer.setVisible(true);
                save.setVisible(false);
            }
        });



        delete.addClickListener((Button.ClickListener) clickEvent -> {

            try {
                if (DAO.getFilterOrdering(lastName.getValue()).size() == 0) {

                    try {
                        this.delete(mainUI.customersGrid.custGrid.getSelectedRow());
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    //mainUI.drawGrid();

                    try {
                        mainUI.customersGrid.customersList = mainUI.updateCustGrid();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    mainUI.customersGrid.container = new BeanItemContainer<Customers>(Customers.class, mainUI.customersGrid.customersList);

                    mainUI.drawCustTable(mainUI.customersGrid.container);

                } else {
                    mainUI.addWindow(mainUI.subWindowFilter);

                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });


    }

    public void setForm(Customers customers){
        this.customers = customers;

    }

    public void setCustomers(TextField firstName, TextField lastName,
                             TextField patronymic, TextField phone) throws SQLException, ClassNotFoundException {
        



        if ((firstName.getValue()).isEmpty() || (lastName.getValue()).isEmpty()
                || (patronymic.getValue()).isEmpty() || (phone.getValue()).isEmpty()) {
            mainUI.addWindow(mainUI.subWindow);
            return;
            

        } else {
            customers = new Customers(integer, firstName.getValue(), lastName.getValue(), patronymic.getValue(),
                    phone.getValue());
        }


        DAO.addCustomers(customers);
        setVisible(true);
        mainUI.drawGrid();
/*
        try {
            mainUI.customersGrid.customersList = mainUI.updateCustGrid();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        mainUI.customersGrid.container = new BeanItemContainer<Customers>(Customers.class, mainUI.customersGrid.customersList);

        mainUI.drawCustTable(mainUI.customersGrid.container);
*/
    }

    private void save() throws SQLException, ClassNotFoundException {
        setCustomers(firstName, lastName, patronymic, phone);
        setVisible(false);
    }

    public void select(Object selected){

        firstName.setValue((String) mainUI.customersGrid.custGrid.getContainerDataSource().getItem(selected).getItemProperty("name").getValue());
        lastName.setValue((String) mainUI.customersGrid.custGrid.getContainerDataSource().getItem(selected).getItemProperty("surname").getValue());
        patronymic.setValue((String) mainUI.customersGrid.custGrid.getContainerDataSource().getItem(selected).getItemProperty("patronymic").getValue());
        phone.setValue((String) mainUI.customersGrid.custGrid.getContainerDataSource().getItem(selected).getItemProperty("phone").getValue());

    }

    public void delete(Object selected) throws SQLException, ClassNotFoundException {
        integer = (Integer) mainUI.customersGrid.custGrid.getContainerDataSource().getItem(selected).getItemProperty("id_cus").getValue();
        DAO.deleteCustomers(integer);

    }

    public void clear(){
        firstName.clear();
        lastName.clear();
        patronymic.clear();
        phone.clear();
    }




}