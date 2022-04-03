package com.example.giuaky.template;

import java.io.Serializable;

public class UpdatePage implements Serializable {
    String title;
    String btnText;
    String btnStyle;

    public UpdatePage() {
    }

    public UpdatePage(String title, String btnText, String btnStyle) {
        this.title = title;
        this.btnText = btnText;
        this.btnStyle = btnStyle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public String getBtnStyle() {
        return btnStyle;
    }

    public void setBtnStyle(String btnStyle) {
        this.btnStyle = btnStyle;
    }
}
