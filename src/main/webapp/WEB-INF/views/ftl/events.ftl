<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
    <#include "fragments/header.ftl">
    <body class="fullBackground">
        <div class="container container-bordered">
            <#include "fragments/bodyHeader.ftl">
            <@security.authorize access="hasRole('ROLE_ADMIN')">
                <button class="btn btn-outline-primary my-2 my-sm-0" id="createEvent">Create event</button>
            </@security.authorize>
            <table class="table" id="datatable">
                <thead>
                <tr>
                    <th width="4%" class="allign-text-center">#</th>
                    <th class="d-none">Id</th>
                    <th>Name</th>
                    <th>Date-Audinorium</th>
                    <th class="allign-text-center">Base price</th>
                    <th class="allign-text-center">Rating</th>
                    <@security.authorize access="hasRole('ROLE_ADMIN')">
                        <th class="allign-text-center">Add date</th>
                        <th class="allign-text-center">Remove</th>
                    </@security.authorize>
                </tr>
                </thead>

                <tbody>
                     <#list events as event>
                     <tr>
                         <td class="allign-text-center">${event?index + 1}</td>
                         <td class="id d-none">${event.id}</td>
                         <td>${event.name}</td>
                         <td>
                             <#list event.auditoriums as date, auditorium>
                                 <a href="<@spring.url '/events/${event.id}/date/${date}/visual'/>">
                                     ${date} - ${auditorium.name}<br>
                                 </a>
                             </#list>

                         </td>
                         <td class="allign-text-center">${event.basePrice}</td>
                         <td class="allign-text-center">${event.rating}</td>
                         <@security.authorize access="hasRole('ROLE_ADMIN')">
                             <td align="center"><img class="add-icon" src="<@spring.url '/resources/images/add-notactive.svg'/>"></td>
                             <td align="center"><img class="delete-icon" src="<@spring.url '/resources/images/delete-notactive.svg'/>"></td>
                         </@security.authorize>
                         </tr>
                     </#list>
                </tbody>
            </table>

            <form method="POST" action="<@spring.url '/events/uploadFile'/>" enctype="multipart/form-data" >
                <input type="file" name="file" />
                <input type="submit" value="Upload">
            </form>
        </div>

        <div class="modal fade" id="editRow">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="modalTitle">Registration</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" action="<@spring.url '/users/register'/>" method="post" id="detailsForm" >

                            <div class="form-group">
                                <label for="first_name" class="control-label col-xs-3">First name</label>
                                <div class="col-xs-9">
                                    <input type="text" class="form-control" id="firstName" name="firstName" placeholder="First name">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="last_name" class="control-label col-xs-3">Last name</label>
                                <div class="col-xs-9">
                                    <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Last name">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="email" class="control-label col-xs-3">Email</label>
                                <div class="col-xs-9">
                                    <input type="email" class="form-control" id="email" name="email" placeholder="email">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="password" class="control-label col-xs-3">Password</label>

                                <div class="col-xs-9">
                                    <input type="password" class="form-control" id="password" name="password" placeholder="">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="birthday" class="control-label col-xs-3">Birthday</label>

                                <div class="col-xs-9">
                                    <input type="date" class="form-control" id="birthday" name="birthday" placeholder="">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-xs-offset-3 col-xs-9">
                                    <button type="submit" class="btn btn-primary">Register</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="addEvent">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="modalTitle">Adding new event:</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" action="<@spring.url '/events/add'/>" method="post" id="addEventForm" >

                            <div class="form-group">
                                <label for="eventName" class="control-label col-xs-3">Title</label>
                                <div class="col-xs-9">
                                    <input type="text" class="form-control" id="eventName" name="name" placeholder="Title">
                                </div>
                            </div>

                            <#--<div class="form-group">-->
                                <#--<label class="mr-sm-2" for="movieRating">Rating</label>-->
                                <#--<select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="movieRating">-->
                                    <#--<option value="low">Low</option>-->
                                    <#--<option value="mid" selected>Middle</option>-->
                                    <#--<option value="high">High</option>-->
                                <#--</select>-->
                            <#--</div>-->

                            <div class="form-group">
                                <label for="rating" class="control-label col-xs-3">Rating</label>
                                <input type="text" class="form-control" id="rating" name="rating" list="ratingnames">
                                <datalist id="ratingnames">
                                    <option value="LOW">
                                    <option value="MID">
                                    <option value="HIGH">
                                </datalist>
                            </div>

                            <div class="form-group">
                                <label for="basePrice" class="control-label col-xs-3">Title</label>
                                <div class="col-xs-9">
                                    <input type="number" step="0.01" class="form-control" id="basePrice" name="basePrice" placeholder="0.00">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-xs-offset-3 col-xs-9">
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addDate">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="modalTitle">Assigning date and auditorium</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" action="<@spring.url '/events/date/add'/>" method="post" id="addDateForm" >

                            <div class="form-group d-none">
                                <label for="eventId" class="control-label col-xs-3">Id</label>
                                <div class="col-xs-9">
                                    <input type="number" class="form-control" id="eventId" name="eventId" placeholder="">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="eventTime" class="control-label col-xs-3">Event time</label>
                                <div class="col-xs-9">
                                    <input type="datetime-local" class="form-control" id="eventTime" name="eventTime" placeholder="">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="auditorium" class="control-label col-xs-3">Auditorium</label>
                                <input type="text" class="form-control" id="auditorium" name="auditorium" list="auditorium-names">
                                <datalist id="auditorium-names">
                                    <#list auditoriums as auditorium>
                                        <option value="${auditorium.name}">
                                    </#list>
                                    <#--<option value="green">-->
                                    <#--<option value="red">-->
                                </datalist>
                            </div>

                            <div class="form-group">
                                <div class="col-xs-offset-3 col-xs-9">
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/events.js'/>"></script>
    </body>
</html>