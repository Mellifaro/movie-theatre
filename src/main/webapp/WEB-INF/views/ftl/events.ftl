<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
    <#include "fragments/header.ftl">
    <body class="fullBackground">
        <div class="container container-bordered">
            <#include "fragments/bodyHeader.ftl">

            <table class="table" id="datatable">
                <thead>
                <tr>
                    <th width="4%" class="allign-text-center">#</th>
                    <th>Name</th>
                    <th>Date-Audinorium</th>
                    <th class="allign-text-center">Base price</th>
                    <th class="allign-text-center">Rating</th>
                </tr>
                </thead>

                <tbody>
                     <#list events as event>
                     <tr>
                         <td class="allign-text-center">${event?index + 1}</td>
                         <td>${event.name}</td>
                         <td>
                             <#list event.auditoriums as date, auditorium>
                                 <a href="<@spring.url '/events/${event.id}/date/${date}'/>">
                                     ${date} - ${auditorium.name}<br>
                                 </a>
                             </#list>

                         </td>
                         <td class="allign-text-center">${event.basePrice}</td>
                         <td class="allign-text-center">${event.rating}</td>
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

        <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/events.js'/>"></script>
    </body>
</html>