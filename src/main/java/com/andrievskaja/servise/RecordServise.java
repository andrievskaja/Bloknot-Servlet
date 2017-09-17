/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrievskaja.servise;

import com.andrievskaja.service.model.view.RecordForm;
import com.andrievskaja.service.model.view.RecordView;
import java.util.List;

/**
 *
 * @author Людмила
 */
public interface RecordServise {
   /*
    Add new record 
     */
    public RecordView save(RecordForm form);


    public List<RecordView> getAll(Long userId);

    /*
    Delete  task in TaskTable
     */
    public String delete(Long id, Long userId);

    /*
    Edit  record
     */
    public String edit(Long id, String text, Long userId);

   
}
