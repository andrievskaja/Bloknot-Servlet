<!doctype html>
<%@ page import="java.text.*,java.util.*" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="en">
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="<c:url value="/css/font-awesome.css" />" rel="stylesheet">
        <link href="<c:url value="/css/style.css" />" rel="stylesheet">

    </head>
    <body>
        <div class="table">
             <div class="table-header">
                 <span class="fa fa-calendar" aria-hidden="true"></span><span>Complete the task ssdsdfsdfasd</span><span  class="fa fa-calendar" aria-hidden="true"></span><span  class="fa fa-calendar" aria-hidden="true"></span>
             </div>
            <form  name="form" id="save-form">
                <div class="heder-add">
                    <input 
                        class="input-task" 
                        type="text"
                        name="task"
                        id="save-task"/>
                    <button class="add-task" id="save-button" href="#" type="button">Add task</button>
                </div>
            </form>
            <table class="simple-little-table" id="task-table">
                <tr>
                    <th style="width: 10%"> </th>
                    <th ></th>
                    <th style="width: 10%"></th>

                    <th style="width: 10%"></th>
                </tr><!-- Table Header -->
                <c:forEach var="task" items="${tasks}">
                    <tr id="${task.id}">
                        <td id="${task.id}">
                            <input type="checkbox" ${task.done== true?"checked" :""} id="${task.id}"> </td>
                        <td id='task-${task.id}'>${task.task}</td>

                        <td id="edit-${task.id}" >                                                     

                            <a href="#" class="btn-lg" 
                               data-toggle="modal" 
                               data-target="#myModal"
                               data-id="${task.id}">
                                Edit
                            </a>
                        </td>
                        <td class="settings-task">
                            <a class="fa fa-window-close" aria-hidden="true" href="#" onclick="deleteTask(${task.id});">Delete</a>

                        </td>
                    </tr><!-- Table Row -->
                </c:forEach>

            </table>

        </div>
        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Редактировать задание</h4>
                    </div>
                    <div class="modal-body">
                        <form name="myForm" id="modal-window-form">
                            <input type="text" name="task" id="editText">
                            <input type="hidden" name="id" id="idTask">
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="modal-button" class="btn btn-default" data-dismiss="modal">Редактировать</button>
                    </div>
                </div>

            </div>

        </div>
        <script src="<c:url value="/js/libs/jquery/jquery.min.js" />"></script>
        <script src="<c:url value="/js/libs/bootstrap.min.js" />"></script>
        
        <script>
                                $(document).on("click", ".btn-lg", function () {
                                    var code = $(this).data('id');
                                    $("#idTask").val(code);

                                    $('#myModal').modal('show');
                                });
                               $(document).on('click','#modal-button', function (e) {
                                    event.preventDefault();
                                    var formURL = "<c:url value="/task/edit"/>";
                                    var posting = $.post(formURL, $('#modal-window-form').serialize());
                                    posting.done(function (data) {
                                        console.log(data);
                                        console.log($('#task-' + data.id + '').length);
                                        $('#task-' + data.id).html(data.task);
//                                        $('#task-' + data.id + '').html(data.task);
                                        console.log('task-' + data.id + '');
                                        console.log(data.task);
//                                        $('#task-3').html("7777777");
                                    });
                                });
                                var deleteTask = function (id) {
                                    $.post('${pageContext.servletContext.contextPath}/task/delete', {id: id}, function (data) {
                                        if (data === "ok") {
                                            $('#' + id + '').remove();
                                        }
                                    });
                                };
                                  $(document).on('click','#save-button', function (e) {
                                    e.preventDefault();
                                    var formURL = "<c:url value="/task/add"/>";

                                    var posting = $.post(formURL, $('#save-form').serialize());
                                    posting.done(function (data) {
                                        console.log(data);
                                        $('#task-table tr:last').after('<tr id="' + data.id + '">\n\
    <td><input type="checkbox"  id="' + data.id + '"></td>\n\
    <td id="task-' + data.id+'">' + data.task + '</td>\n\
    \n\
    <td  id="edit-' + data.id+'"><a href="#" class="btn-lg" \n\
data-toggle="modal" data-target="#myModal"  data-id="' + data.id + '"> Edit </a></td>\n\
    <td> <a href="#" onclick="deleteTask(' + data.id + ')";>Delete</a></td>\n\
    </tr>');
                                    });
                                });

                                $(document).on('change', ':checkbox', function () {
                                    var formURL = "<c:url value="/task/changeStatus"/>";
                                    $.post(formURL, {id: this.id});
                                });
        </script>
    </body>
</html>