
package com.andrievskaja.service.model.view;

/**
 *
 * @author Людмила
 */
public class RecordForm {

    private Long id;
    private String text;
    private Long userId;


    public RecordForm() {
    }

    public RecordForm(String text) {
        this.text = text;
    }

    public RecordForm(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
