/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.util.List;
import java.util.stream.Collectors;
import modals.Book;
import views.CheckList;
import views.Input;
import views.OutPut;

/**
 *
 * @author ritzhaki
 */
public abstract class BaseController {

    protected final BaseDal _dal;

    public BaseController(BaseDal dal) {
        this._dal = dal;

    }
   
}
