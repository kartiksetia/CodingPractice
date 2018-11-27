package com.accenture.kartik.accenturetask.mvp.view;

public interface MainView {

    /**
     * Shows an error dialog.
     *
     * @param title The title of the dialog.
     * @param message The message of the dialog.
     */
    void showErrorDialog(String title, String message);


    void updateList();
}
