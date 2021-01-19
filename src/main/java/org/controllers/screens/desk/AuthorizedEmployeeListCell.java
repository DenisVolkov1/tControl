package org.controllers.screens.desk;

import javafx.scene.control.ListCell;

public class AuthorizedEmployeeListCell extends ListCell<AuthorizedEmployeeInfo> {
    @Override
    protected void updateItem(AuthorizedEmployeeInfo item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null)
        {
            AuthorizedEmployeeCellController controller = new AuthorizedEmployeeCellController();
            controller.setInfo(item);
            setGraphic(controller.getMainPane());
        }

    }
}
