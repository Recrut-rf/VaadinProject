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
public class OrderingGrid extends FormLayout {

    public List<Ordering> orderingList = DAO.getOrdering();
    public BeanItemContainer<Ordering> container = new BeanItemContainer<>(Ordering.class, orderingList);
    public Grid orderGrid = null;

    com.MainUI mainUI;

    public OrderingGrid(MainUI mainUI) throws SQLException, ClassNotFoundException {
        this.mainUI = mainUI;


        orderGrid = new Grid(container);

        orderGrid.setColumnOrder("id_or", "description", "customerInt", "createddate", "enddate",
                "cost", "status");

        orderGrid.getDefaultHeaderRow().getCell("id_or")
                .setHtml("<u>ID</u>");

        orderGrid.getDefaultHeaderRow().getCell("description")
                .setHtml("<u>Описание</u>");

        orderGrid.getDefaultHeaderRow().getCell("customerInt")
                .setHtml("<u>Клиент (Фамилия)</u>");

        orderGrid.getDefaultHeaderRow().getCell("createddate")
                .setHtml("<u>Дата создания</u>");

        orderGrid.getDefaultHeaderRow().getCell("enddate")
                .setHtml("<u>Дата окончания работ</u>");

        orderGrid.getDefaultHeaderRow().getCell("cost")
                .setHtml("<u>Стоимость</u>");

        orderGrid.getDefaultHeaderRow().getCell("status")
                .setHtml("<u>Статус</u>");


//  Фильтр----------------------------------------------------------------------
        Grid.HeaderRow filterRow = orderGrid.appendHeaderRow();
// Set up a filter for all columns
        for (Object pid : orderGrid.getContainerDataSource()
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
        addComponent(orderGrid);
        orderGrid.setSizeFull();
        setVisible(true);
    }

}

