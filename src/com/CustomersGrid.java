package com;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SOTRUDNIK on 26.06.2017.
 */
public class CustomersGrid extends FormLayout {

    public List<Customers> customersList = DAO.getCustomers();
    public BeanItemContainer<Customers> container = new BeanItemContainer<>(Customers.class, customersList);
    public Grid custGrid = null;

    MainUI mainUI;

    public CustomersGrid(MainUI mainUI) throws SQLException, ClassNotFoundException {
        this.mainUI = mainUI;


        custGrid = new Grid(container);

        custGrid.setColumnOrder("id_cus", "name", "surname", "patronymic", "phone");

        custGrid.getDefaultHeaderRow().getCell("id_cus")
                .setHtml("<u>ID</u>");

        custGrid.getDefaultHeaderRow().getCell("name")
                .setHtml("<u>Имя</u>");


        custGrid.getDefaultHeaderRow().getCell("surname")
                .setHtml("<u>Фамилия</u>");

        custGrid.getDefaultHeaderRow().getCell("patronymic")
                .setHtml("<u>Отчество</u>");

        custGrid.getDefaultHeaderRow().getCell("phone")
                .setHtml("<u>Телефон</u>");

//  Фильтр----------------------------------------------------------------------
        Grid.HeaderRow filterRow = custGrid.appendHeaderRow();
// Set up a filter for all columns
        for (Object pid : custGrid.getContainerDataSource()
                .getContainerPropertyIds()) {
            Grid.HeaderCell cell = filterRow.getCell(pid);
// Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.setInputPrompt("Фильтр");
            filterField.setColumns(8);
// Update filter When the filter input is changed
            filterField.addTextChangeListener(change -> {
// Can't modify filters so need to replace

                container.removeContainerFilters(pid);
// (Re)create the filter if necessary
                if (!change.getText().isEmpty())

                    container.addContainerFilter(
                            new SimpleStringFilter(pid,
                                    change.getText(), true, false));

            });
            cell.setComponent(filterField);
        }
//---------------------------------------------------------------------

//  Layout
        addComponent(custGrid);
        custGrid.setSizeFull();
        setVisible(true);
    }

}