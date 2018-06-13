package com;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by SOTRUDNIK on 25.06.2017.
 */
public class OrderingForm extends FormLayout {

    public TextField description = new TextField("Описание заказа");
    public TextField customerInt = new TextField("Клиент (Фамилия)");
    public DateField createddate = new DateField("Дата создания");
    public DateField enddate = new DateField("Дата окончания работ");
    public TextField cost = new TextField("Стоимость");
    public NativeSelect statusSelect = new NativeSelect("Статус");

    public String status = null;

    public Button save = new Button("Ok");
    public Button delete = new Button("Удалить");
    public Button changeOrder = new Button("Изменить");
    public Button cancel = new Button("Отменить");


    private Ordering ordering = new Ordering();
    private MainUI mainUI;
    Integer integer = ordering.getId_or();

    RegexpValidator regexpValidator = new RegexpValidator("[a-яА-Я]", false,
            "Только буквы");
    RegexpValidator regVal = new RegexpValidator("[0-9]", false,
            "Только цифры");

    Label label = new Label();



    public OrderingForm(MainUI mainUI) {
        this.mainUI = mainUI;

        statusSelect.addItems("Запланирован", "Выполнен", "Принят клиентом");

        setSizeUndefined();

        label.setWidth("30");
        label.setHeight("100");
        label.setVisible(true);
        HorizontalLayout buttons = new HorizontalLayout(save, changeOrder, label, cancel, delete);
        HorizontalLayout button = new HorizontalLayout(delete);
        addComponents(description, customerInt, createddate, enddate, cost, statusSelect, buttons, button);

        description.setMaxLength(255);
        customerInt.setMaxLength(45);
        cost.setMaxLength(10);


        description.addValidator(regexpValidator);
        customerInt.addValidator(regexpValidator);
        cost.addValidator(regVal);

        description.addListener((Listener) click -> {
            description.setValidationVisible(true);
            if (!description.isValid()) {
                Notification.show("Введены неверные данные!!!");
                description.clear();
            }
        });

        customerInt.addListener((Listener) click -> {
            customerInt.setValidationVisible(true);
            if (!customerInt.isValid()) {
                Notification.show("Введены неверные данные!!!");
                customerInt.clear();
            }
        });


/*
        customerInt.addListener((Listener) click -> {
            try {
                if (DAO.getFilterCustomers(customerInt.getValue()).size() == 0){
                    Notification.show("Данной фамилии нет в базе Клиентов," +
                            " добавление(изменение) данных невозможно!!!");
                    customerInt.clear();
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
  */


        customerInt.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                // Assuming that the value type is a String
                List<Customers> value = null;
                try {
                    value = DAO.getFilterCustomers(customerInt.getValue());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (value.isEmpty()){
                    Notification.show("Данной фамилии нет в базе Клиентов," +
                            " добавление(изменение) данных невозможно!!!");
                    customerInt.clear();
                }


            }
        });



        cost.addListener((Listener) click -> {
            cost.setValidationVisible(true);
            if (!cost.isValid()) {
                Notification.show("Введены неверные данные!!!");
                cost.clear();
            }
        });


        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        changeOrder.setStyleName(ValoTheme.BUTTON_PRIMARY);
        changeOrder.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        delete.setStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        delete.setVisible(false);
        changeOrder.setVisible(false);


        save.addClickListener((Button.ClickListener) event -> {
            try {
                this.save();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        //mainUI.orderingGrid.orderGrid.setImmediate(true);

        cancel.addClickListener((Button.ClickListener) (Button.ClickEvent event) -> {
            this.clear();
            setVisible(false);
            mainUI.orderingGrid.orderGrid.deselectAll();

        }) ;

        changeOrder.addClickListener((Button.ClickListener) (Button.ClickEvent event) -> {



            if ((description.getValue()).isEmpty() || (customerInt.getValue()).isEmpty()
                    || createddate.isEmpty() || enddate.isEmpty()
                    || (cost.getValue()).isEmpty() || statusSelect.isEmpty()) {
                mainUI.addWindow(mainUI.subWindowOr);
                return;
            }

            try {
                this.save();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                this.delete(mainUI.orderingGrid.orderGrid.getSelectedRow());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            //mainUI.drawGrid();
/*
            try {
                mainUI.orderingGrid.orderingList = mainUI.updateOrderGrid();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            mainUI.orderingGrid.container = new BeanItemContainer<Ordering>(Ordering.class, mainUI.orderingGrid.orderingList);

            mainUI.drawOrderTable(mainUI.orderingGrid.container);
*/

        }) ;


        mainUI.orderingGrid.orderGrid.addSelectionListener((SelectionEvent.SelectionListener) event -> {

            if (mainUI.orderingGrid.orderGrid.getSelectedRow() == null){
                setVisible(false);
            } else {
                this.clear();
                this.select(mainUI.orderingGrid.orderGrid.getSelectedRow());
                setVisible(true);
                delete.setVisible(true);
                changeOrder.setVisible(true);
                save.setVisible(false);
            }


        });

        delete.addClickListener((Button.ClickListener) clickEvent -> {

            try {
                this.delete(mainUI.orderingGrid.orderGrid.getSelectedRow());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            //mainUI.drawGrid();

            try {
                mainUI.orderingGrid.orderingList = mainUI.updateOrderGrid();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            mainUI.orderingGrid.container = new BeanItemContainer<Ordering>(Ordering.class, mainUI.orderingGrid.orderingList);

            mainUI.drawOrderTable(mainUI.orderingGrid.container);

        });
    }


    public void setForm(Ordering ordering) {
        this.ordering = ordering;

    }

    public void setOrdering(TextField description, TextField customerInt, DateField createddate,
                            DateField enddate, TextField cost, String status) throws SQLException, ClassNotFoundException {


        status = (String) statusSelect.getValue();


        if ((description.getValue()).isEmpty() || (customerInt.getValue()).isEmpty()
                || createddate.isEmpty() || enddate.isEmpty()
                || (cost.getValue()).isEmpty() || statusSelect.isEmpty()) {
            mainUI.addWindow(mainUI.subWindowOr);
            return;
        } else {
            ordering = new Ordering(integer, description.getValue(), customerInt.getValue(), createddate.getValue(),
                    enddate.getValue(), cost.getValue(), status);

        }

        DAO.addOrdering(ordering);
        setVisible(true);
        mainUI.drawGrid();
/*
        try {
            mainUI.orderingGrid.orderingList = mainUI.updateOrderGrid();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        mainUI.orderingGrid.container = new BeanItemContainer<Ordering>(Ordering.class, mainUI.orderingGrid.orderingList);

        mainUI.drawOrderTable(mainUI.orderingGrid.container);
*/
    }

    public void save() throws SQLException, ClassNotFoundException {
        setOrdering(description, customerInt, createddate, enddate, cost, status);


        setVisible(false);
    }

    public void select(Object selected) {

        description.setValue((String) mainUI.orderingGrid.orderGrid.getContainerDataSource().getItem(selected).getItemProperty("description").getValue());
        customerInt.setValue((String) mainUI.orderingGrid.orderGrid.getContainerDataSource().getItem(selected).getItemProperty("customerInt").getValue());
        createddate.setValue((Date) mainUI.orderingGrid.orderGrid.getContainerDataSource().getItem(selected).getItemProperty("createddate").getValue());
        enddate.setValue((Date) mainUI.orderingGrid.orderGrid.getContainerDataSource().getItem(selected).getItemProperty("enddate").getValue());
        cost.setValue((String) mainUI.orderingGrid.orderGrid.getContainerDataSource().getItem(selected).getItemProperty("cost").getValue());

        String string = new String(String.valueOf(mainUI.orderingGrid.orderGrid.getContainerDataSource().getItem(selected).getItemProperty("status")));

        statusSelect.setValue(string);


    }

    public void delete(Object selected) throws SQLException, ClassNotFoundException {
        integer = (Integer) mainUI.orderingGrid.orderGrid.getContainerDataSource().getItem(selected).getItemProperty("id_or").getValue();
        DAO.deleteOrdering(integer);

    }

    public void clear() {
        description.clear();
        customerInt.clear();
        createddate.clear();
        enddate.clear();
        cost.clear();
        statusSelect.clear();
    }


}