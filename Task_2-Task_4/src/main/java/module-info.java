module com.example.task_4v2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.Task_2_Task_4 to javafx.fxml;
    exports com.example.Task_2_Task_4;
}