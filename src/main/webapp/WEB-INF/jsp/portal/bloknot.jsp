<!doctype html>
<%@ page import="java.text.*,java.util.*" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Bloknot</title>

        <link rel="stylesheet" type="text/css" href="/style.css"/>
    </head>
    <body>
        <div class="logout-div">
            <a class="logout-button" href="/LogoutServlet">Logout</a>
        </div>
        <div id="header-div">
            <div class="table">
                <div class="table-header">
                    <span class="fa fa-calendar" aria-hidden="true"></span><span>Bloknot</span><span  class="fa fa-calendar" aria-hidden="true"></span><span  class="fa fa-calendar" aria-hidden="true"></span>

                </div>
                <form  name="form" id="save-form"><div class="heder-add">
                        <input class="input-task" type="text" name="text"/>
                        <button class="add-task save-button" onclick="save()" href="#" type="button">Add record</button>
                    </div> </form>
                <table class="simple-little-table" id="task-table">
                    <tr>
                        <th style="width: 10%"> </th>
                        <th ></th>
                        <th style="width: 10%"></th>
                        <th style="width: 10%"></th>
                    </tr>
                    <c:forEach var="record" items="${records}"> 
                        <tr id="record-tr-${record.id}">
                            <td>
                                ${record.date}
                            </td>
                            <td id="task-${record.id}">
                                <input id="input-${record.id}" class="input-text" type="text" name="text" value="${record.text}"> 
                            </td>

                            <td id="edit-${record.id}"><a href="#"  onclick="edit(${record.id});"> Edit </a></td>
                            <td> <a href="#" onclick="deleteRecord(${record.id});">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <script src="<c:url value="/jquery.min.js" />"></script>
            <script>
                                var deleteRecord = function (id) {
                                    $.post('${pageContext.servletContext.contextPath}/delete', {id: id}, function (data) {
                                        console.log(data);
                                        if (data === "ok") {
                                            $("#record-tr-" + id).remove();
                                        }
                                    });
                                };
                                var edit = function (id) {
                                    var text = $("#input-" + id).val();
                                    console.log(text);
                                    $.post('${pageContext.servletContext.contextPath}/edit', {id: id, text: text}, function (data) {

                                    });
                                };
                                var save = function (id) {
                                    var formURL = "<c:url value="/add"/>";
                                    var posting = $.post(formURL, $("#save-form").serialize());
                                    posting.done(function (data) {
                                        console.log(data);

                                        var result = $.parseJSON(data);
                                        $('#task-table tr:last').after('<tr id="record-tr-' + result.id + '">\n\
        <td>  ' + result.date + ' </td>\n\
        <td><input id="input-' + result.id + '" class="input-text" type="text" name="text" value="' + result.text + '"></td>\n\
        \n\
        <td><a href="#" onclick="edit(' + result.id + ')";> Edit </a></td>\n\
        <td> <a href="#" onclick="deleteRecord(' + result.id + ')";>Delete</a></td>\n\
        </tr>');
                                    });
                                };
            </script>
    </body>
</html>
