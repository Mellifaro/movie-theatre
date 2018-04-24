<#import "spring.ftl" as spring />
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Movie theatre</title>

        <link rel="stylesheet" href="<@spring.url '/resources/css/style.css'/>"/>
        <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/4.1.0/css/bootstrap.min.css'/>">
    </head>

    <body class="fullBackground">
        <div class="container container-bordered marg">
            <h3><em>The List Of Users</em></h3>
            <table class="table" id="datatable">
                <thead>
                    <tr>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Email</th>
                        <th>Birthday</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>

                <tbody>
                     <#list users as user>
                     <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.birthday}</td>
                        <td><img class="edit-icon" src="<@spring.url '/resources/images/edit-notactive.svg'/>"></td>
                        <td></td>
                     </tr>
                     </#list>
                </tbody>
            </table>

            <div class="custom-file">
                <input type="file" class="custom-file-input" id="validatedCustomFile" required>
                <label class="custom-file-label" for="validatedCustomFile">Choose file...</label>
                <div class="invalid-feedback">Example invalid custom file feedback</div>
            </div>
        </div>

        <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/main.js'/>"></script>
    </body>
</html>